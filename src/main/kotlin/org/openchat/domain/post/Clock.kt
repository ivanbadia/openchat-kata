package org.openchat.domain.post

import java.time.LocalDateTime

open class Clock {
    open fun now(): LocalDateTime {
        return LocalDateTime.now()
    }
}