package com.wrathenn.gateway.service.kafka

import org.apache.kafka.clients.admin.AdminClientConfig
import org.apache.kafka.clients.admin.NewTopic
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.core.KafkaAdmin

@Configuration
class KafkaTopicConfig {

//    @Bean
//    fun kafkaAdmin(
//        @Value("\${bnuuy.host}") host: String,
//        @Value("\${bnuuy.port}") port: Int,
//    ): KafkaAdmin {
//        val configs: MutableMap<String, Any> = HashMap()
//        configs[AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG] = "$host:$port"
//        configs["security.protocol"] = "SASL_PLAINTEXT"
//        configs["sasl.mechanism"] = "PLAIN"
//        configs["sasl.jaas.config"] = "org.apache.kafka.common.security.plain.PlainLoginModule required " +
//            "username=user" + "password=password;"
//        return KafkaAdmin(configs)
//    }
//
//    @Bean
//    fun topic1(
//        @Value("\${bnuuy.topics}") topicName: String,
//    ): NewTopic? {
//        return NewTopic(topicName, 1, 1.toShort())
//    }
}