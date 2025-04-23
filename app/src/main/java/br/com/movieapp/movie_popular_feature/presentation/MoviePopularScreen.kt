package br.com.movieapp.movie_popular_feature.presentation

import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.paging.compose.collectAsLazyPagingItems
import br.com.movieapp.R
import br.com.movieapp.core.presentation.components.common.MovieAppBar
import br.com.movieapp.core.utils.UtilFunctions
import br.com.movieapp.movie_popular_feature.presentation.components.MovieContent
import br.com.movieapp.movie_popular_feature.presentation.state.MoviePopularState

@Composable
fun MoviePopularScreen(
    uiState: MoviePopularState,
    navigateToDetailMovie: (Int) -> Unit,
    modifier: Modifier = Modifier
) {

    val movies = uiState.movies.collectAsLazyPagingItems()

    Scaffold(
        topBar = {
            MovieAppBar(R.string.popular_movies)
        },
        content = { paddingValues ->
            MovieContent(
                pagingMovies = movies,
                paddingValues = paddingValues,
                onClick = { movieId ->
                    UtilFunctions.logInfo("MOVIE_ID", movieId.toString())
                    navigateToDetailMovie(movieId)
                }
            )
        }
    )
}