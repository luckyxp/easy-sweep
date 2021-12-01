package com.xy.sweep;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author climb.xu
 * @date 2021/11/30 12:45
 */
@SpringBootApplication(scanBasePackages = "com.xy")
public class App {
    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }
}
