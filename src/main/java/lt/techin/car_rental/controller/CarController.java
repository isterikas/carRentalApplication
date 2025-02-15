package lt.techin.car_rental.controller;

import lt.techin.car_rental.dto.CarMapper;
import lt.techin.car_rental.dto.CarRequestDTO;
import lt.techin.car_rental.dto.CarResponseDTO;
import lt.techin.car_rental.model.Car;
import lt.techin.car_rental.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CarController {
  private CarService carService;

  @Autowired
  public CarController(CarService carService) {
    this.carService = carService;
  }

  @PostMapping("/cars")
  public ResponseEntity<CarResponseDTO> createCar(@RequestBody CarRequestDTO carRequestDTO) {
    Car savedCar = new Car(carRequestDTO.brand(), carRequestDTO.model(), carRequestDTO.year());
    carService.save(savedCar);
    return ResponseEntity.created(ServletUriComponentsBuilder.fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(savedCar.getId())
                    .toUri())
            .body(CarMapper.toResponseDTO(savedCar));
  }

  @GetMapping("/cars")
  public ResponseEntity<List<CarResponseDTO>> getCars() {
    return ResponseEntity.ok().body(CarMapper.toCarResponseDTOList(carService.getAllCars()));
  }

  @GetMapping("/cars/available")
  public ResponseEntity<List<CarResponseDTO>> getAvailableCars() {
    List<Car> availableCars = carService.getAllCars().stream().filter(car -> car.getStatus().equals("AVAILABLE")).toList();
    return ResponseEntity.ok().body(CarMapper.toCarResponseDTOList(availableCars));
  }

  @GetMapping("/cars/{id}")
  public ResponseEntity<CarResponseDTO> getCarById(@PathVariable long id) {
    if (!carService.existsById(id)) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
    return ResponseEntity.ok().body(CarMapper.toResponseDTO(carService.getCarById(id)));
  }

  @PutMapping("/cars/{id}")
  public ResponseEntity<CarResponseDTO> editCarSpecification(@PathVariable long id, @RequestBody CarRequestDTO carRequestDTO) {
    if (!carService.existsById(id)) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
    Car carFromDb = carService.getCarById(id);
    carFromDb.setBrand(carRequestDTO.brand());
    carFromDb.setModel(carRequestDTO.model());
    carFromDb.setYear(carRequestDTO.year());
    carService.save(carFromDb);
    return ResponseEntity.ok().body(CarMapper.toResponseDTO(carFromDb));
  }

  @DeleteMapping("/cars/{id}")
  public ResponseEntity<?> deleteCar(@PathVariable long id) {
    if (!carService.existsById(id)) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Car does not exist.");
    }
    if (carService.getCarById(id).getStatus().equals("RENTED")) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Cannot delete a rented car.");
    }
    carService.deleteCarbyId(id);
    return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Car deleted successfully.");
  }


}
