package lt.techin.car_rental.dto;

import java.math.BigDecimal;

public record RentalReturnResponseDTO(long days, BigDecimal price) {


}
