package me.ogq.ocp.sample.config

import me.ogq.ocp.sample.model.ImageRegistered
import org.apache.kafka.clients.producer.ProducerConfig
import org.apache.kafka.common.serialization.StringSerializer
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.core.DefaultKafkaProducerFactory
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.kafka.support.serializer.JsonSerializer

@Configuration
class KafkaProducerConfig(
    @Value("\${spring.kafka.bootstrap-servers}")
    private val bootstrapServers: String
) {
    @Bean
    fun kafkaTemplate(): KafkaTemplate<String, ImageRegistered> {
        val config = mapOf(
            ProducerConfig.BOOTSTRAP_SERVERS_CONFIG to bootstrapServers,
            ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG to StringSerializer::class.java,
            ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG to JsonSerializer::class.java
        )

        return KafkaTemplate(DefaultKafkaProducerFactory(config))
    }
}
