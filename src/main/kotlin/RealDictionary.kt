
import org.http4k.client.OkHttp
import org.http4k.core.HttpHandler
import org.http4k.core.Request
import org.http4k.core.Uri
import org.http4k.core.then
import org.http4k.filter.ClientFilters

fun RealDictionary(): HttpHandler = { request: Request ->
    ClientFilters.SetBaseUriFrom(Uri.of("https://api.dictionaryapi.dev/api/v2/entries/en/"))
        .then(OkHttp()).invoke(request)
}
