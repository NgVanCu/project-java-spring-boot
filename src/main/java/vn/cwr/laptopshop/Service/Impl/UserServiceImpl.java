package vn.cwr.laptopshop.service.Impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import vn.cwr.laptopshop.domain.Role;
import vn.cwr.laptopshop.domain.User;
import vn.cwr.laptopshop.dto.RegisterDTO;
import vn.cwr.laptopshop.dto.UserDTO;
import vn.cwr.laptopshop.dto.UserUpdateProfileDTO;
import vn.cwr.laptopshop.mapper.UserMapper;
import vn.cwr.laptopshop.repository.RoleRepository;
import vn.cwr.laptopshop.repository.UserRepository;
import vn.cwr.laptopshop.service.UserService;
import vn.cwr.laptopshop.util.FileUploadUtil;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserMapper userMapper;
    @Value("${project.upload.dir.user}")
    private String userUploadDir;

    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.userMapper = userMapper;
    }

    @Override
    @Transactional
    public UserDTO registerUser(RegisterDTO registerDTO) {
        if (userRepository.existsByEmail(registerDTO.getEmail())) {
            throw new RuntimeException("Đã tồn tại Email này rồi!");
        }
        if (!registerDTO.getPassword().equals(registerDTO.getConfirmPassword())) {
            throw new RuntimeException("Mật khẩu không trùng khớp");
        }
        User newUser = new User();
        newUser.setEmail(registerDTO.getEmail());
        newUser.setPassword(registerDTO.getPassword());
        newUser.setFirstName(registerDTO.getFirstName());
        newUser.setLastName(registerDTO.getLastName());
        newUser.setPhone(registerDTO.getPhone());
        newUser.setAddress(registerDTO.getAddress());
        Role defaultRole = roleRepository.findByName("USER");
        newUser.setRole(defaultRole);
        userRepository.save(newUser);
        UserDTO userDTO = userMapper.toDTO(newUser);
        return userDTO;
    }

    @Override
    @Transactional
    public UserDTO updateProfile(UserUpdateProfileDTO updateProfileDTO, Long userId) {
        User existUser = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Không tồn tại User này!"));
        if (updateProfileDTO.getFirstName() != null && !updateProfileDTO.getFirstName().trim().isEmpty()) {
            existUser.setFirstName(updateProfileDTO.getFirstName());
        }
        if (updateProfileDTO.getLastName() != null && !updateProfileDTO.getLastName().trim().isEmpty()) {
            existUser.setLastName(updateProfileDTO.getLastName());
        }
        if (updateProfileDTO.getPhone() != null && !updateProfileDTO.getPhone().trim().isEmpty()) {
            existUser.setPhone(updateProfileDTO.getPhone());
        }
        if (updateProfileDTO.getAddress() != null && !updateProfileDTO.getAddress().trim().isEmpty()) {
            existUser.setAddress(updateProfileDTO.getAddress());
        }
        MultipartFile avatarFile = updateProfileDTO.getAvatar();
        if (avatarFile != null && !avatarFile.isEmpty()) {
            try {
                String originalFileName = StringUtils.cleanPath(avatarFile.getOriginalFilename());
                String fileName = System.currentTimeMillis() + "_" + originalFileName;
                FileUploadUtil.saveFile(userUploadDir, fileName, avatarFile);
                existUser.setAvatar(fileName);
            } catch (Exception e) {
                throw new RuntimeException("Lỗi trong quá trình lưu ảnh đại diện: " + e.getMessage());
            }
        }
        userRepository.save(existUser);
        UserDTO userDTO = userMapper.toDTO(existUser);
        return userDTO;
    }

    @Override
    public UserDTO getUserById(Long id) {
        User existUser = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tồn tại User này!"));
        UserDTO userDTO = userMapper.toDTO(existUser);
        return userDTO;
    }

    @Override
    public void deleteUserById(Long id) {
        this.getUserById(id);
        userRepository.deleteById(id);
    }

    @Override
    public Page<UserDTO> getUsers(int page, int size) {
        page = Math.max(page, 1);
        size = Math.max(1, Math.min(size, 50));

        PageRequest pageable = PageRequest.of(
                page - 1,
                size,
                Sort.by("id").descending());

        return userRepository.findAll(pageable)
                .map(userMapper::toDTO);
    }
}
