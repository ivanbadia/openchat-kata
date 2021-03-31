package org.openchat.acceptance.stages

import com.tngtech.jgiven.Stage
import com.tngtech.jgiven.annotation.ExpectedScenarioState
import groovy.json.JsonSlurper
import ratpack.http.MediaType
import ratpack.http.client.ReceivedResponse

import static org.openchat.acceptance.stages.RegularExpressions.DATE_PATTERN
import static org.openchat.acceptance.stages.RegularExpressions.UUID_PATTERN
import static ratpack.http.Status.CREATED
import static ratpack.http.Status.OK

class ThenPost extends Stage<ThenPost> {
    @ExpectedScenarioState
    private User user
    @ExpectedScenarioState
    private ReceivedResponse response
    @ExpectedScenarioState
    private List<String> posts

    def the_post_is_created() {
        assert response.status == CREATED
        assert response.body.contentType.type == MediaType.APPLICATION_JSON
        def createdPost = new JsonSlurper().parseText(response.body.text)
        assert createdPost.postId ==~ UUID_PATTERN
        assert createdPost.userId == user.id
        assert createdPost.text == posts[0]
        assert createdPost.dateTime ==~ DATE_PATTERN
        self()
    }

    def the_posts_are_shown_in_reverse_chronological_order() {
        assert response.status == OK
        assert response.body.contentType.type == MediaType.APPLICATION_JSON
        def retrievedPosts = new JsonSlurper().parseText(response.body.text)
        containsCreatedPostsInReverseOrder(retrievedPosts)
        self()
    }

    private void containsCreatedPostsInReverseOrder(retrievedPosts) {
        def reversedPosts = posts.reverse()
        assert retrievedPosts.size() == posts.size()
        for (int i = 0; i < posts.size(); i++) {
            assert retrievedPosts[i].postId ==~ UUID_PATTERN
            assert retrievedPosts[i].userId == user.id
            assert retrievedPosts[i].text == reversedPosts[i]
            assert retrievedPosts[i].dateTime ==~ DATE_PATTERN
        }
    }
}
