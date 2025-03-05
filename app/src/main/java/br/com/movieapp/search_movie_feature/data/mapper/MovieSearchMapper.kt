package br.com.movieapp.search_movie_feature.data.mapper

import br.com.movieapp.core.data.remote.model.SearchResult
import br.com.movieapp.core.domain.Movie
import br.com.movieapp.core.domain.MovieSearch
import br.com.movieapp.core.utils.toPostUrl

fun List<SearchResult>.toMovieSearch(): List<MovieSearch> = map {
    MovieSearch(
        id = it.id,
        voteAverage = it.voteAverage,
        imageUrl = it.posterPath.toPostUrl()
    )
}
