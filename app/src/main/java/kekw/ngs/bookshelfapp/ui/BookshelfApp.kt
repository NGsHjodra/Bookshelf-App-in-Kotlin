package kekw.ngs.bookshelfapp.ui

import android.util.Log
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import kekw.ngs.bookshelfapp.R
import kekw.ngs.bookshelfapp.ui.screen.BookDetailsScreen
import kekw.ngs.bookshelfapp.ui.screen.BookUiState
import kekw.ngs.bookshelfapp.ui.screen.BookViewModel
import kekw.ngs.bookshelfapp.ui.screen.ErrorScreen
import kekw.ngs.bookshelfapp.ui.screen.HomeScreen
import kekw.ngs.bookshelfapp.ui.screen.LoadingScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookshelfApp(
    navController: NavHostController = rememberNavController()
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = { BookTopAppBar(scrollBehavior = scrollBehavior) }
    ) { padding ->
        NavHost(
            navController = navController,
            startDestination = "home",
            modifier = Modifier.padding(padding)
        ) {
            // Home Screen
            composable("home") {
                val bookViewModel: BookViewModel = viewModel(factory = BookViewModel.factory)
                HomeScreen(
                    bookUiState = bookViewModel.bookUiState,
                    retryAction = bookViewModel::getBooks,
                    contentPadding = padding,
                    navController = navController
                )
            }

            // Book Details Screen
            composable(
                route = "bookDetails/{bookId}",
                arguments = listOf(navArgument("bookId") { type = NavType.StringType })
            ) { backStackEntry ->
                val bookId = backStackEntry.arguments?.getString("bookId") ?: return@composable
                val bookViewModel: BookViewModel = viewModel(factory = BookViewModel.factory)

                // Fetch book details
                LaunchedEffect(bookId) {
                    bookViewModel.getBookDetails(bookId)
                }

                when (val state = bookViewModel.bookUiState) {
                    is BookUiState.Loading -> LoadingScreen()
                    is BookUiState.SuccessBookDetails -> {
                        Log.d("BookDetailsScreen", "Book Details: ${state.bookDetails}")
                        BookDetailsScreen(bookDetails = state.bookDetails)
                    }
                    is BookUiState.Error -> ErrorScreen(retryAction = { bookViewModel.getBookDetails(bookId) })
                    else -> Unit
                }
            }
        }
    }
}



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookTopAppBar(scrollBehavior: TopAppBarScrollBehavior, modifier: Modifier = Modifier) {
    CenterAlignedTopAppBar(
        scrollBehavior = scrollBehavior,
        title = {
            Text(
                text = stringResource(R.string.app_name),
                style = MaterialTheme.typography.headlineSmall,
            )
        },
        modifier = modifier
    )
}