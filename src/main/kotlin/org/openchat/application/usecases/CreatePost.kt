package org.openchat.application.usecases

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import org.openchat.domain.post.*
import org.openchat.domain.user.*

fun createPost(postRepository: PostRepository,
               inappropriateLanguageDetector: InappropriateLanguageDetector,
               clock: Clock)
        : (String, String) -> Either<InappropriateLanguage, Post> =
        lambda@{ userId, text ->
            if (inappropriateLanguageDetector.containsInappropriateWords(text)) {
                return@lambda InappropriateLanguage().left()
            }

            val post = Post(postRepository.nextId(), UserId(userId), text, clock.now())
            postRepository.add(post)
            post.right()
        }
