package br.com.movieapp.movie_favorite_feature.domain.usecase

import br.com.movieapp.core.domain.Movie
import br.com.movieapp.core.utils.ResultData
import br.com.movieapp.movie_favorite_feature.domain.repository.MovieFavoriteRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

interface DeleteMovieFavoriteUseCase {
    suspend operator fun invoke(params: Params) : Flow<ResultData<Unit>>
    data class Params(val movie: Movie)
}

class DeleteMovieFavoriteUseCaseImpl @Inject constructor(
    private val repository: MovieFavoriteRepository
) : DeleteMovieFavoriteUseCase {

    override suspend fun invoke(params: DeleteMovieFavoriteUseCase.Params) : Flow<ResultData<Unit>> {
        return flow {
            val delete = repository.delete(params.movie)
            emit(ResultData.Success(delete))
        }.flowOn(Dispatchers.IO)
    }
}