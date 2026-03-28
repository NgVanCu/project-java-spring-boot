package vn.cwr.laptopshop.controller;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import vn.cwr.laptopshop.domain.User;
import vn.cwr.laptopshop.dto.RegisterDTO;
import vn.cwr.laptopshop.dto.UserDTO;
import vn.cwr.laptopshop.dto.UserUpdateProfileDTO;
import vn.cwr.laptopshop.service.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/api/v1")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/users/register")
    public ResponseEntity<?> RegisterUser(@Valid @RequestBody RegisterDTO registerDTO,
            BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            String errorMessage = bindingResult.getFieldError().getDefaultMessage();
            return ResponseEntity.badRequest().body(errorMessage);
        }
        UserDTO userDTO = userService.registerUser(registerDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(userDTO);
    }

    @PutMapping(value = "/users/updateProfile/{id}", consumes = { "multipart/form-data" })
    public ResponseEntity<?> updateProfile(@Valid @ModelAttribute UserUpdateProfileDTO updateProfileDTO,
            @PathVariable("id") Long userId) {
        UserDTO userDTO = userService.updateProfile(updateProfileDTO, userId);
        return ResponseEntity.ok(userDTO);
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<?> getUserById(@PathVariable("id") Long userId) {
        UserDTO userDTO = userService.getUserById(userId);
        return ResponseEntity.ok(userDTO);
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<?> deleteUserById(@PathVariable("id") Long userId) {
        userService.deleteUserById(userId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/users")
    public ResponseEntity<Page<UserDTO>> getUsers(@RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size) {
        Page<UserDTO> userPage = userService.getUsers(page, size);
        return ResponseEntity.ok(userPage);
    }
}
