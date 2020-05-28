package org.openchat

import org.openchat.application.usecases.loginUser
import org.openchat.application.usecases.registerUser
import org.openchat.infrastructure.api.LoginHandler
import org.openchat.infrastructure.api.RegisterUserHandler
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
        val registerUser = registerUser(userRepository)
        val loginUser = loginUser(userRepository)
        RatpackServer.start { server ->
            server

                    .handlers { chain ->
                        chain
                                .all(RequestLogger.ncsa())
                                .get("") { it.render(BANNER) }
                                .post("registration", RegisterUserHandler(registerUser))
                                .post("login", LoginHandler(loginUser))
                    }
        }

    }
}