package ru.nik.kotlinkafkapr.model

import com.fasterxml.jackson.annotation.JsonFormat
import java.time.LocalDateTime

class Message(val name: String, val body: String) {

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    val dateTime: LocalDateTime = LocalDateTime.now()
}