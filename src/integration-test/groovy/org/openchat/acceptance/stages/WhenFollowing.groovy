package org.openchat.acceptance.stages

import com.tngtech.jgiven.Stage
import com.tngtech.jgiven.annotation.ExpectedScenarioState
import com.tngtech.jgiven.annotation.ProvidedScenarioState
import ratpack.http.client.ReceivedResponse

import static org.openchat.acceptance.stages.clients.FollowingClient.createFollowing

class WhenFollowing  extends Stage<WhenFollowing> {
    @ExpectedScenarioState
    private List<User> users
    @ProvidedScenarioState
    private ReceivedResponse response

    private usernameEqualTo = { String followerUsername ->
        return { user -> user.username == followerUsername }
    }

    def $_follows_$(String followerUsername, String followeeUsername) {
        String followerId = users.find(usernameEqualTo(followerUsername)).id
        String followeeId = users.find(usernameEqualTo(followeeUsername)).id
        response = createFollowing followerId, followeeId
        self()
    }
}
