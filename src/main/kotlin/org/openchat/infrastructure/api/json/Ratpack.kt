package org.openchat.infrastructure.api.json

import com.fasterxml.jackson.databind.ObjectMapper
import ratpack.handling.Context

fun Context.getObjectMapper(): ObjectMapper {
    return this.get(ObjectMapper::class.java)
}