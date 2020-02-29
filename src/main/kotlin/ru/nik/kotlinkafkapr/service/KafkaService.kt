package ru.nik.kotlinkafkapr.service

interface KafkaService {
    fun sendMessage(topic: String, userName: String, text: String)
    fun getTopics(): List<String>
    fun createTopic(name: String)
}