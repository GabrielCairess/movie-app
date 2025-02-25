package br.com.movieapp.core.data.remote.response

import br.com.movieapp.core.data.remote.model.SearchResult
import com.google.gson.annotations.SerializedName

data class SearchResponse(
    val page: Int,
    @SerializedName("total_pages")
    val totalPages: Int,
    val results: List<SearchResult>,
    @SerializedName("total_results")
    val totalResults: Int
)