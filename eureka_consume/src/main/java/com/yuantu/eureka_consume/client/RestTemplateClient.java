package com.yuantu.eureka_consume.client;

import com.netflix.hystrix.contrib.javanica.annotation.DefaultProperties;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.yuantu.eureka_consume.domian.PamDTO;
import com.yuantu.eureka_consume.entity.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@DefaultProperties(defaultFallback = "defaultFallback")
public class RestTemplateClient {

    private final RestTemplate restTemplate;
    private final ConsumeClient consumeClient;
    @Autowired
    public RestTemplateClient(RestTemplate restTemplate, ConsumeClient consumeClient) {
        this.restTemplate = restTemplate;
        this.consumeClient = consumeClient;
    }

    /**
     *
     * 通过new的方式获取restTemplate
     *
     * */
    @GetMapping("/getString")
    public String getConsumePerson(){
        RestTemplate restTemplate = new RestTemplate();
        String string = restTemplate.getForObject("http://localhost:8091/getString", String.class);
        System.out.println(string);
        return "person";
    }

     //通过config配置获取restTemplate
     //1.在 @Autowired注入时，注入的还是 private RestTemplate restTemplate;不是config类(此时的config类是RestTemplateConfig)
     //2.config配置文件中使用@LoadBalanced时，调用要使用服务名
    @GetMapping("/getConfigString")
    @HystrixCommand(commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "3000")
    })
    public String getPerson(){
        String string = restTemplate.getForObject("http://EUREKA-PROVIDER/getString", String.class);
        System.out.println(string);
        return "success person";
    }

    /***
     *
     * 熔断
     * @return string
     *
     */
    @GetMapping("/getConfigHystrix")
    @HystrixCommand(fallbackMethod = "fallback")
    public String getConfigHystrix(){
        String string = restTemplate.getForObject("http://EUREKA-PROVIDER/getString", String.class);
        System.out.println(string);
        return "success person";
    }
    private String fallback(){
        return "现在拥挤请稍后";
    }
    private String defaultFallback(){
        return "[现在拥挤请稍后]";
    }

    //******************************************************
    //postForObject请求
    @GetMapping("/postPerson")
    public String postPerson(){
        PamDTO pamDTO = new PamDTO();
        pamDTO.setId(1);
        String person = restTemplate.postForObject("http://EUREKA-PROVIDER/getPersonRest", pamDTO, String.class);
        System.out.println(person);
        return "success";
    }

    @GetMapping("/postPerson1")
    public String postPerson1(){
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Person> person = restTemplate.postForEntity("http://localhost:8091/getPerson", 1, Person.class);
        System.out.println(person);
        return "success";
    }

    @PostMapping("/clientGetPerson")
    @HystrixCommand
    public String clientGetPerson(@RequestParam("id")Integer id){
        if (id ==1){
            throw new RuntimeException("client exception");
        }
        String providerPerson = consumeClient.getProviderPerson(id);
        return "client success";
    }

}
