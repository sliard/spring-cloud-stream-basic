package com.example.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;
import java.util.function.Function;

@Service
public class StreamService {
    private static final Logger LOG = LoggerFactory.getLogger(StreamService.class);

    private final AtomicInteger total = new AtomicInteger(0);

    @Bean
    public Consumer<Item> printItem() {
        return data -> LOG.info("Item " + data.name + "(" + data.price + ")");
    }

    @Bean
    public Function<Item, Integer> getPrice() {
        return item -> item.price;
    }

    @Bean
    public Consumer<Integer> calculTotal() {
        return price -> LOG.info("Total : " + total.addAndGet(price));
    }

}
