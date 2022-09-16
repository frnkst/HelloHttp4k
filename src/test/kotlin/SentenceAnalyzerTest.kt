import com.natpryce.hamkrest.and
import com.natpryce.hamkrest.assertion.assertThat
import org.http4k.core.*
import org.http4k.hamkrest.hasBody
import org.http4k.hamkrest.hasStatus
import org.junit.jupiter.api.Test

class SentenceAnalyzerTest {
    @Test
    fun `can count words`() {
        val app: HttpHandler = SentenceAnalyzerApp()
        val request = Request(Method.POST, "/count").body("the lazy lazy cat")
        val reponse = app(request)
        assertThat(reponse, hasStatus(Status.OK) and hasBody("4"))
    }

    @Test
    fun `keep track of total calls`() {
        val app = SentenceAnalyzerApp()

        assertThat(app(Request(Method.GET, "/calls")), hasStatus(Status.OK) and hasBody("0"))
        app(Request(Method.POST, "/count")).body("the lazy lazy cat")
        assertThat(app(Request(Method.GET, "/calls")), hasStatus(Status.OK) and hasBody("1"))
    }
}

