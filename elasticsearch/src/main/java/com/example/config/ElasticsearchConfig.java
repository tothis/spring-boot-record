package com.example.config;

import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.RestClients;
import org.springframework.data.elasticsearch.config.AbstractElasticsearchConfiguration;

/**
 * @author 李磊
 * @datetime 2020/6/21 15:07
 * @description
 */
@Configuration
public class ElasticsearchConfig extends AbstractElasticsearchConfiguration {
    // https://docs.spring.io/spring-data/elasticsearch/docs/current/reference/html
    @Bean
    @Override
    public RestHighLevelClient elasticsearchClient() {
        ClientConfiguration config = ClientConfiguration.builder()
                .connectedTo("192.168.92.128:9200")
                // .withConnectTimeout(Duration.ofSeconds(5))
                // .withSocketTimeout(Duration.ofSeconds(3))
                // .usingSsl()
                // .withDefaultHeaders(defaultHeaders)
                // .withBasicAuth(username, password)
                .build();
        return RestClients.create(config).rest();
    }
}