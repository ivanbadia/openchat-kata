package org.openchat

import org.openchat.application.usecases.*
import org.openchat.domain.post.Clock
import org.openchat.domain.post.InappropriateLanguageDetector
import org.openchat.infrastructure.api.*
import org.openchat.infrastructure.persistence.InMemoryFollowingRepository
import org.openchat.infrastructure.persistence.InMemoryPostRepository
import org.openchat.infrastructure.persistence.InMemoryUserRepository
import ratpack.handling.RequestLogger
import ratpack.server.RatpackServer

val BANNER = """
     ██████╗ ██████╗ ███████╗███╗   ██╗ ██████╗██╗  ██╗ █████╗ ████████╗
██╔═══██╗██╔══██╗██╔════╝████╗  ██║██╔════╝██║  ██║██╔══██╗╚══██╔══╝
██║   ██║██████╔╝█████╗  ██╔██╗ ██║██║     ███████║███████║   ██║   
██║   ██║██╔═══╝ ██╔══╝  ██║╚██╗██║██║     ██╔══██║██╔══██║   ██║   
╚██████╔╝██║     ███████╗██║ ╚████║╚██████╗██║  ██║██║  ██║   ██║   
 ╚═════╝ ╚═╝     ╚══════╝╚═╝  ╚═══╝ ╚═════╝╚═╝  ╚═╝╚═╝  ╚═╝   ╚═╝
""".trimIndent()

object OpenChatApp {
    @JvmStatic
    fun main(args: Array<String>) {
        println(BANNER)

        val userRepository = InMemoryUserRepository()
        val postRepository = InMemoryPostRepository()
        val followingRepository = InMemoryFollowingRepository()

        val registerUser = registerUser(userRepository)
        val loginUser = loginUser(userRepository)
        val createPost = createPost(postRepository, InappropriateLanguageDetector(), Clock())
        val retrieveTimeline = retrieveTimeline(postRepository)
        val createFollowing = createFollowing(followingRepository)
        val getFollowees = getFollowees(followingRepository, userRepository)

        RatpackServer.start { server ->
            server

                    .handlers { chain ->
                        chain
                                .all(RequestLogger.ncsa())
                                .get("") { it.render(BANNER) }
                                .post("registration", RegisterUserHandler(registerUser))
                                .post("login", LoginHandler(loginUser))
                                .path("users/:userId/timeline") { ctx ->
                                    ctx.byMethod { methodSpec ->
                                        methodSpec
                                                .get(TimelineHandler(retrieveTimeline))
                                                .post(CreatePostHandler(createPost))
                                    }

                                }
                                .post("followings", CreateFollowingHandler(createFollowing))
                                .get("followings/:followerId/followees", GetFolloweesHandler(getFollowees))
                    }

        }
    }
}