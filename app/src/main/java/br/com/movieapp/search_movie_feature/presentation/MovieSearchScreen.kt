package br.com.movieapp.search_movie_feature.presentation

import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.paging.compose.collectAsLazyPagingItems
import br.com.movieapp.R
import br.com.movieapp.core.presentation.components.common.MovieAppBar
import br.com.movieapp.search_movie_feature.presentation.components.SearchContent
import br.com.movieapp.search_movie_feature.presentation.state.MovieSearchState

@Composable
fun MovieSearchScreen(
    uiState: MovieSearchState,
    onEvent: (MovieSearchEvent) -> Unit,
    onFetch: (String) -> Unit,
    navigateToDetailMovie: (Int) -> Unit,
    modifier: Modifier = Modifier
) {

    val pagingMovies = uiState.movies.collectAsLazyPagingItems()

    Scaffold(
        topBar = {
            MovieAppBar(R.string.search_movies)
        },
        content = { paddingValues ->
            SearchContent(
                paddingValues = paddingValues,
                pagingMovies = pagingMovies,
                query = uiState.query,
                onEvent = {
                    onEvent(it)
                },
                onSearch = {
                    onFetch(it)
                },
                onDetail = {
                    navigateToDetailMovie(it)
                }
            )
        }
    )
}