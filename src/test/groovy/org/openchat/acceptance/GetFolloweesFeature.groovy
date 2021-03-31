package org.openchat.acceptance

import com.tngtech.jgiven.spock.ScenarioSpec
import org.openchat.acceptance.stages.GivenUsers
import org.openchat.acceptance.stages.ThenFollowing
import org.openchat.acceptance.stages.WhenFollowing

class GetFolloweesFeature extends ScenarioSpec<GivenUsers, WhenFollowing, ThenFollowing> {

    def "return all followees for a given user"() {
        expect:

        given().users("biel", "rebeca", "sonia")
                .and()
                .$_follows_$("biel", "rebeca")
                .and()
                .$_follows_$("biel", "sonia")
        when().$_checks_the_followees("biel")
        then().the_users_returned_are("rebeca", "sonia")
    }
}
