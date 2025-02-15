package lt.techin.car_rental.service;

import lt.techin.car_rental.model.Car;
import lt.techin.car_rental.repository.CarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CarService {
  private CarRepository carRepository;

  @Autowired
  public CarService(CarRepository carRepository) {
    this.carRepository = carRepository;
  }

  public void save(Car car) {
    carRepository.save(car);
  }

  public List<Car> getAllCars() {
    return carRepository.findAll();
  }

  public boolean existsById(long id) {
    return carRepository.existsById(id);
  }

  public Car getCarById(long id) {
    return carRepository.findById(id).get();
  }

  public void deleteCarbyId(long id) {
    carRepository.deleteById(id);
  }
}
