package kekw.ngs.bookshelfapp.ui.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import kekw.ngs.bookshelfapp.R
import kekw.ngs.bookshelfapp.network.Book
import kekw.ngs.bookshelfapp.ui.theme.Shapes


@Composable
fun HomeScreen(
    bookUiState: BookUiState,
    retryAction: () -> Unit,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp),
    navController: NavHostController
) {
    when (bookUiState) {
        is BookUiState.Loading -> LoadingScreen(modifier = modifier.fillMaxSize())
        is BookUiState.Success -> BooksGridScreen(
            books = bookUiState.books,
            navController = navController,
            modifier = modifier.fillMaxSize(),
            contentPadding = contentPadding
        )
        is BookUiState.Error -> ErrorScreen(retryAction, modifier = modifier.fillMaxSize())
        else -> Unit
    }
}

@Composable
fun LoadingScreen(modifier: Modifier = Modifier) {
    Text(stringResource(R.string.loading), modifier = modifier)
}

@Composable
fun ErrorScreen(retryAction: () -> Unit, modifier: Modifier = Modifier) {
    Text(stringResource(R.string.loading_failed), modifier = modifier)
}

@Composable
fun BookCard(
    book: Book,
    modifier: Modifier = Modifier,
    navController: NavHostController
) {
    Card(
        modifier = modifier
            .fillMaxSize()
            .aspectRatio(9f/16f)
            .padding(4.dp)
            .clickable {
                navController.navigate("bookDetails/${book.id}")
            },
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
//        Log.d("BookCard", "Thumbnail URL: ${book.thumbnailUrl}")
        AsyncImage(
            model = ImageRequest.Builder(context = LocalContext.current)
                .data(book.thumbnailUrl)
                .crossfade(true)
                .build(),
            contentScale = ContentScale.Crop,
            contentDescription = null,
            placeholder = painterResource(R.drawable.day1),
            error = painterResource(R.drawable.day2),
            modifier = Modifier
                .fillMaxSize()
//                .padding(4.dp)
                .clip(shape = Shapes.medium)

        )
    }
}

@Composable
fun BooksGridScreen(
    books: List<Book>,
    navController: NavHostController,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp),
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = modifier.padding(horizontal = 4.dp),
        contentPadding = contentPadding,
    ) {
        items(items = books, key = { book -> book.id }) { book ->
            BookCard(
                book = book,
                modifier = Modifier
                    .padding(4.dp)
                    .fillMaxWidth(),
                navController = navController
            )
        }
    }
}

