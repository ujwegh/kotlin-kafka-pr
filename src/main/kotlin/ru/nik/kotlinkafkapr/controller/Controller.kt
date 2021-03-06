package ru.nik.kotlinkafkapr.controller

import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.*
import ru.nik.kotlinkafkapr.dto.RequestDto
import ru.nik.kotlinkafkapr.service.KafkaService

@RestController
@RequestMapping("/rest")
class Controller(val kafkaService: KafkaService) {

    companion object {
        private val logger = LoggerFactory.getLogger(Controller::class.java)
    }

    @PostMapping("/message")
    fun sendMessage(@RequestBody dto: RequestDto) {
        logger.info("Send message: {}", dto)
        kafkaService.sendMessage(dto.topic, dto.userName, dto.message)
    }

    @GetMapping("/topics")
    fun getTopics(): List<String> {
        logger.info("Get all topics")
        return kafkaService.getTopics()
    }

    @PostMapping("/topic")
    fun createTopic(@RequestParam("name") name: String) {
        logger.info("Create new topic: {}", name)
        kafkaService.createTopic(name)
    }

    @DeleteMapping("/topic")
    fun deleteTopic(@RequestParam("name") name: String) {
        logger.info("Delete topic: {}", name)
        kafkaService.deleteTopic(name)
    }

}