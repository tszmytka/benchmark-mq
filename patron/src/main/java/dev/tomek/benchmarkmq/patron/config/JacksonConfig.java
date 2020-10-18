package dev.tomek.benchmarkmq.patron.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import dev.tomek.benchmarkmq.common.Airplane;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JacksonConfig {
    @Bean
    public ObjectReader requestReader(ObjectMapper objectMapper) {
        return objectMapper.readerFor(Airplane.class);
    }
}
