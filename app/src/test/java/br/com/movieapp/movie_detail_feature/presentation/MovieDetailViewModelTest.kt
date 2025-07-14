package br.com.movieapp.movie_detail_feature.presentation

import androidx.compose.ui.graphics.Color
import androidx.lifecycle.SavedStateHandle
import androidx.paging.PagingData
import br.com.movieapp.TestDispatcherRule
import br.com.movieapp.core.domain.model.MovieDetailsFactory
import br.com.movieapp.core.domain.model.MovieFactory
import br.com.movieapp.core.utils.ResultData
import br.com.movieapp.movie_detail_feature.domain.usecase.GetMovieDetailsUseCase
import br.com.movieapp.movie_favorite_feature.domain.usecase.AddMovieFavoriteUseCase
import br.com.movieapp.movie_favorite_feature.domain.usecase.DeleteMovieFavoriteUseCase
import br.com.movieapp.movie_favorite_feature.domain.usecase.IsMovieFavoriteUseCase
import com.google.common.truth.Truth.assertThat
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.argumentCaptor
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(MockitoJUnitRunner::class)
class MovieDetailViewModelTest {
    @get:Rule
    val dispatcherRule = TestDispatcherRule()

    @Mock
    lateinit var getMovieDetailsUseCase: GetMovieDetailsUseCase

    @Mock
    lateinit var addMoVieFavoriteUseCase: AddMovieFavoriteUseCase

    @Mock
    lateinit var deleteFavoriteDetailsUseCase: DeleteMovieFavoriteUseCase

    @Mock
    lateinit var isMovieFavoritesUseCase: IsMovieFavoriteUseCase

    @Mock
    lateinit var savedStateHandle: SavedStateHandle

    private val movieDetailsFactory = MovieDetailsFactory().create(
        poster = MovieDetailsFactory.Poster.Avengers
    )

    private val pagingData = PagingData.from(
        listOf(
            MovieFactory().create(poster = MovieFactory.Poster.Avengers),
            MovieFactory().create(poster = MovieFactory.Poster.JohnWick)
        )
    )

    private val movie = MovieFactory().create(
        poster = MovieFactory.Poster.Avengers
    )

    private val viewModel by lazy {
        MovieDetailViewModel(
            getMovieDetailsUseCase =getMovieDetailsUseCase,
            addFavoriteUseCase = addMoVieFavoriteUseCase,
            deleteMovieFavoriteUseCase = deleteFavoriteDetailsUseCase,
            isMovieFavoriteUseCase = isMovieFavoritesUseCase,
            savedStateHandle = savedStateHandle.apply {
                whenever(savedStateHandle.get<Int>("movieId")).thenReturn(movie.id)
            }
        )
    }

    @Test
    fun `must notify uiState with success when get movies similar and movie details return success`() = runTest {
        whenever(getMovieDetailsUseCase.invoke(any())).thenReturn(
            flowOf(
                ResultData.Success(
                    flowOf(pagingData) to movieDetailsFactory
                )
            )
        )

        whenever(isMovieFavoritesUseCase.invoke(any())).thenReturn(
            flowOf(ResultData.Success(true))
        )

        val argumentCaptor = argumentCaptor<GetMovieDetailsUseCase.Params>()
        
        viewModel.uiState.isLoading

        verify(getMovieDetailsUseCase).invoke(argumentCaptor.capture())
        assertThat(argumentCaptor.firstValue.movieId).isEqualTo(argumentCaptor.firstValue.movieId)
        val movieDetails = viewModel.uiState.movieDetails
        val results = viewModel.uiState.results
        assertThat(movieDetails).isNotNull()
        assertThat(results).isNotNull()

    }

    @Test
    fun `must notify uiState with failure when get movies similar and movie details return failure`() = runTest {
        val exception = Exception("Um erro ocorreu!")

        whenever(getMovieDetailsUseCase.invoke(any())).thenReturn(
            flowOf(
                ResultData.Failure(exception)
            )
        )

        whenever(isMovieFavoritesUseCase.invoke(any())).thenReturn(
            flowOf(ResultData.Success(true))
        )

        viewModel.uiState.isLoading

        val error = viewModel.uiState.error
        assertThat(error).isEqualTo(exception.message)
    }

