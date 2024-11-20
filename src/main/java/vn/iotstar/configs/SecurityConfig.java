package vn.iotstar.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public UserDetailsService userDetailsService(PasswordEncoder encoder) {
        UserDetails admin = User.withUsername("quynh")
            .password(encoder.encode("123"))
            .roles("ADMIN")
            .build();

        UserDetails user = User.withUsername("user")
            .password(encoder.encode("123"))
            .roles("USER")
            .build();

        return new InMemoryUserDetailsManager(admin, user);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http.csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/hello").permitAll() // Không yêu cầu đăng nhập
                .requestMatchers("/customer/all").hasRole("ADMIN") // Chỉ ADMIN được truy cập
                .requestMatchers("/customer/*").hasRole("USER") // Chỉ USER được truy cập
                .anyRequest().authenticated() // Các endpoint khác yêu cầu đăng nhập
            )
            .formLogin(form -> form
                .defaultSuccessUrl("/hello", true) // Sau khi login chuyển tới /hello
                .permitAll()
            )
            .exceptionHandling(ex -> ex
                .accessDeniedPage("/error") // Trang lỗi cho 403
            )
            .build();
    }
}
