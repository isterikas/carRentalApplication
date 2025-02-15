package lt.techin.car_rental.controller;

import lt.techin.car_rental.dto.UserMapper;
import lt.techin.car_rental.dto.UserRequestDTO;
import lt.techin.car_rental.dto.UserResponseDTO;
import lt.techin.car_rental.model.Role;
import lt.techin.car_rental.model.User;
import lt.techin.car_rental.service.RoleService;
import lt.techin.car_rental.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("/api")
public class UserController {

  private final UserService userService;
  private final PasswordEncoder passwordEncoder;

  @Autowired
  public UserController(UserService userService, PasswordEncoder passwordEncoder, RoleService roleService) {
    this.userService = userService;
    this.passwordEncoder = passwordEncoder;
  }

  @PostMapping("/users")
  public ResponseEntity<UserResponseDTO> saveUser(@RequestBody UserRequestDTO userRequestDTO) {
    User newUser = new User();
    newUser.setUsername(userRequestDTO.username());
    newUser.setPassword(passwordEncoder.encode(userRequestDTO.password()));
    newUser.setRoles(List.of(new Role(1L)));
    userService.save(newUser);
    return ResponseEntity.created(ServletUriComponentsBuilder.fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(newUser.getId())
                    .toUri())
            .body(UserMapper.toUserResponseDTO(newUser));
  }

  @GetMapping("/users")
  public ResponseEntity<List<UserResponseDTO>> getAllUsers() {
    return ResponseEntity.ok(UserMapper.toUserResponseDTOList(userService.findAllUsers()));
  }

  @GetMapping("/users/{id}")
  public ResponseEntity<?> getUser(@PathVariable long id, Authentication authentication) {
    User user = (User) authentication.getPrincipal();
    if (id != user.getId()) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized request.");
    }
    return ResponseEntity.status(HttpStatus.OK).body(UserMapper.toUserResponseDTO(userService.findUserById(id)));
  }

  @PutMapping("/users/{id}")
  public ResponseEntity<String> updateUserPassword(@PathVariable long id, @RequestBody UserRequestDTO userRequestDTO, Authentication authentication) {
    User user = (User) authentication.getPrincipal();
    if (id != user.getId()) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized request.");
    }
    User userFromDb = userService.findUserById(id);
    userFromDb.setPassword(passwordEncoder.encode(userRequestDTO.password()));
    userService.save(userFromDb);
    return ResponseEntity.status(HttpStatus.OK).body("Password update successfully.");
  }

  @DeleteMapping("/users/{id}")
  public ResponseEntity<String> deleteUser(@PathVariable long id, Authentication authentication) {
    User user = (User) authentication.getPrincipal();
    if (id != user.getId()) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized request.");
    }
    userService.deleteUserById(id);
    return ResponseEntity.status(HttpStatus.NO_CONTENT).body("User deleted successfully.");
  }
}
