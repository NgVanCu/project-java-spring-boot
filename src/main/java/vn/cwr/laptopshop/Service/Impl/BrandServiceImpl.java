package vn.cwr.laptopshop.service.Impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import org.springframework.util.StringUtils;

import vn.cwr.laptopshop.domain.Brand;
import vn.cwr.laptopshop.dto.BrandCreateDTO;
import vn.cwr.laptopshop.dto.BrandUpdateDTO;
import vn.cwr.laptopshop.repository.BrandRepository;
import vn.cwr.laptopshop.service.BrandService;
import vn.cwr.laptopshop.util.FileUploadUtil;

@Service
public class BrandServiceImpl implements BrandService {
    private final BrandRepository brandRepository;
    @Value("${project.upload.dir.brand}")
    private String brandUploadDir;

    public BrandServiceImpl(BrandRepository brandRepository) {
        this.brandRepository = brandRepository;
    }

    @Override
    @Transactional
    public Brand addToBrand(BrandCreateDTO brandCreateDTO) {
        if (brandRepository.existsByNameIgnoreCase(brandCreateDTO.getName())) {
            throw new RuntimeException("Tên hãng này đã tồn tại");
        }
        Brand brand = new Brand();
        brand.setName(brandCreateDTO.getName());
        brand.setCountry(brandCreateDTO.getCountry());
        brand.setDescription(brandCreateDTO.getDescription());
        MultipartFile logoBrand = brandCreateDTO.getLogo();
        if (logoBrand != null && !logoBrand.isEmpty()) {
            try {
                String originalFileName = StringUtils.cleanPath(logoBrand.getOriginalFilename());
                String fileName = System.currentTimeMillis() + "_" + originalFileName;
                FileUploadUtil.saveFile(brandUploadDir, fileName, logoBrand);
                brand.setLogo(fileName);
            } catch (Exception e) {
                throw new RuntimeException("Lỗi trong quá trình lưu ảnh đại diện: " + e.getMessage());
            }
        }
        return brandRepository.save(brand);
    }

    @Override
    @Transactional
    public Brand updateToBrand(BrandUpdateDTO brandUpdateDTO, Long id) {
        Brand existingBrand = brandRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Hãng này không tồn tại"));
        if (brandUpdateDTO.getName() != null && !brandUpdateDTO.getName().trim().isEmpty()) {
            existingBrand.setName(brandUpdateDTO.getName());
        }
        if (brandUpdateDTO.getCountry() != null && !brandUpdateDTO.getCountry().trim().isEmpty()) {
            existingBrand.setCountry(brandUpdateDTO.getCountry());
        }
        if (brandUpdateDTO.getDescription() != null && !brandUpdateDTO.getDescription().trim().isEmpty()) {
            existingBrand.setDescription(brandUpdateDTO.getDescription());
        }
        MultipartFile logoBrand = brandUpdateDTO.getLogo();
        if (logoBrand != null && !logoBrand.isEmpty()) {
            try {
                String originalFileName = StringUtils.cleanPath(logoBrand.getOriginalFilename());
                String fileName = System.currentTimeMillis() + "_" + originalFileName;
                FileUploadUtil.saveFile(brandUploadDir, fileName, logoBrand);
                existingBrand.setLogo(fileName);
            } catch (Exception e) {
                throw new RuntimeException("Lỗi trong quá trình lưu ảnh đại diện: " + e.getMessage());
            }
        }
        return brandRepository.save(existingBrand);
    }

    @Override
    public List<Brand> getAllBrands() {
        List<Brand> brands = brandRepository.findAll();
        return brands;
    }

    @Override
    public Brand getBrandById(Long id) {
        Brand brand = brandRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tồn tại hãng sản xuất này"));
        return brand;
    }

    @Override
    public void deleteBrandById(Long id) {
        this.getBrandById(id);
        brandRepository.deleteById(id);
    }
}
