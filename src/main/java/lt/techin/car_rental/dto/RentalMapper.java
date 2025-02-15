package lt.techin.car_rental.dto;

import lt.techin.car_rental.model.Car;
import lt.techin.car_rental.model.Rental;
import lt.techin.car_rental.model.User;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class RentalMapper {

  public static Rental toRental(@RequestBody RentalRequestDTO rentalRequestDTO, User user) {
    Rental rental = new Rental();
    rental.setUser(user);
    rental.setCar(rentalRequestDTO.car());
    rental.setRentalStart(LocalDateTime.now());
    rental.setRentalEnd(null);
    rental.setPrice(null);
    return rental;
  }

  public static RentalResponseDTO toRentalResponseDTO(Rental rental, Car car) {
    String formattedStart = rental.getRentalStart().format(DateTimeFormatter.ISO_LOCAL_DATE);
    return new RentalResponseDTO(car.getBrand() + " " + car.getModel(), formattedStart);
  }

  public static RentalResponseDTO toRentalResponseDTO(Rental rental) {
    String formattedStart = rental.getRentalStart().format(DateTimeFormatter.ISO_LOCAL_DATE);
    return new RentalResponseDTO(rental.getCar().getBrand() + " " + rental.getCar().getModel(), formattedStart);
  }

  public static List<RentalResponseDTO> toRentalResponseDTOList(List<Rental> rentals) {
    return rentals.stream().map(RentalMapper::toRentalResponseDTO).toList();
  }

  public static RentalReturnResponseDTO toRentalReturnResponseDTO(Rental rental) {
    long days = ChronoUnit.DAYS.between(rental.getRentalStart(), rental.getRentalEnd());
    return new RentalReturnResponseDTO(days, rental.getPrice());
  }
}