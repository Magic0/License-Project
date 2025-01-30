package com.mitocode.licenseservice.proxy;

import com.mitocode.common_models.model.dto.ClientDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "client-query-service")
public interface ClientFeign {
    @GetMapping("/v0/clients/{clientId}")
    ClientDTO getClientById(@PathVariable("clientId") String orderId);
}
