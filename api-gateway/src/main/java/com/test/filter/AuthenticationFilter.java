package com.test.filter;

import com.test.constant.CommonConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ResponseStatusException;

@Component
public class AuthenticationFilter extends AbstractGatewayFilterFactory<AuthenticationFilter.Config> {

    @Autowired
    private RouteValidator validator;

    @Autowired
    private WebClient.Builder webClientBuilder;

    public AuthenticationFilter() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();
            if (validator.isSecured.test(request)) {
                HttpHeaders headers = request.getHeaders();
                if (!headers.containsKey(HttpHeaders.AUTHORIZATION)) {
                    throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Missing authorization header");
                }
                String authHeader = headers.getFirst(HttpHeaders.AUTHORIZATION);
                if (authHeader != null && authHeader.startsWith(CommonConstants.BEARER_TOKEN_PREFIX)) {
                    String token = authHeader.substring(CommonConstants.BEARER_PREFIX_LENGTH);
                    String requestUri = exchange.getRequest().getURI().getPath();
                    System.out.println("Request URI: " + requestUri);
                    // Handle the response here
                    return webClientBuilder.build()
                            .get()
                            .uri("http://AUTHENTICATION/api/auth/authorize?token=" + token + "&uri=" + requestUri)
                            .retrieve()
                            .bodyToMono(String.class)
                            .flatMap(response -> {
                                System.out.println(response);
                                return chain.filter(exchange);
                            })
                            .onErrorMap(ex -> {
                                System.out.println("Invalid access!");
                                throw  new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Unauthorized access to application", ex);
                            });
                } else {
                    throw new  ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid authorization header");
                }
            }
            return chain.filter(exchange);
        };
    }

    public static class Config {

    }
}
