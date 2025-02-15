package lt.techin.car_rental.dto;

import lt.techin.car_rental.model.Car;
import org.springframework.security.core.Authentication;

public record RentalRequestDTO(Car car) {
}
