import com.natpryce.hamkrest.and
import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import org.http4k.core.HttpHandler
import org.http4k.core.Method
import org.http4k.core.Request
import org.http4k.core.Status
import org.http4k.hamkrest.hasBody
import org.http4k.hamkrest.hasStatus
import org.junit.jupiter.api.Test

abstract class SentenceAnalyzerContract {
    abstract val app: HttpHandler

    @Test
    fun `can count words`() {
        val request = Request(Method.POST, "/count").body("the lazy lazy cat")
        val reponse = app(request)
        assertThat(reponse, hasStatus(Status.OK) and hasBody("4"))
    }

    @Test
    fun `keep track of total calls`() {
        assertThat(app(Request(Method.GET, "/calls")), hasStatus(Status.OK) and hasBody("0"))
        app(Request(Method.POST, "/count")).body("the lazy lazy cat")
        assertThat(app(Request(Method.GET, "/calls")), hasStatus(Status.OK) and hasBody("1"))
    }

    @Test
    fun `analyse a sentence`() {
        val request = Request(Method.POST, "/analyze").body("david ivan")
        val response = app(request)
        val expected = """{"breakdown":{"d":2,"a":2,"v":2,"i":2," ":1,"n":1}}"""
        assertThat(response.body.toString(), equalTo(expected))
    }
}