    @Test
    fun `must call delete favorite and notify of uiState with filled favorite icon when current icon is checked`() = runTest {
        whenever(getMovieDetailsUseCase.invoke(any())).thenReturn(
            flowOf(
                ResultData.Success(
                    flowOf(pagingData) to movieDetailsFactory
                )
            )
        )

        whenever(deleteFavoriteDetailsUseCase.invoke(any())).thenReturn(
            flowOf(ResultData.Success(Unit))
        )

        whenever(isMovieFavoritesUseCase.invoke(any())).thenReturn(
            flowOf(ResultData.Success(true))
        )

        val deleteArgumentCaptor = argumentCaptor<DeleteMovieFavoriteUseCase.Params>()
        val checkedArgumentCaptor = argumentCaptor<IsMovieFavoriteUseCase.Params>()

        viewModel.onAddFavorite(movie = movie)

        verify(deleteFavoriteDetailsUseCase).invoke(deleteArgumentCaptor.capture())
        assertThat(movie).isEqualTo(deleteArgumentCaptor.firstValue.movie)

        verify(isMovieFavoritesUseCase).invoke(checkedArgumentCaptor.capture())
        assertThat(checkedArgumentCaptor.firstValue.movieId).isEqualTo(movie.id)

        val iconColor = viewModel.uiState.iconColor
        assertThat(iconColor).isEqualTo(Color.White)
    }

    @Test
    fun `must notify of uiState with filled favorite icon when current icon is unchecked`() = runTest {
        whenever(getMovieDetailsUseCase.invoke(any())).thenReturn(
            flowOf(
                ResultData.Success(
                    flowOf(pagingData) to movieDetailsFactory
                )
            )
        )

        whenever(addMoVieFavoriteUseCase.invoke(any())).thenReturn(
            flowOf(ResultData.Success(Unit))
        )

        whenever(isMovieFavoritesUseCase.invoke(any())).thenReturn(
            flowOf(ResultData.Success(false))
        )

        val addArgumentCaptor = argumentCaptor<AddMovieFavoriteUseCase.Params>()
        val checkedArgumentCaptor = argumentCaptor<IsMovieFavoriteUseCase.Params>()

        viewModel.onAddFavorite(movie = movie)

        verify(addMoVieFavoriteUseCase).invoke(addArgumentCaptor.capture())
        assertThat(movie).isEqualTo(addArgumentCaptor.firstValue.movie)

        verify(isMovieFavoritesUseCase).invoke(checkedArgumentCaptor.capture())
        assertThat(checkedArgumentCaptor.firstValue.movieId).isEqualTo(movie.id)

        val iconColor = viewModel.uiState.iconColor
        assertThat(iconColor).isEqualTo(Color.Red)
    }

    @Test
    fun `must notify uiState with bookmark icon filled in if bookmark check returns true`() = runTest {
        whenever(getMovieDetailsUseCase.invoke(any())).thenReturn(
            flowOf(
                ResultData.Success(
                    flowOf(pagingData) to movieDetailsFactory
                )
            )
        )

        whenever(isMovieFavoritesUseCase.invoke(any())).thenReturn(
            flowOf(ResultData.Success(true))
        )

        val checkedArgumentCaptor = argumentCaptor<IsMovieFavoriteUseCase.Params>()

        viewModel.uiState.isLoading

        verify(isMovieFavoritesUseCase).invoke(checkedArgumentCaptor.capture())
        assertThat(movie.id).isEqualTo(checkedArgumentCaptor.firstValue.movieId)

        val iconColor = viewModel.uiState.iconColor
        assertThat(iconColor).isEqualTo(Color.Red)
    }

    @Test
    fun `must notify uiState with bookmark icon filled in if bookmark check returns false`() = runTest {
        whenever(getMovieDetailsUseCase.invoke(any())).thenReturn(
            flowOf(
                ResultData.Success(
                    flowOf(pagingData) to movieDetailsFactory
                )
            )
        )

        whenever(isMovieFavoritesUseCase.invoke(any())).thenReturn(
            flowOf(ResultData.Success(false))
        )

        val checkedArgumentCaptor = argumentCaptor<IsMovieFavoriteUseCase.Params>()

        viewModel.uiState.isLoading

        verify(isMovieFavoritesUseCase).invoke(checkedArgumentCaptor.capture())
        assertThat(movie.id).isEqualTo(checkedArgumentCaptor.firstValue.movieId)

        val iconColor = viewModel.uiState.iconColor
        assertThat(iconColor).isEqualTo(Color.White)
    }

}