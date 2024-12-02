package kekw.ngs.bookshelfapp.data

import kekw.ngs.bookshelfapp.network.BookApiService
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory

class DefaultAppContainer(
    baseUrl: String = DEFAULT_BASE_URL
) : AppContainer {

    private val retrofit: Retrofit = Retrofit.Builder()
        .addConverterFactory(
            Json { ignoreUnknownKeys = true }
                .asConverterFactory(
                    "application/json".toMediaType()
                )
        )
        .baseUrl(baseUrl)
        .build()


    private val retrofitService: BookApiService by lazy {
        retrofit.create(BookApiService::class.java)
    }

    override val bookRepository: BookRepository by lazy {
        NetWorkBookRepository(retrofitService)
    }

    companion object {
        private const val DEFAULT_BASE_URL = "https://www.googleapis.com/books/v1/"
    }
}