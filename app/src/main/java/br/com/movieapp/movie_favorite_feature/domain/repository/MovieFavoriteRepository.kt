package br.com.movieapp.movie_favorite_feature.domain.repository

import br.com.movieapp.core.domain.model.Movie
import kotlinx.coroutines.flow.Flow

interface MovieFavoriteRepository {
    fun getMovies() : Flow<List<Movie>>
    suspend fun insert(movie: Movie)
    suspend fun isFavorite(movieId: Int) : Boolean
    suspend fun delete(movie: Movie)

}