package br.com.movieapp.movie_favorite_feature.presentation

import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import br.com.movieapp.R
import br.com.movieapp.core.domain.Movie
import br.com.movieapp.core.presentation.components.common.MovieAppBar
import br.com.movieapp.movie_favorite_feature.presentation.components.MovieFavoriteContent

@Composable
fun MovieFavoriteScreen(
    movies: List<Movie>,
    navigateToDetail: (Int) -> Unit,
    modifier: Modifier = Modifier
) {

    Scaffold(
        topBar = {
            MovieAppBar(R.string.favorite_movies)
        },
        content = { paddingValues ->
            MovieFavoriteContent(
                paddingValues = paddingValues,
                movies = movies,
                onFavoriteClick = { movieId ->
                    navigateToDetail(movieId)
                }
            )
        }
    )
}

@Preview
@Composable
private fun MovieFavoriteScreenPreview() {
    MovieFavoriteScreen(
        movies = emptyList(),
        navigateToDetail = {

        }
    )
}