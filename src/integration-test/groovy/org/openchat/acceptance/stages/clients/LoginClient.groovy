package org.openchat.acceptance.stages.clients

import groovy.json.JsonOutput
import org.openchat.acceptance.OpenChat
import org.openchat.acceptance.stages.User
import ratpack.http.client.RequestSpec

class LoginClient {

    public static def login = { User user ->
        return OpenChat.app.httpClient
                .requestSpec { RequestSpec requestSpec ->
                    requestSpec.body.type("application/json")
                    requestSpec.body.text(JsonOutput.toJson(username: user.username, password: user.password))
                }
                .post("/login")
    }
}
