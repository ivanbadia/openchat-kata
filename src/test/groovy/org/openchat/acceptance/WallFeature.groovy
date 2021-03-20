package org.openchat.acceptance

import com.tngtech.jgiven.spock.ScenarioSpec
import org.openchat.acceptance.stages.GivenUsers
import org.openchat.acceptance.stages.ThenWall
import org.openchat.acceptance.stages.WhenWall

class WallFeature extends ScenarioSpec<GivenUsers, WhenWall, ThenWall> {

    def "should show user's wall containing user's posts and followees' posts"() {
        expect:
        given().users("rebeca", "daniela", "pep")
                .and()
                .$_follows_$("rebeca", "daniela")
                .and()
                .$_creates_a_new_post_with_text_$("rebeca", "Hi!. I'm Rebeca")
                .and()
                .$_creates_a_new_post_with_text_$("daniela", "Hi!. I'm Daniela")
                .and()
                .$_creates_a_new_post_with_text_$("pep", "Hi!. I'm Pep")
        when().$_checks_the_wall("rebeca")
        then().the_posts_shown_are("Hi!. I'm Daniela", "Hi!. I'm Rebeca")
    }
}
