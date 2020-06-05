package org.openchat.infrastructure.api

import arrow.core.Either
import org.openchat.domain.following.FollowingAlreadyExists
import ratpack.handling.Context
import ratpack.handling.Handler
import ratpack.http.Status.BAD_REQUEST
import ratpack.http.Status.CREATED
import ratpack.jackson.Jackson


class CreateFollowingHandler(val createFollowing: (String, String) -> Either<FollowingAlreadyExists, Unit>) : Handler {
    override fun handle(ctx: Context) = ctx.parse(Jackson.jsonNode())
            .map { createFollowing(it.get("followerId").asText(), it.get("followeeId").asText()) }
            .then(sendResponse(ctx))

    private fun sendResponse(ctx: Context): (Either<FollowingAlreadyExists, Unit>) -> Unit {
        return { either ->
            either.fold(sendFollowingAlreadyExists(ctx), sendFollowingCreatedSuccessfully(ctx))
        }
    }

    private fun sendFollowingCreatedSuccessfully(ctx: Context): (Unit) -> Unit {
        return {
            ctx.response.status(CREATED)
            ctx.response.send()
        }
    }

    private fun sendFollowingAlreadyExists(ctx: Context): (FollowingAlreadyExists) -> Unit {
        return {
            ctx.response.status(BAD_REQUEST)
            ctx.response.send()
        }
    }

}
