package org.openchat.infrastructure.api

import org.openchat.domain.post.Post
import org.openchat.infrastructure.api.json.getObjectMapper
import org.openchat.infrastructure.api.json.toJson
import ratpack.handling.Context
import ratpack.handling.Handler
import ratpack.http.MediaType.APPLICATION_JSON
import ratpack.http.Status.OK

class TimelineHandler(val retrieveTimeline: (userId : String) -> List<Post>) : Handler {
    override fun handle(ctx: Context) {
        val posts = retrieveTimeline(ctx.pathTokens["userId"]!!)
        ctx.response.status(OK)
                .contentType(APPLICATION_JSON)
                .send(posts.toJson(ctx.getObjectMapper()))
    }

}
