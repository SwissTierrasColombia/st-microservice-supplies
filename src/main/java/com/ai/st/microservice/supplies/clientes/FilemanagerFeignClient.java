package com.ai.st.microservice.supplies.clientes;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "st-microservice-filemanager")
public interface FilemanagerFeignClient {

}
