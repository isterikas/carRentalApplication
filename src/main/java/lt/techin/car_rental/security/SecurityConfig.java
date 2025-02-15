package lt.techin.car_rental.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http
            .authorizeHttpRequests(authorize -> authorize
                    .requestMatchers(HttpMethod.POST, "/api/cars").hasRole("ADMIN")
                    .requestMatchers(HttpMethod.GET, "/api/cars").hasRole("ADMIN")
                    .requestMatchers(HttpMethod.GET, "/api/cars/{id}").hasRole("ADMIN")
                    .requestMatchers(HttpMethod.GET, "/api/cars/available").permitAll()
                    .requestMatchers(HttpMethod.PUT, "/api/cars/{id}").hasRole("ADMIN")
                    .requestMatchers(HttpMethod.DELETE, "/api/cars/{id}").hasRole("ADMIN")
                    .requestMatchers(HttpMethod.POST, "/api/users").permitAll()
                    .requestMatchers(HttpMethod.GET, "/api/users").hasRole("ADMIN")
                    .requestMatchers(HttpMethod.GET, "/api/rentals/history").hasRole("ADMIN")
                    .anyRequest().authenticated())
            .csrf(c -> c.disable())
            .httpBasic(Customizer.withDefaults());
    return http.build();
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }
}