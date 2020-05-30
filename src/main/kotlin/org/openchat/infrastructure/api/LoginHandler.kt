package org.openchat.infrastructure.api

import arrow.core.Option
import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import org.openchat.domain.user.Credentials
import org.openchat.domain.user.User
import org.openchat.domain.user.Username
import org.openchat.infrastructure.api.json.toJson
import ratpack.handling.Context
import ratpack.handling.Handler
import ratpack.http.MediaType
import ratpack.http.Status
import ratpack.jackson.Jackson.jsonNode

class LoginHandler(private val loginUser: (String, String) -> Option<User>) : Handler {
    override fun handle(ctx: Context) = ctx.parse(jsonNode())
            .map { json -> loginUser(usernameFrom(json), passwordFrom(json)) }
            .then(sendResponse(ctx))

    private fun sendResponse(ctx: Context): (Option<User>) -> Unit {
        return { maybeUser ->
            maybeUser.fold(sendCredentialsNotFound(ctx), sendUser(ctx))
        }
    }

    private fun passwordFrom(it: JsonNode) = it.get("password").asText()

    private fun usernameFrom(it: JsonNode) = it.get("username").asText()

    private fun sendUser(ctx: Context): (User) -> Unit {
        return { user ->
            ctx.response.status(Status.OK)
                    .contentType(MediaType.APPLICATION_JSON)
            ctx.response.send(user.toJson(ctx.get(ObjectMapper::class.java)))
        }
    }

    private fun sendCredentialsNotFound(ctx: Context): () -> Unit {
        return {
            ctx.response.status(Status.BAD_REQUEST)
            ctx.response.send("Invalid credentials.")
        }
    }

}
