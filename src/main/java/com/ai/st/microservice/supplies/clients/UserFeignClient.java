package com.ai.st.microservice.supplies.clients;

import com.ai.st.microservice.supplies.dto.administration.MicroserviceUserDto;
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

@FeignClient(name = "st-microservice-administration", configuration = UserFeignClient.Configuration.class)
public interface UserFeignClient {

    @GetMapping("/api/administration/v1/users/{id}")
    MicroserviceUserDto findById(@PathVariable Long id);

    @GetMapping("/api/administration/v1/users/token")
    MicroserviceUserDto findByToken(@RequestParam(name = "token") String token);


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
