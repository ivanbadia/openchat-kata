package org.openchat.infrastructure.api.json

import com.fasterxml.jackson.databind.ObjectMapper
import org.openchat.domain.user.User

fun User.toJson(objectMapper: ObjectMapper): ByteArray {
    val objectNode = objectMapper.createObjectNode()
    objectNode.put("userId", this.id.asString())
    objectNode.put("username", this.username.asString())
    objectNode.put("about", this.about)
    return objectMapper.writeValueAsBytes(objectNode)
}