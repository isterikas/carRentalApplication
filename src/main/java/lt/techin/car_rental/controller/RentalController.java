package lt.techin.car_rental.controller;

import lt.techin.car_rental.dto.RentalMapper;
import lt.techin.car_rental.dto.RentalRequestDTO;
import lt.techin.car_rental.dto.RentalResponseDTO;
import lt.techin.car_rental.model.Car;
import lt.techin.car_rental.model.Rental;
import lt.techin.car_rental.model.User;
import lt.techin.car_rental.service.CarService;
import lt.techin.car_rental.service.RentalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("/api")
public class RentalController {
  private final CarService carService;
  private final RentalService rentalService;

  @Autowired
  public RentalController(CarService carService, RentalService rentalService) {
    this.carService = carService;
    this.rentalService = rentalService;
  }

  @PostMapping("/rentals")
  public ResponseEntity<?> addRental(@RequestBody RentalRequestDTO rentalRequestDTO, Authentication authentication) {
    if (carService.getCarById(rentalRequestDTO.car().getId()).getStatus().equals("RENTED")) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Car is already rented");
    }
    User user = ((User) authentication.getPrincipal());
    if (user.getRentals().size() >= 2) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("One user can only rent 2 cars maximum at once");
    }

    Car carFromDb = carService.getCarById(rentalRequestDTO.car().getId());
    carFromDb.setStatus("RENTED");
    carService.save(carFromDb);
    Rental savedRental = rentalService.saveRental(RentalMapper.toRental(rentalRequestDTO, user));

    return ResponseEntity.created(ServletUriComponentsBuilder.fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(savedRental.getId()).toUri())
            .body(RentalMapper.toRentalResponseDTO(savedRental, carFromDb));
  }

  @GetMapping("/rentals/my")
  public ResponseEntity<List<RentalResponseDTO>> getMyRentals(Authentication authentication) {
    return ResponseEntity.status(HttpStatus.OK).body(RentalMapper.toRentalResponseDTOList(rentalService.findAllRentals().stream().
            filter(rental -> rental.getUser().getId() == ((User) authentication.getPrincipal()).getId()).toList()));
  }

  @PutMapping("/rentals/return/{rentalId}")
  public ResponseEntity<?> returnCar(@PathVariable long rentalId) {
    if (!rentalService.rentalExistsById(rentalId)) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Rental not found.");
    }
    Rental rentalFromDb = rentalService.getRentalById(rentalId);
    Car car = rentalFromDb.getCar();
    car.setStatus("AVAILABLE");
    carService.save(car);
    rentalService.saveRental(rentalFromDb);
    return ResponseEntity.status(HttpStatus.OK).body("Car returned successfully");
  }
}
