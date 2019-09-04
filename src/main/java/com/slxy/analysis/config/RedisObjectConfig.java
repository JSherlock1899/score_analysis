package com.slxy.analysis.config;

import com.slxy.analysis.model.Teacher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;

import java.net.UnknownHostException;

/**
 * @author: sherlock
 * @description:
 * @date: 2019/9/4 16:01
 */
@Configuration
public class RedisObjectConfig {

    @Bean
    public RedisTemplate<Object, Teacher> empRedisTemplate(
            RedisConnectionFactory redisConnectionFactory)
            throws UnknownHostException {
        RedisTemplate<Object, Teacher> template = new RedisTemplate<Object, Teacher>();
        template.setConnectionFactory(redisConnectionFactory);
        Jackson2JsonRedisSerializer<Teacher> ser = new Jackson2JsonRedisSerializer<Teacher>(Teacher.class);
        template.setDefaultSerializer(ser);
        return template;
    }
}
