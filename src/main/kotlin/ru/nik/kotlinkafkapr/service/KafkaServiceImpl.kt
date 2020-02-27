package ru.nik.kotlinkafkapr.service

import org.apache.kafka.clients.consumer.Consumer
import org.slf4j.LoggerFactory
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.kafka.core.ConsumerFactory
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Service
import ru.nik.kotlinkafkapr.model.Message


@Service
class KafkaServiceImpl(val kafkaTemplate: KafkaTemplate<String, Message>,
                       val consumerFactory: ConsumerFactory<String, String>) : KafkaService {

    companion object {
        private val logger = LoggerFactory.getLogger(KafkaServiceImpl::class.java)
    }

    override fun sendMessage(name: String, text: String) {
        val message = Message(name, text)
        logger.info("Send message: {}", message)
        kafkaTemplate.send("test", message)
    }

    override fun getTopics(): List<String> {
        val consumer: Consumer<String, String> = consumerFactory.createConsumer()
        val topics = consumer.listTopics()
        return topics.keys.toList()
    }

    @KafkaListener(topics = ["test"], groupId = "users")
    fun consume(message: String?) {
        logger.info("=> consumed {}", message);
    }
}


