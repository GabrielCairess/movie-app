package br.com.movieapp.core.data.remote.response

import br.com.movieapp.core.data.remote.model.MovieResult
import com.google.gson.annotations.SerializedName

data class MovieResponse(
    val page: Int,
    val results:List<MovieResult>,
    @SerializedName("total_pages")
    val totalPages: Int,
    @SerializedName("total_results")
    val totalResults: Int
)
