package vn.cwr.laptopshop.service;

import java.util.List;

import vn.cwr.laptopshop.domain.Brand;
import vn.cwr.laptopshop.dto.BrandCreateDTO;
import vn.cwr.laptopshop.dto.BrandUpdateDTO;

public interface BrandService {
    Brand addToBrand(BrandCreateDTO brandCreateDTO);

    Brand updateToBrand(BrandUpdateDTO brandUpdateDTO, Long id);

    List<Brand> getAllBrands();

    Brand getBrandById(Long id);

    void deleteBrandById(Long id);
}
