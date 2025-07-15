package br.com.movieapp.search_movie_feature.data.mapper

import br.com.movieapp.core.data.remote.model.SearchResult
import br.com.movieapp.core.domain.model.MovieSearch
import br.com.movieapp.core.utils.toPostUrl

fun List<SearchResult>.toMovieSearch(): List<MovieSearch> = map {
    MovieSearch(
        id = it.id,
        voteAverage = it.voteAverage,
        imageUrl = it.posterPath.toPostUrl()
    )
}

fun SearchResult.toMovieSearch() = MovieSearch(
    id = id,
    voteAverage = voteAverage,
    imageUrl = posterPath.toPostUrl()
)