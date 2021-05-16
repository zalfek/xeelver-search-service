package com.xeelver.searchservice.configs;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;


import static io.lettuce.core.ReadFrom.REPLICA_PREFERRED;


@RequiredArgsConstructor
@Configuration
public class RedisConfig {
//    private final RedisProperties redisProperties;

    @Value("${redisPassword}")
    private String redisPassword;

    @Value("${redisHost}")
    private String redisHost;

    @Value("${redisPort}")
    private int redisPort;


    @Bean
    protected LettuceConnectionFactory redisConnectionFactory() {
//        RedisSentinelConfiguration sentinelConfig = new RedisSentinelConfiguration()
//                .master(redisProperties.getSentinel().getMaster());
//        redisProperties.getSentinel().getNodes().forEach(s -> sentinelConfig.sentinel(s, redisProperties.getPort()));
//        sentinelConfig.setPassword(RedisPassword.of(redisProperties.getPassword()));
//        LettuceClientConfiguration clientConfig = LettuceClientConfiguration.builder()
//                .readFrom(REPLICA_PREFERRED)
//                .build();
        RedisStandaloneConfiguration serverConfig = new RedisStandaloneConfiguration(redisHost, redisPort);
        serverConfig.setPassword(redisPassword);
        return new LettuceConnectionFactory(serverConfig, LettuceClientConfiguration.defaultConfiguration());
    }

}

