
import org.http4k.core.*
import org.http4k.format.Jackson.auto
import org.http4k.routing.bind
import org.http4k.routing.routes
import org.http4k.server.SunHttp
import org.http4k.server.asServer
import java.util.concurrent.atomic.AtomicInteger

fun SentenceAnalyzerApp(dictionaryHttp: HttpHandler): HttpHandler {
    val dictionary = Dictionary(dictionaryHttp);
    val counter = AtomicInteger()
    return routes(
        "/count" bind Method.POST to CallCounter(counter).then{ request: Request ->
            Response(Status.OK).body(dictionary.validWordsFrom(request).size.toString())
        },
        "/calls" bind Method.GET to { Response(Status.OK).body(counter.get().toString()) },
        "/analyze" bind Method.POST to { request ->
            val lens = Body.auto<Analysis>().toLens()
            val analysis = request.bodyString().groupBy { it }.mapValues { it.value.size }
            Response(Status.OK).with(lens of Analysis(analysis));
        }
    )
}

private fun Dictionary.validWordsFrom(request: Request) = request.bodyString().split(" ").filter(::isValid)

data class Analysis(val breakdown: Map<Char, Int>)

fun main() {
    SentenceAnalyzerApp(RealDictionary()).asServer(SunHttp(7777)).start();
}
