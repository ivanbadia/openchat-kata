package org.openchat.acceptance.stages.clients

import groovy.json.JsonOutput
import org.openchat.acceptance.OpenChat
import ratpack.http.client.RequestSpec

class FollowingClient {

    public static def createFollowing = { String followerId, String followeeId ->
        return OpenChat.app.httpClient
                .requestSpec { RequestSpec requestSpec ->
                    requestSpec.body.type("application/json")
                    requestSpec.body.text(JsonOutput.toJson(["followerId": followerId, "followeeId": followeeId]))
                }
                .post("/followings")
    }

    public static def retrieveFolloweesBy = { String followerId ->
        return OpenChat.app.httpClient
                .get("/followings/$followerId/followees")
    }
}
