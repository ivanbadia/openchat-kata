package org.openchat.acceptance

import com.tngtech.jgiven.spock.ScenarioSpec
import org.openchat.acceptance.stages.GivenUsers
import org.openchat.acceptance.stages.ThenFollowing
import org.openchat.acceptance.stages.WhenFollowing

class CreateFollowingFeature extends ScenarioSpec<GivenUsers, WhenFollowing, ThenFollowing> {

    def "should create following"() {
        expect:
        given().registered_user("pablo")
                .and()
                .registered_user("juan")
        when().$_follows_$("pablo", "juan")
        then().the_following_is_created()
    }
}
