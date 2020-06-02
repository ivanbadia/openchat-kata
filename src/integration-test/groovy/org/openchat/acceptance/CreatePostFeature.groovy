package org.openchat.acceptance

import com.tngtech.jgiven.spock.ScenarioSpec
import org.openchat.acceptance.stages.Given
import org.openchat.acceptance.stages.ThenPost
import org.openchat.acceptance.stages.WhenPost

class CreatePostFeature extends ScenarioSpec<Given, WhenPost, ThenPost> {

    def "should create post"() {
        expect:
        given().a_registered_user()
        when().the_user_creates_a_post_with("Hello everyone.")
        then().the_post_is_created()
    }
}