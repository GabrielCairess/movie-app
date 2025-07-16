package br.com.movieapp.core.paging

import androidx.paging.PagingSource
import br.com.movieapp.TestDispatcherRule
import br.com.movieapp.core.domain.model.Movie
import br.com.movieapp.core.domain.model.MovieFactory
import br.com.movieapp.core.domain.model.MoviePagingFactory
import br.com.movieapp.movie_detail_feature.domain.source.MovieDetailsRemoteDataSource
import com.google.common.truth.Truth.assertThat
import com.nhaarman.mockitokotlin2.any
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
class MovieSimilarPagingSourceTest {
    @get:Rule
    val testDispatcher = TestDispatcherRule()

    @Mock
    lateinit var remoteDataSource: MovieDetailsRemoteDataSource

    private val movieFactory = MovieFactory()

    private val moviePagingFactory = MoviePagingFactory().create()

    private val movieSimilarPagingSource by lazy {
        MovieSimilarPagingSource(
            movieId = 1,
            remoteDataSource = remoteDataSource
        )
    }

    @Test
    fun `must return a success load result when load is called`() = runTest {
        whenever(remoteDataSource.getMoviesSimilar(any(), any())).thenReturn(
            moviePagingFactory
        )

        val result = movieSimilarPagingSource.load(
            PagingSource.LoadParams.Refresh(
                key = null,
                loadSize = 2,
                placeholdersEnabled = false
            )
        )

        assertThat(PagingSource.LoadResult.Page(
            data = listOf(
                movieFactory.create(MovieFactory.Poster.Avengers),
                movieFactory.create(MovieFactory.Poster.JohnWick)
            ),
            prevKey = null,
            nextKey = null
        )).isEqualTo(result)
    }

    @Test
    fun `must return a excpetion load result when load is called`() = runTest {
        val exception = RuntimeException()
        whenever(remoteDataSource.getMoviesSimilar(any(), any())).thenThrow(
            exception
        )

        val result = movieSimilarPagingSource.load(
            PagingSource.LoadParams.Refresh(
                key = null,
                loadSize = 2,
                placeholdersEnabled = false
            )
        )

        assertThat(PagingSource.LoadResult.Error<Int, Movie>(exception)).isEqualTo(result)
    }
}