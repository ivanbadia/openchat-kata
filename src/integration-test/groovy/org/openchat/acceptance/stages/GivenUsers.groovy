package org.openchat.acceptance.stages

import com.tngtech.jgiven.Stage
import com.tngtech.jgiven.annotation.ProvidedScenarioState
import groovy.json.JsonSlurper

import static org.openchat.acceptance.stages.UserBuilder.anUser
import static org.openchat.acceptance.stages.clients.FollowingClient.createFollowing
import static org.openchat.acceptance.stages.clients.RegistrationClient.register

class GivenUsers extends Stage<GivenUsers> {
    @ProvidedScenarioState
    private List<User> registeredUsers = []

    def user(String username) {
        def response = register anUser().withUsername(username).build()
        def createdUser = new JsonSlurper().parseText(response.body.text)
        registeredUsers.add anUser()
                .withId(createdUser.id)
                .withUsername(createdUser.username)
                .withAbout(createdUser.about)
                .build()
        self()
    }

    def $_follows_$(String followerUsername, String followeeUsername) {
        createFollowing userIdFor(followerUsername), userIdFor(followeeUsername)
        self()
    }

    private String userIdFor(String followerUsername) {
        registeredUsers.find({ user -> user.username == followerUsername }).id
    }
}
