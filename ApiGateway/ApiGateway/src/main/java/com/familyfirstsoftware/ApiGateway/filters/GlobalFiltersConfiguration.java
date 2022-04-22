package com.familyfirstsoftware.ApiGateway.filters;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import reactor.core.publisher.Mono;

/*
    prefilters the lower the index the higher the priority
    post filters is the opposite
 */

@Configuration
public class GlobalFiltersConfiguration  {

    final Logger logger = LoggerFactory.getLogger(GlobalFiltersConfiguration.class);

    @Bean
    @Order(1)
    public GlobalFilter secondPreFilter() {

        return (exchange, chain) ->  {

            logger.info("My second global pre-filter is executed...");

            return chain.filter(exchange).then(Mono.fromRunnable(() -> {
                logger.info("My third  post-filter was executed...");
            }));
        };

    }


    @Bean
    @Order(2)
    public GlobalFilter thirdPreFilter() {

        return (exchange, chain) ->  {

            logger.info("My third global pre-filter is executed...");

            return chain.filter(exchange).then(Mono.fromRunnable(() -> {
                logger.info("My second post-filter was executed...");
            }));
        };

    }

    // first post filter executed then 2 then 1
    @Bean
    @Order(3)
    public GlobalFilter fourthPreFilter() {

        return (exchange, chain) ->  {

            logger.info("My fourth global pre-filter is executed...");

            return chain.filter(exchange).then(Mono.fromRunnable(() -> {
                logger.info("My first post filter is executed");
            }));
        };

    }

}
