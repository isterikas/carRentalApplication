package lt.techin.car_rental.repository;

import lt.techin.car_rental.model.Car;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarRepository extends JpaRepository<Car, Long> {


}
