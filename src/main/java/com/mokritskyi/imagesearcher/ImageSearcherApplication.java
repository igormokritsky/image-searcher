package com.mokritskyi.imagesearcher;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class ImageSearcherApplication {

    public static void main(String[] args) {
        SpringApplication.run(ImageSearcherApplication.class, args);
    }

}
