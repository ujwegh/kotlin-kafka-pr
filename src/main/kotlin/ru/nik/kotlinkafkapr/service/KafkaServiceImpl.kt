package ru.nik.kotlinkafkapr.service

import org.apache.kafka.clients.admin.*
import org.apache.kafka.clients.consumer.Consumer
import org.slf4j.LoggerFactory
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.kafka.core.ConsumerFactory
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Service
import ru.nik.kotlinkafkapr.exceptions.TopicException
import ru.nik.kotlinkafkapr.model.Message
import java.util.*
import java.util.stream.Collectors
import kotlin.streams.toList


@Service
class KafkaServiceImpl(val kafkaTemplate: KafkaTemplate<String, Message>,
                       val consumerFactory: ConsumerFactory<String, String>) : KafkaService {

    companion object {
        private val logger = LoggerFactory.getLogger(KafkaServiceImpl::class.java)
    }

    override fun sendMessage(topic: String, userName: String, text: String) {
        val message = Message(userName, text)
        logger.info("Send message: {}", message)
        kafkaTemplate.send(topic, message)
    }

    override fun getTopics(): List<String> {
        val adminClient = createAdminClient()
        val result: ListTopicsResult = adminClient.listTopics()
        adminClient.close()
        return result.names().get().stream().toList()
    }

    override fun createTopic(name: String) {
        logger.info("In creating new topic: {}", name)
        val existedTopics = getTopics()
        if (existedTopics.contains(name)) {
            throw TopicException("Topic with name: $name already exist.")
        }
        val adminClient = createAdminClient()
        adminClient.use { client ->
            val newTopic = NewTopic(name, 3, 1)
            try {
                val result: CreateTopicsResult = client.createTopics(listOf(newTopic))
                result.all().get()
            } catch (e: Exception) {
                throw TopicException("Failed to create topic: $name")
            } finally {
                client.close()
            }
        }
    }

    override fun deleteTopic(name: String) {
        logger.info("In deleting topic: {}", name)
        val adminClient = createAdminClient()
        try {
            val result: DeleteTopicsResult = adminClient.deleteTopics(Collections.singleton(name))
            result.all().get()
        } catch (e: Exception) {
            throw TopicException("Failed to delete topic: $name")
        }finally {
            adminClient.close()
        }
    }

    @KafkaListener(topicPattern = "users")
    fun consume(message: String?) {
        logger.info("=> consumed {}", message)
    }

    fun createAdminClient() : AdminClient {
        val props: Map<String, Any> = hashMapOf(
                Pair(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, listOf("localhost:9092")))
        return AdminClient.create(props)
    }
}


