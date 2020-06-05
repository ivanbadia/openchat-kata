package org.openchat.acceptance


import com.tngtech.jgiven.spock.ScenarioSpec
import org.openchat.acceptance.stages.GivenUser
import org.openchat.acceptance.stages.ThenPost
import org.openchat.acceptance.stages.WhenPost

class ShowTimelineFeature extends ScenarioSpec<GivenUser, WhenPost, ThenPost> {

    def "should show user's timeline"() {
        expect:
        given().a_registered_user()
                .with().post("First post")
                .and().post("Second post")
                .and().post("Third post")
        when().the_user_checks_his_timeline()
        then().the_user_sees_his_posts_in_reverse_chronological_order()
    }
}
