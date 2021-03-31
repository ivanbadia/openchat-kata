package org.openchat.acceptance

import com.tngtech.jgiven.spock.ScenarioSpec
import org.openchat.acceptance.stages.GivenUsers
import org.openchat.acceptance.stages.ThenFollowing
import org.openchat.acceptance.stages.WhenFollowing

class GetFolloweesFeature extends ScenarioSpec<GivenUsers, WhenFollowing, ThenFollowing> {

    def "return all followees for a given user"() {
        expect:

        given().users("biel", "sonia", "martin")
                .and()
                .$_follows_$("biel", "sonia")
                .and()
                .$_follows_$("biel", "martin")
        when().$_checks_the_followees("biel")
        then().the_users_displayed_are("sonia", "martin")
    }
}
