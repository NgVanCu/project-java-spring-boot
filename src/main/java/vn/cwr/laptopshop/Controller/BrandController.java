package vn.cwr.laptopshop.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import vn.cwr.laptopshop.domain.Brand;
import vn.cwr.laptopshop.dto.BrandCreateDTO;
import vn.cwr.laptopshop.dto.BrandUpdateDTO;
import vn.cwr.laptopshop.service.BrandService;

import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping("/api/v1")
public class BrandController {
    private final BrandService brandService;

    public BrandController(BrandService brandService) {
        this.brandService = brandService;
    }

    @PostMapping(value = "/brands", consumes = { "multipart/form-data" })
    public ResponseEntity<?> addToBrand(@Valid @ModelAttribute BrandCreateDTO brandCreateDTO,
            BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            String errorMessage = bindingResult.getFieldError().getDefaultMessage();
            return ResponseEntity.badRequest().body(errorMessage);
        }
        Brand brand = brandService.addToBrand(brandCreateDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(brand);
    }

    @PutMapping(value = "/update-brand/{id}", consumes = { "multipart/form-data" })
    public ResponseEntity<?> updateBrand(@PathVariable Long id, @Valid @ModelAttribute BrandUpdateDTO brandUpdateDTO) {
        Brand brand = brandService.updateToBrand(brandUpdateDTO, id);
        return ResponseEntity.ok(brand);
    }

    @GetMapping("/brands")
    public ResponseEntity<List<Brand>> getAllBrand() {
        List<Brand> brands = brandService.getAllBrands();
        return ResponseEntity.ok(brands);
    }

    @GetMapping("/brand/{id}")
    public ResponseEntity<Brand> getBrandById(@PathVariable Long id) {
        Brand brand = brandService.getBrandById(id);
        return ResponseEntity.ok(brand);
    }

    @DeleteMapping("/brand/{id}")
    public ResponseEntity<?> deleteBrandById(@PathVariable Long id) {
        brandService.deleteBrandById(id);
        return ResponseEntity.noContent().build();
    }
}
