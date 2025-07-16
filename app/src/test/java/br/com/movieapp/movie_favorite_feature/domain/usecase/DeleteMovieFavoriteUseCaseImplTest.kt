package br.com.movieapp.movie_favorite_feature.domain.usecase

import br.com.movieapp.TestDispatcherRule
import br.com.movieapp.core.domain.model.MovieFactory
import br.com.movieapp.core.utils.ResultData
import br.com.movieapp.movie_favorite_feature.domain.repository.MovieFavoriteRepository
import com.google.common.truth.Truth.assertThat
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
class DeleteMovieFavoriteUseCaseImplTest {

    @get:Rule
    val testDispatcher = TestDispatcherRule()

    @Mock
    lateinit var movieFavoriteRepository: MovieFavoriteRepository

    private val movie = MovieFactory().create(MovieFactory.Poster.JohnWick)

    private val deleteMovieFavoriteUseCase by lazy {
        DeleteMovieFavoriteUseCaseImpl(movieFavoriteRepository)
    }

    @Test
    fun `must return a success result when invoke is called`() = runTest {
        whenever(movieFavoriteRepository.delete(movie)).thenReturn(
            Unit
        )

        val result = deleteMovieFavoriteUseCase.invoke(DeleteMovieFavoriteUseCase.Params(movie)).first()

        assertThat(result).isEqualTo(ResultData.Success(Unit))
    }

    @Test
    fun `must return a exception result when invoke is called`() = runTest {
        val exception = RuntimeException()
        whenever(movieFavoriteRepository.delete(movie)).thenThrow(
            exception
        )

        val result = deleteMovieFavoriteUseCase.invoke(DeleteMovieFavoriteUseCase.Params(movie)).first()

        assertThat(result).isEqualTo(ResultData.Failure(exception))
    }

}