package vn.cwr.laptopshop.service;

import vn.cwr.laptopshop.dto.RegisterDTO;
import vn.cwr.laptopshop.dto.UserDTO;
import vn.cwr.laptopshop.dto.UserUpdateProfileDTO;
import org.springframework.data.domain.Page;

public interface UserService {
    UserDTO registerUser(RegisterDTO registerDTO);

    UserDTO updateProfile(UserUpdateProfileDTO updateProfileDTO, Long userId);

    Page<UserDTO> getUsers(int page, int size);

    UserDTO getUserById(Long id);

    void deleteUserById(Long id);
}
