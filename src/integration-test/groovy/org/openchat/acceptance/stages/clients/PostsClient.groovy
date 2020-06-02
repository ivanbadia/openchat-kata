package org.openchat.acceptance.stages.clients

import groovy.json.JsonOutput
import org.openchat.acceptance.OpenChat
import ratpack.http.client.RequestSpec

class PostsClient {

    public static def createPost = { String userId, String text ->
        return OpenChat.app.httpClient
                .requestSpec { RequestSpec requestSpec ->
                    requestSpec.body.type("application/json")
                    requestSpec.body.text(JsonOutput.toJson(text: text))
                }
                .post("/users/$userId/timeline")
    }


    public static def retrieveTimelineFor = { String userId ->
        return OpenChat.app.httpClient
                .get("/users/$userId/timeline")
    }
}
