package kekw.ngs.bookshelfapp.network

import kotlinx.serialization.Serializable

@Serializable
data class BookDetailsResponse(
    val id: String,
    val volumeInfo: VolumeInfo
)