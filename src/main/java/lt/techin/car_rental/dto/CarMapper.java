package lt.techin.car_rental.dto;

import lt.techin.car_rental.model.Car;

import java.util.List;

public class CarMapper {

  public static CarResponseDTO toResponseDTO(Car car) {
    return new CarResponseDTO(car.getId(), car.getBrand(), car.getModel(), car.getYear(), car.getStatus());
  }

  public static List<CarResponseDTO> toCarResponseDTOList(List<Car> cars) {
    return cars.stream().map(CarMapper::toResponseDTO).toList();
  }

}
