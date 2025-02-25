package br.com.movieapp.core.data.remote.model

import com.google.gson.annotations.SerializedName

data class BelongsToCollection(
    @SerializedName("backdrop_path")
    val backdropPath: String,
    val name: String,
    val id: Int = 0,
    @SerializedName("poster_path")
    val posterPath: String
)