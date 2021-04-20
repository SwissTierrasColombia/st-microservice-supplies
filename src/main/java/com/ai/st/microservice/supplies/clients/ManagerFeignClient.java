package com.ai.st.microservice.supplies.clients;

import com.ai.st.microservice.supplies.dto.managers.MicroserviceManagerDto;
import com.ai.st.microservice.supplies.dto.managers.MicroserviceManagerProfileDto;
import feign.Feign;
import feign.codec.Encoder;
import feign.form.spring.SpringFormEncoder;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.support.SpringEncoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "st-microservice-managers", configuration = ManagerFeignClient.Configuration.class)
public interface ManagerFeignClient {

    @GetMapping("/api/managers/v1/managers/{managerId}")
    MicroserviceManagerDto findById(@PathVariable Long managerId);

    @GetMapping("/api/managers/v1/users/{userCode}/managers")
    MicroserviceManagerDto findByUserCode(@PathVariable Long userCode);

    @GetMapping("/api/managers/v1/users/{userCode}/profiles")
    List<MicroserviceManagerProfileDto> findProfilesByUser(@PathVariable Long userCode);

    class Configuration {

        @Bean
        Encoder feignFormEncoder(ObjectFactory<HttpMessageConverters> converters) {
            return new SpringFormEncoder(new SpringEncoder(converters));
        }

        @Bean
        @Scope("prototype")
        public Feign.Builder feignBuilder() {
            return Feign.builder();
        }

    }

}
