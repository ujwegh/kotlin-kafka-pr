package ru.nik.kotlinkafkapr.service

import org.apache.kafka.clients.admin.AdminClient
import org.apache.kafka.clients.admin.AdminClientConfig
import org.apache.kafka.clients.admin.CreateTopicsResult
import org.apache.kafka.clients.admin.NewTopic
import org.apache.kafka.clients.consumer.Consumer
import org.slf4j.LoggerFactory
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.kafka.core.ConsumerFactory
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Service
import ru.nik.kotlinkafkapr.exceptions.TopicException
import ru.nik.kotlinkafkapr.model.Message


@Service
class KafkaServiceImpl(val kafkaTemplate: KafkaTemplate<String, Message>,
                       val consumerFactory: ConsumerFactory<String, String>,
                       val adminClient: AdminClient) : KafkaService {

    companion object {
        private val logger = LoggerFactory.getLogger(KafkaServiceImpl::class.java)
    }

    override fun sendMessage(topic: String, userName: String, text: String) {
        val message = Message(userName, text)
        logger.info("Send message: {}", message)
        kafkaTemplate.send(topic, message)
    }

    override fun getTopics(): List<String> {
        val consumer: Consumer<String, String> = consumerFactory.createConsumer()
        val topics = consumer.listTopics()
        return topics.keys.toList()
    }

    override fun createTopic(name: String) {
        logger.info("In creating new topic: {}", name)
        val existedTopics = getTopics()
        println(existedTopics)
        if (existedTopics.contains(name)){
            throw TopicException("Topic with name: $name already exist.")
        }

        adminClient.use { adminClient ->
            val newTopic = NewTopic(name, 3, 1)
            try {
                val result: CreateTopicsResult = adminClient.createTopics(listOf(newTopic))
                result.all().get()
            } catch (e: Exception) {
                throw TopicException("Failed to create topic: $name")
            }
        }
    }

    @KafkaListener(topics = ["test", "kotlin"], groupId = "users")
    fun consume(message: String?) {
        logger.info("=> consumed {}", message);
    }
}


