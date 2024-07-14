package com.wrathenn.gateway.service.kafka

import com.wrathenn.gateway.service.models.bnuuy.KafkaRequest
import org.apache.kafka.clients.CommonClientConfigs
import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.common.config.SaslConfigs
import org.apache.kafka.common.security.plain.PlainLoginModule
import org.apache.kafka.common.serialization.StringDeserializer
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory
import org.springframework.kafka.core.ConsumerFactory
import org.springframework.kafka.core.DefaultKafkaConsumerFactory
import org.springframework.kafka.support.serializer.ErrorHandlingDeserializer
import org.springframework.kafka.support.serializer.JsonDeserializer

@Configuration
class KafkaConsumerConfig {

    @Bean
    fun kafkaListenerContainerFactory(consumerFactory: ConsumerFactory<Any?, Any?>): ConcurrentKafkaListenerContainerFactory<Any, Any>? {
        val factory = ConcurrentKafkaListenerContainerFactory<Any, Any>()
        factory.consumerFactory = consumerFactory
        return factory
    }

    @Bean
    fun consumerFactory(
        @Value("\${kafka.bootstrap-servers}") bootstrapServers: String,
        @Value("\${kafka.sasl.enabled}") saslEnabled: Boolean,
        @Value("\${kafka.sasl.username}") username: String?,
        @Value("\${kafka.sasl.password}") password: String?,
    ): ConsumerFactory<Any, Any> {
        val config = mutableMapOf<String, Any>()
        config[ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG] = bootstrapServers
        config[ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG] = StringDeserializer::class.java
        config[ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG] = ErrorHandlingDeserializer::class.java
        config[ErrorHandlingDeserializer.VALUE_DESERIALIZER_CLASS] = JsonDeserializer::class.java
        config[JsonDeserializer.VALUE_DEFAULT_TYPE] = KafkaRequest::class.java.canonicalName
        if (saslEnabled) {
            config[CommonClientConfigs.SECURITY_PROTOCOL_CONFIG] = "SASL_PLAINTEXT"
            config[SaslConfigs.SASL_MECHANISM] = "PLAIN"
            config[SaslConfigs.SASL_JAAS_CONFIG] = String.format(
                "%s required username=\"%s\" password=\"%s\";", PlainLoginModule::class.java.name, username, password
            )
        }
        return DefaultKafkaConsumerFactory(config)
    }

}