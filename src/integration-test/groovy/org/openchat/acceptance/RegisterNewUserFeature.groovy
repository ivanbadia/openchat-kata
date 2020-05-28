package org.openchat.acceptance

import com.tngtech.jgiven.spock.ScenarioSpec
import org.openchat.acceptance.stages.Given
import org.openchat.acceptance.stages.Then
import org.openchat.acceptance.stages.When

class RegisterNewUserFeature extends ScenarioSpec<Given, When, Then> {

    def "should register a new user"() {
        expect:
        given().an_unregistered_user()
        when().the_registration_is_requested()
        then().the_user_is_registered()
    }
}