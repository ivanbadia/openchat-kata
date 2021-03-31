package org.openchat.acceptance.stages

import com.tngtech.jgiven.Stage
import com.tngtech.jgiven.annotation.ExpectedScenarioState
import com.tngtech.jgiven.annotation.ProvidedScenarioState
import com.tngtech.jgiven.annotation.Quoted
import ratpack.http.client.ReceivedResponse

import static org.openchat.acceptance.stages.clients.FollowingClient.createFollowing
import static org.openchat.acceptance.stages.clients.FollowingClient.retrieveFolloweesBy

class WhenFollowing  extends Stage<WhenFollowing> {
    @ExpectedScenarioState
    private List<User> registeredUsers
    @ProvidedScenarioState
    private ReceivedResponse response

    def $_checks_the_followees(@Quoted String username) {
        response = retrieveFolloweesBy userIdFor(username)
        self()
    }

    def $_follows_$(String followerUsername, String followeeUsername) {
        response = createFollowing userIdFor(followerUsername), userIdFor(followeeUsername)
        self()
    }

    private String userIdFor(String followerUsername) {
        registeredUsers.find({ user -> user.username == followerUsername }).id
    }
}
