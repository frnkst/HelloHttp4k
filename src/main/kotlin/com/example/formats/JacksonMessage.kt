package com.example.formats

import org.http4k.core.Body
import org.http4k.format.Jackson.auto

data class JacksonMessage(val message: String)

val jacksonMessageLens = Body.auto<JacksonMessage>().toLens()
