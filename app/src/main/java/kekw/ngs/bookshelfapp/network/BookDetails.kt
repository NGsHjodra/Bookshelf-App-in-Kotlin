package kekw.ngs.bookshelfapp.network

data class BookDetails(
    val id: String,
    val title: String,
    val authors: List<String>,
    val description: String?,
    val thumbnailUrl: String
)