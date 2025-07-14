package br.com.movieapp.movie_favorite_feature.presentation

import br.com.movieapp.TestDispatcherRule
import br.com.movieapp.core.domain.model.MovieFactory
import br.com.movieapp.movie_favorite_feature.domain.usecase.GetMoviesFavoriteUseCase
import com.google.common.truth.Truth.assertThat
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(MockitoJUnitRunner::class)
class MovieFavoriteViewModelTest {


    @get:Rule
    val dispatcherRule = TestDispatcherRule()

    @Mock
    lateinit var getMoviesFavoriteUseCase: GetMoviesFavoriteUseCase

    private val viewModel by lazy {
        MovieFavoriteViewModel(getMoviesFavoriteUseCase)
    }

    private val favoriteMovies = listOf(
        MovieFactory().create(poster = MovieFactory.Poster.Avengers),
        MovieFactory().create(poster = MovieFactory.Poster.JohnWick)
    )

    @Test
    fun `must validate the data object values when fetch movies`() = runTest {
        whenever(getMoviesFavoriteUseCase.invoke()).thenReturn(
            flowOf(favoriteMovies)
        )

        val result = viewModel.uiState.movies.first()

        assertThat(result).isNotEmpty()
        assertThat(result).contains(favoriteMovies[0])
    }
}