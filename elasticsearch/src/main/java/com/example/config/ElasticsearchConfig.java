package com.example.config;

import cn.hutool.core.date.LocalDateTimeUtil;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.RestClients;
import org.springframework.data.elasticsearch.config.AbstractElasticsearchConfiguration;
import org.springframework.data.elasticsearch.core.convert.ElasticsearchCustomConversions;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 李磊
 */
@Configuration
public class ElasticsearchConfig extends AbstractElasticsearchConfiguration {

    @Value("${lilei.elasticsearch.host-and-ports}")
    private String[] hostAndPorts;

    /**
     * 版本对应 https://docs.spring.io/spring-data/elasticsearch/docs/current/reference/html/#preface.versions
     */
    @Bean
    @Override
    public RestHighLevelClient elasticsearchClient() {
        ClientConfiguration config = ClientConfiguration.builder()
                .connectedTo(hostAndPorts)
                /*.usingSsl()
                .withConnectTimeout(Duration.ofSeconds(5))
                .withSocketTimeout(Duration.ofSeconds(3))
                .withDefaultHeaders(defaultHeaders)
                .withBasicAuth(username, password)*/
                .build();
        return RestClients.create(config).rest();
    }

    @Bean
    @Override
    public ElasticsearchCustomConversions elasticsearchCustomConversions() {
        List<Converter> converters = new ArrayList<>();
        converters.add(LongToLocalDateTimeConverter.INSTANCE);
        converters.add(LongToLocalDateConverter.INSTANCE);
        return new ElasticsearchCustomConversions(converters);
    }

    /**
     * 查询时 Long 转 LocalDateTime
     *
     * @ReadingConverter 保证仅在查询时使用
     */
    @ReadingConverter
    enum LongToLocalDateTimeConverter implements Converter<Long, LocalDateTime> {
        INSTANCE;

        @Override
        public LocalDateTime convert(Long source) {
            return LocalDateTimeUtil.of(source);
        }
    }

    /**
     * 查询时 Long 转 LocalDate
     */
    @ReadingConverter
    enum LongToLocalDateConverter implements Converter<Long, LocalDate> {
        INSTANCE;

        @Override
        public LocalDate convert(Long source) {
            return LocalDateTimeUtil.of(source).toLocalDate();
        }
    }
}
