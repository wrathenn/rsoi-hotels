package com.wrathenn.gateway.service.kafka

import org.apache.kafka.clients.CommonClientConfigs
import org.apache.kafka.clients.producer.ProducerConfig
import org.apache.kafka.common.config.SaslConfigs
import org.apache.kafka.common.security.plain.PlainLoginModule
import org.apache.kafka.common.serialization.StringSerializer
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.core.*
import org.springframework.kafka.support.serializer.JsonSerializer


@Configuration
class KafkaProducerConfig {
    private val logger = LoggerFactory.getLogger(this::class.java)

//    @Bean
    fun producerFactory(
        @Value("\${kafka.bootstrap-servers}") bootstrapServers: String,
        @Value("\${kafka.sasl.enabled}") saslEnabled: Boolean,
        @Value("\${kafka.sasl.username}") username: String?,
        @Value("\${kafka.sasl.password}") password: String?
    ): ProducerFactory<String, Any> {
        val config = mutableMapOf<String, Any>()
        config[ProducerConfig.BOOTSTRAP_SERVERS_CONFIG] = bootstrapServers
        config[ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG] = StringSerializer::class.java
        config[ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG] = JsonSerializer::class.java
        if (saslEnabled) {
            config[CommonClientConfigs.SECURITY_PROTOCOL_CONFIG] = "SASL_PLAINTEXT"
            config[SaslConfigs.SASL_MECHANISM] = "PLAIN"
            config[SaslConfigs.SASL_JAAS_CONFIG] = String.format(
                "%s required username=\"%s\" password=\"%s\";", PlainLoginModule::class.java.name, username, password
            )
        }
        return DefaultKafkaProducerFactory(config)
    }

//    @Bean
    fun kafkaTemplate(producerFactory: ProducerFactory<String, Any>): KafkaTemplate<String, Any> {
        return KafkaTemplate(producerFactory)
    }
}