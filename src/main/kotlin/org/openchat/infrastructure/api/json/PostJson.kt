package org.openchat.infrastructure.api.json

import com.fasterxml.jackson.databind.ObjectMapper
import org.openchat.domain.post.Post
import java.time.format.DateTimeFormatter

private val formatter: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'")

fun Post.toJson(objectMapper: ObjectMapper): ByteArray {
    val objectNode = objectMapper.createObjectNode()
    objectNode.put("postId", this.id.asString())
    objectNode.put("text", this.text)
    objectNode.put("userId", this.userId.asString())
    objectNode.put("dateTime", formatter.format(this.dateTime))
    return objectMapper.writeValueAsBytes(objectNode)
}