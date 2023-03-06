package com.aegis.myapplication

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*
import com.aegis.myapplication.dto.Results
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.*

class ApiBridge {
    private val urlPrefix: String = "https://api.themoviedb.org/3"
    private val apiKey = "a18714a5cfc5678e8626ff55504c9dff"

    private val myJson = Json {
        prettyPrint = true // signifie que la sortie JSON sera formatée de manière à être facilement lisible par un humain.
        isLenient = false // signifie que la désérialisation échouera si les données JSON en entrée ne sont pas strictement valides.
        ignoreUnknownKeys = true // signifie que la désérialisation ignorera les clés JSON inconnues plutôt que de lever une exception.
    }
    private val client = HttpClient {
        install(ContentNegotiation) {
            json(myJson)
        }
        install(Logging) {
            logger = Logger.DEFAULT
            level = LogLevel.INFO
        }
    }

    suspend fun getMovies(
        successProcess: (Results) -> Unit,
        errorProcess: (String?) -> Unit
    ) {
        val request = client.get("${urlPrefix}/discover/movie?api_key=${apiKey}&language=en-US&sort_by=popularity.desc&include_adult=false&include_video=false&page=1&with_watch_monetization_types=flatrate") {
            headers {
                contentType(ContentType.Application.Json)
            }
        }
        try {
            val body = request.body<Results>()
            successProcess(body)
        } catch (e: Exception) {
            println(request.body<String>())
            println("ERROR: $e")
            errorProcess("$e")
        }
    }
}