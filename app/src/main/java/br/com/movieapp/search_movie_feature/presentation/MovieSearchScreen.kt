package br.com.movieapp.search_movie_feature.presentation

import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.paging.compose.collectAsLazyPagingItems
import br.com.movieapp.R
import br.com.movieapp.search_movie_feature.presentation.components.SearchContent
import br.com.movieapp.search_movie_feature.presentation.state.MovieSearchState
import br.com.movieapp.ui.theme.black
import br.com.movieapp.ui.theme.white

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
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.search_movies),
                        color = white
                    )
                },
                backgroundColor = black
            )
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