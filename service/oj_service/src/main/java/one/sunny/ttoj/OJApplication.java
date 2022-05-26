package one.sunny.ttoj;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;


@ComponentScan("one.sunny")
@SpringBootApplication(scanBasePackages = {"one.sunny"})
@MapperScan("one.sunny.ttoj.mapper")
@EnableFeignClients(basePackages = "one.sunny.feign.clients")
public class OJApplication {
    public static void main(String[] args) {
        SpringApplication.run(OJApplication.class, args);
    }
}
