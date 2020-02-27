package ru.nik.kotlinkafkapr.service

interface KafkaService {
    fun sendMessage(name: String, text: String)
    fun getTopics(): List<String>
}