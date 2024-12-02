package kekw.ngs.bookshelfapp.ui.screen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import kekw.ngs.bookshelfapp.BookshelfApplication
import kekw.ngs.bookshelfapp.data.BookRepository
import kekw.ngs.bookshelfapp.network.Book
import kekw.ngs.bookshelfapp.network.BookDetails
import kotlinx.coroutines.launch
import java.io.IOException

sealed interface BookUiState {
    data class Success(val books: List<Book>): BookUiState
    data class SuccessBookDetails(val bookDetails: BookDetails) : BookUiState
    object Error: BookUiState
    object Loading: BookUiState
}

class BookViewModel(
    private val bookRepository: BookRepository
): ViewModel() {
    var bookUiState: BookUiState by mutableStateOf(BookUiState.Loading)
        private set

    init {
        getBooks()
    }

    fun getBooks() {
        viewModelScope.launch {
            bookUiState = try {
                BookUiState.Success(bookRepository.getBooks("art+history"))
            } catch (e: IOException) {
                BookUiState.Error
            }
        }
    }

    fun getBookDetails(bookId: String) {
        viewModelScope.launch {
            bookUiState = try {
                val bookDetails = bookRepository.getBookDetails(bookId)
                BookUiState.SuccessBookDetails(bookDetails)
            } catch (e: IOException) {
                BookUiState.Error
            }
        }
    }


    companion object {
        val factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as BookshelfApplication)
                val bookRepository = application.container.bookRepository
                BookViewModel(bookRepository = bookRepository)
            }
        }
    }
}