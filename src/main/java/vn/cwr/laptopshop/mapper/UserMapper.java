package vn.cwr.laptopshop.mapper;

import org.springframework.stereotype.Component;

import vn.cwr.laptopshop.domain.User;
import vn.cwr.laptopshop.dto.UserDTO;

@Component
public class UserMapper {

    public UserDTO toDTO(User user) {
        return UserDTO.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .phone(user.getPhone())
                .address(user.getAddress())
                .build();
    }
}