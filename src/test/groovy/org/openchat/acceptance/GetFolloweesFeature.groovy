package org.openchat.acceptance

import com.tngtech.jgiven.spock.ScenarioSpec
import org.openchat.acceptance.stages.GivenUsers
import org.openchat.acceptance.stages.ThenFollowing
import org.openchat.acceptance.stages.WhenFollowing

class GetFolloweesFeature extends ScenarioSpec<GivenUsers, WhenFollowing, ThenFollowing> {

    def "should return followees by user"() {
        expect:
        given().user("biel")
                .and()
                .user("teo")
                .and()
                .user("martin")
                .and()
                .$_follows_$("biel", "teo")
                .and()
                .$_follows_$("biel", "martin")
        when().$_checks_the_followees("biel")
        then().the_users_shown_are(["teo", "martin"])
    }
}
