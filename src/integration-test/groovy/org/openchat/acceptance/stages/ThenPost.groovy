package org.openchat.acceptance.stages

import com.tngtech.jgiven.Stage
import com.tngtech.jgiven.annotation.ExpectedScenarioState
import groovy.json.JsonSlurper
import ratpack.http.MediaType
import ratpack.http.client.ReceivedResponse

import static ratpack.http.Status.CREATED
import static ratpack.http.Status.OK

class ThenPost extends Stage<ThenPost> {
    private static final def UUID_PATTERN = /[0-9a-f]{8}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{12}/
    private static final def DATE_PATTERN = /\d{4}-[01]\d-[0-3]\dT[0-2]\d:[0-5]\d:[0-5]\d([+-][0-2]\d:[0-5]\d|Z)/

    @ExpectedScenarioState
    private User user
    @ExpectedScenarioState
    private ReceivedResponse response
    @ExpectedScenarioState
    private List<String> posts

    def the_post_is_created() {
        assert response.status == CREATED
        assert response.body.contentType.type == MediaType.APPLICATION_JSON
        def createdPost = parseJson(response.body.text)
        assert createdPost.postId ==~ UUID_PATTERN
        assert createdPost.userId == user.id
        assert createdPost.text == posts[0]
        assert createdPost.dateTime ==~ DATE_PATTERN
        self()
    }

    private Object parseJson(String json) {
        new JsonSlurper().parseText(json)
    }

    def the_user_sees_his_posts_in_reverse_chronological_order() {
        assert response.status == OK
        assert response.body.contentType.type == MediaType.APPLICATION_JSON
        def retrievedPosts = parseJson(response.body.text)
        assert retrievedPosts.size() == posts.size()
        def reversedPosts = posts.reverse()
        for (int i = 0; i < posts.size(); i++) {
            assert retrievedPosts[i].postId ==~ UUID_PATTERN
            assert retrievedPosts[i].userId == user.id
            assert retrievedPosts[i].text == reversedPosts[i]
            assert retrievedPosts[i].dateTime ==~ DATE_PATTERN
        }
        self()
    }

}
