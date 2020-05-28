package org.openchat.acceptance

import com.tngtech.jgiven.spock.ScenarioSpec
import org.openchat.acceptance.stages.Given
import org.openchat.acceptance.stages.Then
import org.openchat.acceptance.stages.When

class LoginFeature extends ScenarioSpec<Given, When, Then> {

    def "should perform user login"() {
        expect:
        given().a_registered_user()
        when().the_user_performs_login()
        then().the_user_is_logged_in()
    }
}