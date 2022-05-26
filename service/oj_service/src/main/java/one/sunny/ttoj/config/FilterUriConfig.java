package one.sunny.ttoj.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Configuration
public class FilterUriConfig {
    @Bean
    public List<String> ignoringList(){
        return new ArrayList<>(Arrays.asList(
                "**.css",
                "**.js",
                "**.png",
                "**.jpg",
                "/v2/api-docs",
                "/definitions/**",
                "/swagger**/**",
                "/configuration/**",
                "/webjars/**"));
    }
}
