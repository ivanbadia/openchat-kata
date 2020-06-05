package org.openchat.infrastructure.api.json

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.node.ObjectNode
import org.openchat.domain.user.User

fun User.toJson(objectMapper: ObjectMapper): ByteArray {
    val objectNode = toObjectNode(objectMapper)
    return objectMapper.writeValueAsBytes(objectNode)
}

fun List<User>.toJson(objectMapper: ObjectMapper): ByteArray {
    val arrayNode = objectMapper.createArrayNode()
    this.forEach { user -> arrayNode.add(user.toObjectNode(objectMapper))}
    return objectMapper.writeValueAsBytes(arrayNode)
}

private fun User.toObjectNode(objectMapper: ObjectMapper): ObjectNode {
    val objectNode = objectMapper.createObjectNode()
    objectNode.put("id", this.id.asString())
    objectNode.put("username", this.username.asString())
    objectNode.put("about", this.about)
    return objectNode
}