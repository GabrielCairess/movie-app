package br.com.movieapp.movie_detail_feature.presentation

import android.annotation.SuppressLint
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.paging.compose.collectAsLazyPagingItems
import br.com.movieapp.R
import br.com.movieapp.core.domain.MovieDetails
import br.com.movieapp.movie_detail_feature.presentation.components.MovieDetailContent
import br.com.movieapp.movie_detail_feature.presentation.state.MovieDetailState
import br.com.movieapp.ui.theme.black
import br.com.movieapp.ui.theme.white

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun MovieDetailsScreen(
    id: Int?,
    uiState: MovieDetailState,
    getMovieDetails: (MovieDetailEvent.GetMovieDetails) -> Unit,
    modifier: Modifier = Modifier
) {

    val pagingMoviesSimilar = uiState.results.collectAsLazyPagingItems()

    LaunchedEffect(key1 = true) {
        if (id != null) {
            getMovieDetails(MovieDetailEvent.GetMovieDetails(id))
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = stringResource(R.string.detail_movie), color = white)
                },
                backgroundColor = black
            )
        },
        content = {
            MovieDetailContent(
                movieDetails = uiState.movieDetails,
                pagingMoviesSimilar = pagingMoviesSimilar,
                isLoading = uiState.isLoading,
                isError = uiState.error ?: "",
                iconColor = uiState.iconColor,
                onAddFavorites = {

                }
            )
        }
    )
}