package com.example.config;

import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.RestClients;
import org.springframework.data.elasticsearch.config.AbstractElasticsearchConfiguration;

/**
 * @author 李磊
 */
@Configuration
public class ElasticsearchConfig extends AbstractElasticsearchConfiguration {
    /**
     * 版本对应 https://docs.spring.io/spring-data/elasticsearch/docs/current/reference/html/#preface.versions
     */
    @Bean
    @Override
    public RestHighLevelClient elasticsearchClient() {
        ClientConfiguration config = ClientConfiguration.builder()
                .connectedTo("localhost:9200")
                /*.usingSsl()
                .withConnectTimeout(Duration.ofSeconds(5))
                .withSocketTimeout(Duration.ofSeconds(3))
                .withDefaultHeaders(defaultHeaders)
                .withBasicAuth(username, password)*/
                .build();
        return RestClients.create(config).rest();
    }
}