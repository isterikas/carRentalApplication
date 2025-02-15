package lt.techin.car_rental.dto;

public record CarRequestDTO(
        String brand,
        String model,
        int year
) {
}
