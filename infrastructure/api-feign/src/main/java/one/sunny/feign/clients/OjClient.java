package one.sunny.feign.clients;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(value = "API-OJ")
public interface OjClient {
    
}
