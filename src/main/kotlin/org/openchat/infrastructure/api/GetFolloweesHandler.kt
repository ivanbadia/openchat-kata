package org.openchat.infrastructure.api

import com.fasterxml.jackson.databind.ObjectMapper
import org.openchat.domain.user.User
import org.openchat.infrastructure.api.json.toJson
import ratpack.handling.Context
import ratpack.handling.Handler
import ratpack.http.MediaType.APPLICATION_JSON
import ratpack.http.Status

class GetFolloweesHandler(val getFollowees: (String) -> List<User>) : Handler{
    override fun handle(ctx: Context) {
        val followees = getFollowees(ctx.pathTokens["followerId"]!!)
        ctx.response.status(Status.OK)
                .contentType(APPLICATION_JSON)
                .send(followees.toJson(ctx.get(ObjectMapper::class.java)))
    }

}
