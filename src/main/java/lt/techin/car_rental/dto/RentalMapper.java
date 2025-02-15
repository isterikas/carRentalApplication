package lt.techin.car_rental.dto;

import lt.techin.car_rental.dto.RentalRequestDTO;
import lt.techin.car_rental.dto.RentalResponseDTO;
import lt.techin.car_rental.model.Car;
import lt.techin.car_rental.model.Rental;
import lt.techin.car_rental.model.User;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestBody;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class RentalMapper {

  public static Rental toRental(@RequestBody RentalRequestDTO rentalRequestDTO, User user) {
    Rental rental = new Rental();
    rental.setUser(user);
    rental.setCar(rentalRequestDTO.car());
    rental.setRentalStart(LocalDateTime.now());
    rental.setRentalEnd(LocalDateTime.now().plusDays(rentalRequestDTO.days()));
    rental.setPrice(BigDecimal.valueOf(50).multiply(BigDecimal.valueOf(rentalRequestDTO.days())));
    return rental;
  }

  public static RentalResponseDTO toRentalResponseDTO(Rental rental, Car car) {
    String formattedStart = rental.getRentalStart().format(DateTimeFormatter.ISO_LOCAL_DATE);
    String formattedEnd = rental.getRentalEnd().format(DateTimeFormatter.ISO_LOCAL_DATE);
    return new RentalResponseDTO(car.getBrand() + " " + car.getModel(), formattedStart, formattedEnd, rental.getPrice());
  }

  public static RentalResponseDTO toRentalResponseDTO(Rental rental) {
    String formattedStart = rental.getRentalStart().format(DateTimeFormatter.ISO_LOCAL_DATE);
    String formattedEnd = rental.getRentalEnd().format(DateTimeFormatter.ISO_LOCAL_DATE);
    return new RentalResponseDTO(rental.getCar().getBrand() + " " + rental.getCar().getModel(), formattedStart, formattedEnd, rental.getPrice());
  }

  public static List<RentalResponseDTO> toRentalResponseDTOList(List<Rental> rentals) {
    return rentals.stream().map(RentalMapper::toRentalResponseDTO).toList();
  }
}