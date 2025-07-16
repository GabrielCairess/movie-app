package br.com.movieapp.movie_favorite_feature.domain.usecase

import br.com.movieapp.TestDispatcherRule
import br.com.movieapp.core.domain.model.MovieFactory
import br.com.movieapp.core.utils.ResultData
import br.com.movieapp.movie_favorite_feature.domain.repository.MovieFavoriteRepository
import com.google.common.truth.Truth.assertThat
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class IsMovieFavoriteUseCaseImplTest {

    @get:Rule
    val testDispatcher = TestDispatcherRule()

    @Mock
    lateinit var movieFavoriteRepository: MovieFavoriteRepository

    private val movie = MovieFactory().create(MovieFactory.Poster.Avengers)

    private val isMovieFavoriteUseCase by lazy {
        IsMovieFavoriteUseCaseImpl(movieFavoriteRepository)
    }

    @Test
    fun `must return a success result when repository returns true`() = runTest {
        whenever(movieFavoriteRepository.isFavorite(any())).thenReturn(
            true
        )

        val result = isMovieFavoriteUseCase.invoke(IsMovieFavoriteUseCase.Params(movie.id)).first()

        assertThat(result).isEqualTo(ResultData.Success(true))
    }

    @Test
    fun `must return a success result when repository returns false`() = runTest {
        whenever(movieFavoriteRepository.isFavorite(any())).thenReturn(
            false
        )

        val result = isMovieFavoriteUseCase.invoke(IsMovieFavoriteUseCase.Params(movie.id)).first()

        assertThat(result).isEqualTo(ResultData.Success(false))
    }

    @Test
    fun `must return a exception when repository fail`() = runTest {
        val exception = RuntimeException()
        whenever(movieFavoriteRepository.isFavorite(any())).thenThrow(
            exception
        )

        val result = isMovieFavoriteUseCase.invoke(IsMovieFavoriteUseCase.Params(movie.id)).first()

        assertThat(result).isEqualTo(ResultData.Failure(exception))
    }
}