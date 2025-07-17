package br.com.movieapp.movie_popular_feature.domain.usecase

import androidx.paging.PagingConfig
import androidx.paging.PagingSource
import br.com.movieapp.TestDispatcherRule
import br.com.movieapp.core.domain.model.MovieFactory
import br.com.movieapp.core.domain.model.PagingSourceMoviesFactory
import br.com.movieapp.movie_popular_feature.domain.repository.MoviePopularRepository
import com.google.common.truth.Truth.assertThat
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class GetPopularMoviesUseCaseImplTest {

    @get:Rule
    val testDispatcher = TestDispatcherRule()

    @Mock
    lateinit var moviePopularRepository: MoviePopularRepository

    private val movie = MovieFactory().create(MovieFactory.Poster.Avengers)

    private val pagingSourceFake = PagingSourceMoviesFactory().create(listOf(movie))

    private val getPopularMoviesUseCase by lazy {
        GetPopularMoviesUseCaseImpl(moviePopularRepository)
    }

    @Test
    fun `must validate flow paging data creation when invoke from use case is called`() = runTest {
        whenever(moviePopularRepository.getPopularMovies()).thenReturn(
            pagingSourceFake
        )

        val result = getPopularMoviesUseCase.invoke(GetPopularMoviesUseCase.Params(PagingConfig(20, initialLoadSize = 20))).first()
        verify(moviePopularRepository).getPopularMovies()

        assertThat(result).isNotNull()
    }

    @Test
    fun `must emit an empty stream when an exception is thrown from repository`() = runTest {
        val exception = RuntimeException()
        whenever(moviePopularRepository.getPopularMovies()).thenThrow(
            exception
        )

        val result = getPopularMoviesUseCase.invoke(GetPopularMoviesUseCase.Params(PagingConfig(20, initialLoadSize = 20))).toList()
        verify(moviePopularRepository).getPopularMovies()

        assertThat(result).isEmpty()
    }

}