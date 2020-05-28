package org.openchat.infrastructure.api

import arrow.core.Either
import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import org.openchat.application.usecases.RegisterUserCmd
import org.openchat.domain.user.User
import org.openchat.domain.user.UsernameAlreadyInUse
import ratpack.handling.Context
import ratpack.handling.Handler
import ratpack.http.MediaType.APPLICATION_JSON
import ratpack.http.Status.BAD_REQUEST
import ratpack.http.Status.CREATED
import ratpack.jackson.Jackson.jsonNode

class RegisterUserHandler(private val registerUser: (RegisterUserCmd) -> Either<UsernameAlreadyInUse, User>) : Handler {
    override fun handle(ctx: Context) {
        ctx.parse(jsonNode())
                .map { it.toRegisterUserCmd() }
                .map { registerUser(it) }
                .then {
                    either -> either.fold(sendUsernameAlreadyInUse(ctx), sendCreatedUser(ctx))
                }
    }

    private fun JsonNode.toRegisterUserCmd(): RegisterUserCmd {
        return RegisterUserCmd(
                this.get("username").asText(),
                this.get("password").asText(),
                this.get("about").asText()
        )
    }

    private fun sendUsernameAlreadyInUse(ctx: Context): (UsernameAlreadyInUse) -> Unit {
        return {
            ctx.response.status(BAD_REQUEST)
            ctx.response.send("Username already in use.")
        }
    }

    private fun sendCreatedUser(ctx: Context): (User) -> Unit {
        return { user ->
            ctx.response.status(CREATED)
                    .contentType(APPLICATION_JSON)
            ctx.response.send(user.toJson(ctx.get(ObjectMapper::class.java)))
        }
    }

    private fun User.toJson(objectMapper: ObjectMapper): ByteArray {
        val objectNode = objectMapper.createObjectNode()
        objectNode.put("userId", this.id.asString())
        objectNode.put("username", this.username.asString())
        objectNode.put("about", this.about)
        return objectMapper.writeValueAsBytes(objectNode)
    }
}