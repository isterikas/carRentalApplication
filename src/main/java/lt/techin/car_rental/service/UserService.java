package lt.techin.car_rental.service;

import lt.techin.car_rental.model.User;
import lt.techin.car_rental.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

  private UserRepository userRepository;

  @Autowired
  public UserService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  public Optional<User> findByUsername(String username) {
    return userRepository.findUserByUsername(username);
  }

  public void save(User user) {
    userRepository.save(user);
  }

  public List<User> findAllUsers() {
    return userRepository.findAll();
  }

  public User findUserById(long id) {
    return userRepository.findById(id).get();
  }

  public boolean userExistsById(long id) {
    return userRepository.existsById(id);
  }

  public void deleteUserById(long id) {
    userRepository.deleteById(id);
  }
}
