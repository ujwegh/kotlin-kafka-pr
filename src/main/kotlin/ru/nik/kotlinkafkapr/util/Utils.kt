package ru.nik.kotlinkafkapr.util

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.util.StdDateFormat
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import org.apache.kafka.common.serialization.Deserializer
import org.apache.kafka.common.serialization.Serializer
import ru.nik.kotlinkafkapr.model.Message


val jsonMapper = ObjectMapper().apply {
    registerKotlinModule()
//    registerModule(JavaTimeModule())
    dateFormat = StdDateFormat()
}

class MessageSerializer : Serializer<Message> {
    override fun serialize(topic: String?, data: Message?): ByteArray? {
        if (data == null) return null
        return jsonMapper.writeValueAsBytes(data)
    }

    override fun close() {}
    override fun configure(configs: MutableMap<String, *>?, isKey: Boolean) {}
}

class MessageDeserializer : Deserializer<Message> {
    override fun deserialize(topic: String, data: ByteArray?): Message? {
        if (data == null) return null
        return jsonMapper.readValue(data, Message::class.java)
    }

    override fun close() {}
    override fun configure(configs: MutableMap<String, *>?, isKey: Boolean) {}
}