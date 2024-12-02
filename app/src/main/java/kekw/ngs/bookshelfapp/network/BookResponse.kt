package kekw.ngs.bookshelfapp.network

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BookResponse(
    val items: List<BookItem>
)

@Serializable
data class BookItem(
    val id: String,
    val volumeInfo: VolumeInfo
)

@Serializable
data class VolumeInfo(
    @SerialName("title")
    val title: String,
    val imageLinks: ImageLinks,
    val authors: List<String>,
    val description: String?
)

@Serializable
data class ImageLinks(
    @SerialName("thumbnail")
    val thumbnail: String,
)

