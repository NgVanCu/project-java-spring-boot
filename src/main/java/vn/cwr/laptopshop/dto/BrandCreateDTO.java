package vn.cwr.laptopshop.dto;

import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BrandCreateDTO {
    @NotBlank(message = "Tên hãng không được để trống")
    private String name;

    private String country;

    @NotNull(message = "Ảnh của hãng không được để trống")
    private MultipartFile logo;

    private String description;
}
