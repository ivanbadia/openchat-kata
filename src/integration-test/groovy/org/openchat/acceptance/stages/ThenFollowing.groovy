package org.openchat.acceptance.stages

import com.tngtech.jgiven.Stage
import com.tngtech.jgiven.annotation.ExpectedScenarioState
import ratpack.http.client.ReceivedResponse

import static ratpack.http.Status.CREATED

class ThenFollowing extends Stage<ThenFollowing> {
    @ExpectedScenarioState
    private ReceivedResponse response

    def the_following_is_created() {
        assert response.status == CREATED
        return self()
    }
}
