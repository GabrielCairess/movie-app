package br.com.movieapp.search_movie_feature.domain.usecase

import androidx.paging.PagingConfig
import br.com.movieapp.TestDispatcherRule
import br.com.movieapp.core.domain.model.MovieSearchFactory
import br.com.movieapp.core.domain.model.PagingSearchMoviesFactory
import br.com.movieapp.search_movie_feature.domain.repository.MovieSearchRepository
import com.google.common.truth.Truth.assertThat
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
class GetMovieSearchUseCaseImplTest {

    @get:Rule
    val testDispatcher = TestDispatcherRule()

    @Mock
    lateinit var movieSearchRepository: MovieSearchRepository

    private val movieSearchFactory = MovieSearchFactory().create(MovieSearchFactory.Poster.Avengers)

    val pagingSourceFake = PagingSearchMoviesFactory().create(listOf(movieSearchFactory))

    private val movieSearchUseCase by lazy {
        GetMovieSearchUseCaseImpl(movieSearchRepository)
    }

    @Test
    fun `must validate flow paging data creation when invoke from use case is called`() = runTest {
        whenever(movieSearchRepository.getSearchMovies("")).thenReturn(
            pagingSourceFake
        )

        val result = movieSearchUseCase.invoke(
            GetMovieSearchUseCase.Params("", PagingConfig(20, initialLoadSize = 20))
        ).first()

        assertThat(result).isNotNull()
    }

    @Test
    fun `must validate flow paging data creation when invoke from use case is called and returns a empty flow`() = runTest {
        val exception = RuntimeException()

        whenever(movieSearchRepository.getSearchMovies("")).thenThrow(
            exception
        )

        val result = movieSearchUseCase.invoke(
            GetMovieSearchUseCase.Params("", PagingConfig(20, initialLoadSize = 20))
        ).toList()

        assertThat(result).isEmpty()
    }

}