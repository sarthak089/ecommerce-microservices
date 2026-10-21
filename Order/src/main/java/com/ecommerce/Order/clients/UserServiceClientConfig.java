package com.ecommerce.Order.clients;

import org.springframework.cloud.client.loadbalancer.LoadBalancerInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

import java.util.Optional;

@Configuration
public class UserServiceClientConfig {

//    @Bean
//    @LoadBalanced
//    @Qualifier("lbRestClientBuilder")
//    public RestClient.Builder resClientBuilder (){
//        return  RestClient.builder();
//    }
//
//    @Bean
//    public ProductServiceClient restClientInterface( @Qualifier("lbRestClientBuilder") RestClient.Builder restClientBuilder) {
//        RestClient restClient = restClientBuilder
//                .baseUrl("http://product-service")
//                .build();
//
//        RestClientAdapter adapter = RestClientAdapter.create(restClient);
//
//        HttpServiceProxyFactory factory = HttpServiceProxyFactory
//                .builderFor(adapter)
//                .build();
//
//        return factory.createClient(ProductServiceClient.class);
//    }

    @Bean
    public UserServiceClient userServiceClient(LoadBalancerInterceptor lbInterceptor) {
        RestClient restClient = RestClient.builder()          // fresh builder, not a bean
                .baseUrl("http://user-service")
                .defaultStatusHandler(HttpStatusCode::is4xxClientError,((request, response) -> Optional.empty()))
                .requestInterceptor(lbInterceptor)
                .build();

        HttpServiceProxyFactory factory = HttpServiceProxyFactory
                .builderFor(RestClientAdapter.create(restClient))
                .build();

        return factory.createClient(UserServiceClient.class);
    }

}
