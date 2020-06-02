package org.openchat.acceptance

import com.tngtech.jgiven.spock.ScenarioSpec
import org.openchat.acceptance.stages.Given
import org.openchat.acceptance.stages.ThenUser
import org.openchat.acceptance.stages.WhenUser

class RegisterNewUserFeature extends ScenarioSpec<Given, WhenUser, ThenUser> {

    def "should register a new user"() {
        expect:
        given().an_unregistered_user()
        when().the_registration_is_requested()
        then().the_user_is_registered()
    }
}