package org.openchat.infrastructure.api

import arrow.core.Either
import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import org.openchat.domain.post.InappropriateLanguage
import org.openchat.domain.post.Post
import org.openchat.infrastructure.api.json.toJson
import ratpack.handling.Context
import ratpack.handling.Handler
import ratpack.http.MediaType
import ratpack.http.Status
import ratpack.jackson.Jackson

class CreatePostHandler(private val registerPost: (String, String) -> Either<InappropriateLanguage, Post>) : Handler {
    override fun handle(ctx: Context) = ctx.parse(Jackson.jsonNode())
            .map(toPostText())
            .map { registerPost(ctx.pathTokens["userId"]!!, it) }
            .then(sendResponse(ctx))

    private fun toPostText(): (i: JsonNode) -> String = { it.get("text").asText() }

    private fun sendResponse(ctx: Context): (Either<InappropriateLanguage, Post>) -> Unit {
        return { either ->
            either.fold(sendPostContainsInappropriateLanguage(ctx),
                    sendCreatedPost(ctx))
        }
    }

    private fun sendPostContainsInappropriateLanguage(ctx: Context): (InappropriateLanguage) -> Unit {
        return {
            ctx.response.status(Status.BAD_REQUEST)
            ctx.response.send("Post contains inappropriate language.")
        }
    }

    private fun sendCreatedPost(ctx: Context): (Post) -> Unit {
        return { post ->
            ctx.response.status(Status.CREATED)
                    .contentType(MediaType.APPLICATION_JSON)
            ctx.response.send(post.toJson(ctx.get(ObjectMapper::class.java)))
        }
    }

}
