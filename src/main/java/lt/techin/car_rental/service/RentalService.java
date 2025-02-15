package lt.techin.car_rental.service;

import lt.techin.car_rental.model.Rental;
import lt.techin.car_rental.repository.RentalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RentalService {

  private final RentalRepository rentalRepository;

  @Autowired
  public RentalService(RentalRepository rentalRepository) {
    this.rentalRepository = rentalRepository;
  }

  public Rental saveRental(Rental rental) {
    return rentalRepository.save(rental);
  }

  public Rental getRentalById(long id) {
    return rentalRepository.findById(id).get();
  }

  public boolean rentalExistsById(long id) {
    return rentalRepository.existsById(id);
  }

  public List<Rental> findAllRentals() {
    return rentalRepository.findAll();
  }
}
