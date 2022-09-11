package com.example

import java.net.URI
import java.net.http.HttpRequest
import java.net.http.HttpResponse
import java.time.Duration
import java.net.http.HttpClient;


private val httpClient: HttpClient = HttpClient.newBuilder()
    .version(HttpClient.Version.HTTP_1_1)
    .connectTimeout(Duration.ofSeconds(10))
    .build()


class GenderApiService {

    fun getGender(name: String): HttpResponse<String> {
        val request = HttpRequest.newBuilder()
            .uri(URI("https://api.genderize.io?name=${name}"))
            .GET()
            .build()

        val response: HttpResponse<String> =
            httpClient.send(request, HttpResponse.BodyHandlers.ofString())

        return response
    }
}
