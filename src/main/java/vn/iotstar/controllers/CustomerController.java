package vn.iotstar.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import vn.iotstar.entity.Customer;

@RestController
@EnableMethodSecurity
public class CustomerController {

    final private List<Customer> customers = List.of(
        Customer.builder().id("001").name("Trần Diễm Quỳnh").email("diemquynhspkt@gmail.com").build(),
        Customer.builder().id("002").name("Diễm Quỳnh").email("diemquynh@gmail.com").build()
    );

    @GetMapping("/hello")
    public ResponseEntity<String> hello() {
        return ResponseEntity.ok("Hello is Guest");
    }

    @GetMapping("/customer/all")
    @PreAuthorize("hasRole('ADMIN')") // Chỉ ADMIN được truy cập
    public ResponseEntity<List<Customer>> getCustomerList() {
        return ResponseEntity.ok(this.customers);
    }

    @GetMapping("/customer/{id}")
    @PreAuthorize("hasRole('USER')") // Chỉ USER được truy cập
    public ResponseEntity<Customer> getCustomerById(@PathVariable String id) {
        return ResponseEntity.ok(
            this.customers.stream()
                .filter(customer -> customer.getId().equals(id))
                .findFirst()
                .orElse(null)
        );
    }
}
