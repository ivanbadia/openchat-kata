package org.openchat.infrastructure.api

import org.openchat.domain.post.Post
import org.openchat.infrastructure.api.json.getObjectMapper
import org.openchat.infrastructure.api.json.toJson
import ratpack.handling.Context
import ratpack.handling.Handler
import ratpack.http.MediaType
import ratpack.http.Status

class WallHandler(private val retrieveWall: (String) -> List<Post>) : Handler{
    override fun handle(ctx: Context) {
        val posts = retrieveWall(ctx.pathTokens["userId"]!!)
        ctx.response.status(Status.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .send(posts.toJson(ctx.getObjectMapper()))
    }

}
