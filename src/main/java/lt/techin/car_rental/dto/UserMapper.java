package lt.techin.car_rental.dto;

import lt.techin.car_rental.model.User;

import java.util.List;

public class UserMapper {

  public static UserResponseDTO toUserResponseDTO(User user) {
    return new UserResponseDTO(user.getUsername(), user.getPassword());
  }

  public static List<UserResponseDTO> toUserResponseDTOList(List<User> users) {
    return users.stream().map(UserMapper::toUserResponseDTO).toList();
  }

}
