package org.openchat.infrastructure.api

import com.fasterxml.jackson.databind.ObjectMapper
import io.netty.handler.codec.http.HttpHeaderNames.CONTENT_TYPE
import org.openchat.domain.post.Post
import org.openchat.infrastructure.api.json.toJson
import ratpack.handling.Context
import ratpack.handling.Handler
import ratpack.http.MediaType
import ratpack.http.Status.OK

class TimelineHandler(val retrieveTimeline: (userId : String) -> List<Post>) : Handler {
    override fun handle(ctx: Context) {
        val posts = retrieveTimeline(ctx.pathTokens["userId"]!!)
        ctx.response.status(OK)
                .contentType(MediaType.APPLICATION_JSON)
                .send(posts.toJson(ctx.get(ObjectMapper::class.java)))
    }

}
