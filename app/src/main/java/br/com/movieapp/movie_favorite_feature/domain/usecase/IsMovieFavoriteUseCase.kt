package br.com.movieapp.movie_favorite_feature.domain.usecase

import br.com.movieapp.core.domain.Movie
import br.com.movieapp.core.utils.ResultData
import br.com.movieapp.movie_favorite_feature.domain.repository.MovieFavoriteRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

interface IsMovieFavoriteUseCase {
    suspend operator fun invoke(params: Params) : Flow<ResultData<Boolean>>
    data class Params(val movieId: Int)
}

class IsMovieFavoriteUseCaseImpl @Inject constructor(
    private val repository: MovieFavoriteRepository
) : IsMovieFavoriteUseCase {

    override suspend fun invoke(params: IsMovieFavoriteUseCase.Params) : Flow<ResultData<Boolean>> {
        return flow {
            val isFavorite = repository.isFavorite(params.movieId)
            emit(ResultData.Success(isFavorite))
        }.flowOn(Dispatchers.IO)
    }
}