package vn.cwr.laptopshop.dto;

import org.springframework.web.multipart.MultipartFile;

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

public class UserUpdateProfileDTO {

    private String firstName;

    private String lastName;

    private String phone;

    private String address;

    private MultipartFile avatar;
}
