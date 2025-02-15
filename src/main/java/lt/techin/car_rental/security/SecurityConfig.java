package lt.techin.car_rental.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http
            .authorizeHttpRequests(authorize -> authorize
                    .requestMatchers(HttpMethod.POST, "/api/cars").permitAll()
                    .requestMatchers(HttpMethod.GET, "/api/cars").permitAll()
                    .requestMatchers(HttpMethod.GET, "/api/cars/{id}").permitAll()
                    .requestMatchers(HttpMethod.PUT, "/api/cars").permitAll()
                    .requestMatchers(HttpMethod.DELETE, "/api/cars").permitAll()
                    .requestMatchers(HttpMethod.POST, "/api/users").permitAll()
                    .requestMatchers(HttpMethod.GET, "/api/users").permitAll()
                    .requestMatchers(HttpMethod.PUT, "/api/users/{id}").permitAll()
                    .requestMatchers(HttpMethod.DELETE, "/api/users/{id}").permitAll()
                    .anyRequest().permitAll())
            .csrf(c -> c.disable())
            .httpBasic(Customizer.withDefaults());
    return http.build();
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }
}