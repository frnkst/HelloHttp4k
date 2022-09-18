import org.http4k.core.*
import org.http4k.routing.bind
import org.http4k.routing.path
import org.http4k.routing.routes

fun FakeDictionary(): HttpHandler {
    return routes("/{word}" bind Method.GET to { request: Request ->
        when (request.path("word")) {
            "the" -> Response(Status.OK)
            else -> Response(Status.NOT_FOUND)
        }
    })
}
