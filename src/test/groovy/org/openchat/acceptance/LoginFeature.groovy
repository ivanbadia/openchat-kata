package org.openchat.acceptance

import com.tngtech.jgiven.spock.ScenarioSpec
import org.openchat.acceptance.stages.GivenUser
import org.openchat.acceptance.stages.ThenUser
import org.openchat.acceptance.stages.WhenUser

class LoginFeature extends ScenarioSpec<GivenUser, WhenUser, ThenUser> {

    def "perform user login"() {
        expect:
        given().a_registered_user()
        when().the_user_performs_login()
        then().the_user_is_logged_in()
    }
}
