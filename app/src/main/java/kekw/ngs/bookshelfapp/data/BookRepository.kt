package kekw.ngs.bookshelfapp.data

import kekw.ngs.bookshelfapp.network.Book
import kekw.ngs.bookshelfapp.network.BookDetails

interface BookRepository {
    suspend fun getBooks(query: String): List<Book>
    suspend fun getBookDetails(bookId: String): BookDetails
}