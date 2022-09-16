import org.http4k.core.*
import org.http4k.routing.bind
import org.http4k.routing.routes
import org.http4k.server.SunHttp
import org.http4k.server.asServer

fun SentenceAnalyzerApp(): HttpHandler =
    routes(
        "/count" bind Method.POST to { request: Request ->
            Response(Status.OK).body(request.bodyString().split(" ").size.toString())
        },
        "/calls" bind Method.GET to { request: Request ->
            Response(Status.OK).body("not implemented yet")
        }
    )


fun main() {
    SentenceAnalyzerApp().asServer(SunHttp(8000)).start();
}
