package org.openchat.acceptance

import com.tngtech.jgiven.spock.ScenarioSpec
import org.openchat.acceptance.stages.GivenUser
import org.openchat.acceptance.stages.ThenPost
import org.openchat.acceptance.stages.WhenPost

class CreatePostFeature extends ScenarioSpec<GivenUser, WhenPost, ThenPost> {

    def "create a post"() {
        expect:
        given().a_registered_user()
        when().the_user_creates_a_post_with("Hello everyone.")
        then().the_post_is_created()
    }
}
