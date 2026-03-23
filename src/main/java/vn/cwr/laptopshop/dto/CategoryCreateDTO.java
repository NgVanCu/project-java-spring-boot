package vn.cwr.laptopshop.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CategoryCreateDTO {
    @NotBlank(message = "Tên danh mục không được để trống")
    private String name;

    private String description;
}
