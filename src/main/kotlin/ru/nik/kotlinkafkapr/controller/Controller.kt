package ru.nik.kotlinkafkapr.controller

import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.nik.kotlinkafkapr.service.KafkaService
import ru.nik.kotlinkafkapr.service.KafkaServiceImpl

@RestController
@RequestMapping("/rest")
class Controller(val kafkaService: KafkaService) {

    companion object{
        private val logger = LoggerFactory.getLogger(Controller::class.java)
    }


    @PostMapping("/message")
    fun sendMessage(@RequestBody message: String) {
        logger.info("Send message: {}", message)
        kafkaService.sendMessage("Ivan", message)
    }

    fun getTopics(): List<String> {
        logger.info("Get all topics")
        return kafkaService.getTopics()
    }

}