package br.com.movieapp.movie_favorite_feature.domain.usecase

import br.com.movieapp.TestDispatcherRule
import br.com.movieapp.core.domain.model.MovieFactory
import br.com.movieapp.movie_favorite_feature.domain.repository.MovieFavoriteRepository
import com.google.common.truth.Truth.assertThat
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class GetMoviesFavoriteUseCaseImplTest {

    @get:Rule
    val testDispatcher = TestDispatcherRule()

    @Mock
    lateinit var movieFavoriteRepository: MovieFavoriteRepository

    private val movies = listOf(
        MovieFactory().create(MovieFactory.Poster.JohnWick),
        MovieFactory().create(MovieFactory.Poster.Avengers)
    )

    private val getMoviesFavoriteUseCase by lazy {
        GetMoviesFavoriteUseCaseImpl(movieFavoriteRepository)
    }

    @Test
    fun `must return a success result when repository returns a list movies`() = runTest {
        whenever(movieFavoriteRepository.getMovies()).thenReturn(
            flowOf(movies)
        )

        val result = getMoviesFavoriteUseCase.invoke().first()

        assertThat(result).isEqualTo(movies)
    }

    @Test
    fun `must return a empty flow result when a exception happens`() = runTest {
        val exception = RuntimeException()
        whenever(movieFavoriteRepository.getMovies()).thenThrow(
            exception
        )

        val result = getMoviesFavoriteUseCase.invoke().toList()
        verify(movieFavoriteRepository).getMovies()

        assertThat(result).isEmpty()
    }
}