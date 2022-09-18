
import org.http4k.core.HttpHandler

class DictionaryTest : DictionaryContract() {
    override val http: HttpHandler = FakeDictionary()
}
