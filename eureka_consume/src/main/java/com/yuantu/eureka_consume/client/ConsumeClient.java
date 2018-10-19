package com.yuantu.eureka_consume.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "eureka-provider", fallback = ConsumeClient.ConsumeClientFallback.class)
public interface ConsumeClient {

    @PostMapping("/getPerson")
    public String getProviderPerson(@RequestParam("id") Integer id);

    //public String getProviderConnection();

    @Component
    static class ConsumeClientFallback implements ConsumeClient{
        @Override
        public String getProviderPerson(Integer id) {
            return "稍等一会，太挤了";
        }
    }

}
