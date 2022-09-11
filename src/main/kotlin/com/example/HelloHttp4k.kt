package com.example

import com.example.formats.JacksonMessage
import com.example.formats.jacksonMessageLens
import org.http4k.core.*
import org.http4k.core.Method.GET
import org.http4k.core.Status.Companion.OK
import org.http4k.filter.DebuggingFilters.PrintRequest
import org.http4k.lens.BiDiLens
import org.http4k.lens.Query
import org.http4k.lens.nonEmptyString
import org.http4k.routing.bind
import org.http4k.routing.routes
import org.http4k.server.SunHttp
import org.http4k.server.asServer


val app: HttpHandler = routes(
    "/gender" bind GET to {
        val nameLens: BiDiLens<Request, String?> =
            Query.nonEmptyString().optional("name")
        val name: String = nameLens.extract(it).orEmpty()
        val result = GenderApiService().getGender(name)

        Response(OK).with(jacksonMessageLens of JacksonMessage(result.body()))
    }
)

fun main() {
    val printingApp: HttpHandler = PrintRequest().then(app)
    val server = printingApp.asServer(SunHttp(9000)).start()
    println("Server started on " + server.port())
}
