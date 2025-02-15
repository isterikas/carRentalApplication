package lt.techin.car_rental.service;

import lt.techin.car_rental.model.Role;
import lt.techin.car_rental.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleService {
  private RoleRepository roleRepository;

  @Autowired
  public RoleService(RoleRepository roleRepository) {
    this.roleRepository = roleRepository;
  }

  public Role getDefaultRole() {
    return roleRepository.findById(1L).get();
  }
}
