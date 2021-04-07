package org.openchat.acceptance.stages

import com.tngtech.jgiven.Stage
import com.tngtech.jgiven.annotation.ProvidedScenarioState
import com.tngtech.jgiven.annotation.Quoted
import groovy.json.JsonSlurper
import org.openchat.acceptance.formatters.ArrayFormat

import static org.openchat.acceptance.stages.UserBuilder.anUser
import static org.openchat.acceptance.stages.clients.FollowingClient.createFollowing
import static org.openchat.acceptance.stages.clients.PostsClient.createPost
import static org.openchat.acceptance.stages.clients.RegistrationClient.register

class GivenUsers extends Stage<GivenUsers> {
    @ProvidedScenarioState
    private List<User> registeredUsers = []

    def users(@ArrayFormat String[] usernames) {
        usernames.each {username -> user(username)}
        self()
    }

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

    def $_follows_$(@Quoted String followerUsername, @Quoted String followeeUsername) {
        createFollowing userIdFor(followerUsername), userIdFor(followeeUsername)
        self()
    }

    def $_creates_a_new_post_with_text_$(@Quoted String username, @Quoted String post) {
        createPost userIdFor(username), post
        self()
    }

    private String userIdFor(String username) {
        registeredUsers.find({ user -> user.username == username }).id
    }


}
