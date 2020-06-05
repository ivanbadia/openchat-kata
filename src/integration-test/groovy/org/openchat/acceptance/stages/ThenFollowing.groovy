package org.openchat.acceptance.stages

import com.tngtech.jgiven.Stage
import com.tngtech.jgiven.annotation.ExpectedScenarioState
import groovy.json.JsonSlurper
import ratpack.http.MediaType
import ratpack.http.client.ReceivedResponse

import static ratpack.http.Status.CREATED
import static ratpack.http.Status.OK

class ThenFollowing extends Stage<ThenFollowing> {
    @ExpectedScenarioState
    private List<User> registeredUsers
    @ExpectedScenarioState
    ReceivedResponse response

    def the_following_is_created() {
        assert response.status == CREATED
        return self()
    }

    def the_users_shown_are(List<String> usernames) {
        assert response.status == OK
        assert response.body.contentType.type == MediaType.APPLICATION_JSON

        List followees = new JsonSlurper().parseText(response.body.text) as List
        assert followees.size() == usernames.size()
        this.registeredUsers.find(usernameIsIn(usernames))
                .each(assertThatIsIn(followees))
        return self()
    }

    private Closure assertThatIsIn(List followees) {
        {
            user ->
                def followee = followees.find { followee -> followee.username == user.username }
                assert followee != null
                assert followee.id == user.id
                assert followee.about == user.about
        }
    }

    private Closure usernameIsIn(List<String> usernames) {
        { user -> usernames.contains(user.username) }
    }
}

