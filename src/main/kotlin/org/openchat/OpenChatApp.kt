package org.openchat

import org.openchat.application.usecases.createPost
import org.openchat.application.usecases.loginUser
import org.openchat.application.usecases.registerUser
import org.openchat.application.usecases.retrieveTimeline
import org.openchat.domain.post.Clock
import org.openchat.domain.post.InappropriateLanguageDetector
import org.openchat.domain.post.Post
import org.openchat.infrastructure.api.LoginHandler
import org.openchat.infrastructure.api.CreatePostHandler
import org.openchat.infrastructure.api.RegisterUserHandler
import org.openchat.infrastructure.api.TimelineHandler
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
        val registerUser = registerUser(userRepository)
        val loginUser = loginUser(userRepository)
        val createPost = createPost(postRepository, InappropriateLanguageDetector(), Clock())
        val retrieveTimeline = retrieveTimeline(postRepository)

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
                    }

        }
    }
}