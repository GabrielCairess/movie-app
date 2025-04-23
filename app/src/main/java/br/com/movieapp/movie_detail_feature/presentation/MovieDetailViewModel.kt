package br.com.movieapp.movie_detail_feature.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.movieapp.core.domain.Movie
import br.com.movieapp.core.utils.Constants
import br.com.movieapp.core.utils.ResultData
import br.com.movieapp.core.utils.UtilFunctions
import br.com.movieapp.movie_detail_feature.domain.usecase.GetMovieDetailsUseCase
import br.com.movieapp.movie_detail_feature.domain.usecase.GetMovieDetailsUseCase.*
import br.com.movieapp.movie_detail_feature.presentation.state.MovieDetailState
import br.com.movieapp.movie_favorite_feature.domain.usecase.AddMovieFavoriteUseCase
import br.com.movieapp.movie_favorite_feature.domain.usecase.DeleteMovieFavoriteUseCase
import br.com.movieapp.movie_favorite_feature.domain.usecase.IsMovieFavoriteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailViewModel @Inject constructor(
    private val getMovieDetailsUseCase: GetMovieDetailsUseCase,
    private val addFavoriteUseCase: AddMovieFavoriteUseCase,
    private val deleteMovieFavoriteUseCase: DeleteMovieFavoriteUseCase,
    private val isMovieFavoriteUseCase: IsMovieFavoriteUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    var uiState by mutableStateOf(MovieDetailState())
        private set


    private val movieId = savedStateHandle.get<Int>(key = Constants.MOVIE_DETAIL_ARGUMENT_KEY)

    init {
        movieId?.let {
            checkedFavorite(MovieDetailEvent.CheckedFavorite(it))
            getMovieDetails(MovieDetailEvent.GetMovieDetails(it))
        }
    }

    fun getMovieDetails(getMovieDetails: MovieDetailEvent.GetMovieDetails) {
        event(getMovieDetails)
    }

    fun checkedFavorite(checkedFavorite: MovieDetailEvent.CheckedFavorite) {
        event(checkedFavorite)
    }

    fun onAddFavorite(movie: Movie) {
        if (uiState.iconColor == Color.White) {
            event(MovieDetailEvent.AddFavorite(movie))
        } else {
            event(MovieDetailEvent.RemoveFavorite(movie))
        }
    }

    private fun event(event: MovieDetailEvent) {
        when (event) {
            is MovieDetailEvent.GetMovieDetails  -> {
                viewModelScope.launch {
                    getMovieDetailsUseCase.invoke(
                        params = Params(event.movieId)
                    ).collect { resultData ->
                        when (resultData) {
                            is ResultData.Success -> {
                                uiState = uiState.copy(
                                    isLoading = false,
                                    movieDetails = resultData.data.second,
                                    results = resultData.data.first
                                )
                            }

                            is ResultData.Failure -> {
                                uiState = uiState.copy(
                                    isLoading = false,
                                    error = resultData.exception.message.toString()
                                )
                                UtilFunctions.logError("MovieDetailViewModelError", "event: ${resultData.exception.message}")
                            }

                            is ResultData.Loading -> {
                                uiState = uiState.copy(isLoading = true)
                            }
                        }
                    }
                }
            }

            is MovieDetailEvent.AddFavorite -> {
                viewModelScope.launch {
                    addFavoriteUseCase.invoke(params = AddMovieFavoriteUseCase.Params(
                        movie = event.movie
                    )).collectLatest { result ->
                        when (result) {
                            is ResultData.Success -> {
                                uiState = uiState.copy(
                                    iconColor = Color.Red
                                )
                            }
                            is ResultData.Failure -> {
                                UtilFunctions.logError("MovieDetailViewModelError", "event: ${result.exception.message}")
                            }
                            is ResultData.Loading -> {
                            }
                        }

                    }
                }
            }
            is MovieDetailEvent.CheckedFavorite -> {
                viewModelScope.launch {
                    isMovieFavoriteUseCase.invoke(params = IsMovieFavoriteUseCase.Params(
                        movieId = event.movieId
                    )).collectLatest { result ->
                        when (result) {
                            is ResultData.Success -> {
                                uiState = uiState.copy(
                                    iconColor = if (result.data == true) Color.Red else Color.White
                                )
                            }

                            is ResultData.Failure -> {
                                UtilFunctions.logError(
                                    "MovieDetailViewModelError",
                                    "event: ${result.exception.message}"
                                )
                            }

                            is ResultData.Loading -> {
                            }
                        }
                    }
                }
            }

            is MovieDetailEvent.RemoveFavorite -> {
                viewModelScope.launch {
                    deleteMovieFavoriteUseCase.invoke(params = DeleteMovieFavoriteUseCase.Params(
                        movie = event.movie
                    )).collectLatest { result ->
                        when (result) {
                            is ResultData.Success -> {
                                uiState = uiState.copy(
                                    iconColor = Color.White
                                )
                            }
                            is ResultData.Failure -> {
                                UtilFunctions.logError(
                                    "MovieDetailViewModelError",
                                    "event: ${result.exception.message}"
                                )
                            }
                            is ResultData.Loading -> {
                            }
                        }
                    }
                }
            }
        }
    }
}