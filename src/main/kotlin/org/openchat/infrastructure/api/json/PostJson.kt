package org.openchat.infrastructure.api.json

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.node.ObjectNode
import org.openchat.domain.post.Post
import java.time.format.DateTimeFormatter

private val formatter: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'")

fun Post.toJson(objectMapper: ObjectMapper): ByteArray {
    val objectNode = this.toObjectNode(objectMapper)
    return objectMapper.writeValueAsBytes(objectNode)
}

fun List<Post>.toJson(objectMapper: ObjectMapper): ByteArray {
    val arrayNode = objectMapper.createArrayNode()
    this.forEach { post -> arrayNode.add(post.toObjectNode(objectMapper))}
    return objectMapper.writeValueAsBytes(arrayNode)
}

private fun Post.toObjectNode(objectMapper: ObjectMapper): ObjectNode? {
    val objectNode = objectMapper.createObjectNode()
    objectNode.put("postId", this.id.asString())
    objectNode.put("text", this.text)
    objectNode.put("userId", this.userId.asString())
    objectNode.put("dateTime", formatter.format(this.dateTime))
    return objectNode
}
