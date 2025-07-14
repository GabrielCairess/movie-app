package br.com.movieapp.search_movie_feature.presentation

import androidx.paging.PagingData
import br.com.movieapp.TestDispatcherRule
import br.com.movieapp.core.domain.model.MovieSearchFactory
import br.com.movieapp.search_movie_feature.domain.usecase.GetMovieSearchUseCase
import com.google.common.truth.Truth.assertThat
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class MovieSearchViewModelTest {

    @get:Rule
    val dispatcher = TestDispatcherRule()

    @Mock
    lateinit var getMovieSearchUseCase: GetMovieSearchUseCase

    private val viewModel by lazy {
        MovieSearchViewModel(getMovieSearchUseCase)
    }

    private val fakePagingSearchMovies = PagingData.from(
        listOf(
            MovieSearchFactory().create(poster = MovieSearchFactory.Poster.Avengers),
            MovieSearchFactory().create(poster = MovieSearchFactory.Poster.JohnWick)
        )
    )


    @Test
    fun `must validate paging data object values when calling paging data from movies`() {
        whenever(getMovieSearchUseCase.invoke(any())).thenReturn(
            flowOf(fakePagingSearchMovies)
        )

        viewModel.fetch()
        val result = viewModel.uiState.movies

        assertThat(result).isNotNull()
    }
}