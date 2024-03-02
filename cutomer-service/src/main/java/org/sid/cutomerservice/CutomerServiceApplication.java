package org.sid.cutomerservice;

import org.sid.cutomerservice.entities.Customer;
import org.sid.cutomerservice.repositories.CustomerRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;

@SpringBootApplication
public class CutomerServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(CutomerServiceApplication.class, args);
    }

    @Bean
    CommandLineRunner start(CustomerRepository customerRepository) {
        return args -> {
            customerRepository.saveAll(List.of(
                    Customer.builder().name("anwar").email("anwar@gmail.com").build(),
                    Customer.builder().name("ahmed").email("ahmed@gmail.com").build(),
                    Customer.builder().name("aziz").email("aziz@gmail.com").build()
            ));

            customerRepository.findAll().forEach(System.out::println);
        };

    }
}
