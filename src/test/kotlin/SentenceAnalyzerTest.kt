
import org.http4k.core.HttpHandler

class SentenceAnalyzerTest : SentenceAnalyzerContract() {
    override val app: HttpHandler = SentenceAnalyzerApp(FakeDictionary())
}
