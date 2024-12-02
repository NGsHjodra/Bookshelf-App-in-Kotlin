package kekw.ngs.bookshelfapp.data

import android.util.Log
import kekw.ngs.bookshelfapp.network.Book
import kekw.ngs.bookshelfapp.network.BookApiService
import kekw.ngs.bookshelfapp.network.BookDetails

class NetWorkBookRepository(
    private val bookApiService: BookApiService
) : BookRepository {
    override suspend fun getBooks(query: String): List<Book> {
        val response = bookApiService.getBooks(query)

        return response.items.map { item ->
            Log.d("NetWorkBookRepository", "Thumbnail URL: ${item.volumeInfo.imageLinks.thumbnail}")

            Book(
                id = item.id,
                title = item.volumeInfo.title,
                thumbnailUrl = item.volumeInfo.imageLinks.thumbnail.replace("http://", "https://")
            )
        }
    }

    override suspend fun getBookDetails(bookId: String): BookDetails {
        Log.d("NetWorkBookRepository", "Fetching book details for book ID: $bookId")
        val response = bookApiService.getBookDetails(bookId)
        return BookDetails(
            id = response.id,
            title = response.volumeInfo.title,
            authors = response.volumeInfo.authors,
            description = response.volumeInfo.description,
            thumbnailUrl = response.volumeInfo.imageLinks.thumbnail.replace("http://", "https://")
        )
    }
}