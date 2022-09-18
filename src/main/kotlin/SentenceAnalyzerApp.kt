import org.http4k.core.*
import org.http4k.routing.bind
import org.http4k.routing.routes
import org.http4k.server.SunHttp
import org.http4k.server.asServer
import java.util.concurrent.atomic.AtomicInteger

fun SentenceAnalyzerApp(): HttpHandler {
    val counter = AtomicInteger()
    return routes(
        "/count" bind Method.POST to CallCounter(counter).then{ request: Request ->
            Response(Status.OK).body(request.bodyString().split(" ").size.toString())
        },
        "/calls" bind Method.GET to { Response(Status.OK).body(counter.get().toString()) },
        "/analyze" bind Method.POST to { Response(Status.OK).body("") }
    )
}


fun main() {
    SentenceAnalyzerApp().asServer(SunHttp(8000)).start();
}
