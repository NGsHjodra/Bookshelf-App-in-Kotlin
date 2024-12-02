package kekw.ngs.bookshelfapp.network

//@Serializable
data class Book(
    val id: String,

//    @SerialName(value = "title")
    val title: String,

//    @SerialName(value = "thumbnail")
    val thumbnailUrl: String
)
