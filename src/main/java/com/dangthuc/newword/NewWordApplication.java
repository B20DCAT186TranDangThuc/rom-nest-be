package com.dangthuc.newword;

import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class NewWordApplication {

    public static void main(String[] args) {
//        SpringApplication.run(NewWordApplication.class, args);
        new SpringApplicationBuilder(NewWordApplication.class)
                .bannerMode(Banner.Mode.CONSOLE).build().run(args);
    }

}
