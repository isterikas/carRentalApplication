package lt.techin.car_rental.model;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDateTime;

@Entity
@Table(name = "rentals")
public class Rental {
  @Id
  private long id;
  @ManyToOne
  private User user;
  @ManyToOne(fetch = FetchType.EAGER)
  private Car car;
  private LocalDateTime rentalStart;
  private LocalDateTime rentalEnd;
  private BigDecimal price;

  public Rental(User user, Car car, LocalDateTime rentalStart, LocalDateTime rentalEnd, BigDecimal price) {
    this.user = user;
    this.car = car;
    this.rentalStart = rentalStart;
    this.rentalEnd = rentalEnd;
    this.price = price;
  }

  public Rental() {
  }

  public long getId() {
    return id;
  }

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }

  public Car getCar() {
    return car;
  }

  public void setCar(Car car) {
    this.car = car;
  }

  public LocalDateTime getRentalStart() {
    return rentalStart;
  }

  public void setRentalStart(LocalDateTime rentalStart) {
    this.rentalStart = rentalStart;
  }

  public LocalDateTime getRentalEnd() {
    return rentalEnd;
  }

  public void setRentalEnd(LocalDateTime rentalEnd) {
    this.rentalEnd = rentalEnd;
  }

  public BigDecimal getPrice() {
    return price;
  }

  public void setPrice(BigDecimal price) {
    this.price = price;
  }
}
