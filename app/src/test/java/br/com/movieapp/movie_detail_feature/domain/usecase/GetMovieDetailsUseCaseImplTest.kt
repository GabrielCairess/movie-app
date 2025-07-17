package br.com.movieapp.movie_detail_feature.domain.usecase

import androidx.paging.PagingConfig
import br.com.movieapp.TestDispatcherRule
import br.com.movieapp.core.domain.model.MovieDetailsFactory
import br.com.movieapp.core.domain.model.MovieFactory
import br.com.movieapp.core.domain.model.PagingSourceMoviesFactory
import br.com.movieapp.core.utils.ResultData
import br.com.movieapp.movie_detail_feature.domain.repository.MovieDetailsRepository
import com.google.common.truth.Truth.assertThat
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class GetMovieDetailsUseCaseImplTest {

    @get:Rule
    val testDispatcher = TestDispatcherRule()

    @Mock
    lateinit var movieDetailsRepository: MovieDetailsRepository

    private val movie = MovieFactory().create(
        MovieFactory.Poster.Avengers
    )

    private val movieDetailFactory = MovieDetailsFactory().create(
        MovieDetailsFactory.Poster.Avengers
    )

    private val pagingSourceFake = PagingSourceMoviesFactory().create(
        listOf(movie)
    )

    private val getMovieDetailsUseCase by lazy {
        GetMovieDetailsUseCaseImpl(repository = movieDetailsRepository)
    }

    @Test
    fun `must return success from result status when get both requests return success`() = runTest {
        whenever(movieDetailsRepository.getMovieSimilar(movie.id)).thenReturn(
            pagingSourceFake
        )

        whenever(movieDetailsRepository.getMovieDetails(movie.id)).thenReturn(
            movieDetailFactory
        )

        val result = getMovieDetailsUseCase.invoke(
            GetMovieDetailsUseCase.Params(
                movieId = movie.id,
                pagingConfig = PagingConfig(
                    pageSize = 20,
                    initialLoadSize = 20
                )
            )
        )

        verify(movieDetailsRepository).getMovieDetails(movie.id)
        verify(movieDetailsRepository).getMovieSimilar(movie.id)

        assertThat(result).isNotNull()
        assertThat(result is ResultData.Success).isTrue()
    }

    @Test
    fun `must return error from result status when get movie similar return error`() = runTest {
        val exception = RuntimeException()
        whenever(movieDetailsRepository.getMovieSimilar(movie.id)).thenThrow(exception)

        val result = getMovieDetailsUseCase.invoke(
            GetMovieDetailsUseCase.Params(
                movieId = movie.id,
                pagingConfig = PagingConfig(
                    pageSize = 20,
                    initialLoadSize = 20
                )
            )
        )

        verify(movieDetailsRepository).getMovieSimilar(movie.id)

        assertThat(result is ResultData.Failure).isTrue()
    }

    @Test
    fun `must return error from result status when get movie details return error`() = runTest {
        val exception = RuntimeException()
        whenever(movieDetailsRepository.getMovieDetails(movie.id)).thenThrow(exception)

        val result = getMovieDetailsUseCase.invoke(
            GetMovieDetailsUseCase.Params(
                movieId = movie.id,
                pagingConfig = PagingConfig(
                    pageSize = 20,
                    initialLoadSize = 20
                )
            )
        )

        verify(movieDetailsRepository).getMovieDetails(movie.id)

        assertThat(result is ResultData.Failure).isTrue()
    }
}