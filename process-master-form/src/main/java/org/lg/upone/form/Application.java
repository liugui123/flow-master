package org.lg.upone.form;

import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ImportResource;

/**
 * Create Time:2020-03-25
 * User: liugui
 * Email: 
 */
@SpringBootApplication
@ImportResource("classpath:spring/*.xml")
@EnableDubbo
@ComponentScan("org.lg.*")
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
