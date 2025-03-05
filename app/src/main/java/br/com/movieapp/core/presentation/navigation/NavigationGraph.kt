package br.com.movieapp.core.presentation.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import br.com.movieapp.movie_popular_feature.presentation.MoviePopularScreen
import br.com.movieapp.movie_popular_feature.presentation.MoviePopularViewModel
import br.com.movieapp.search_movie_feature.presentation.MovieSearchScreen
import br.com.movieapp.search_movie_feature.presentation.MovieSearchViewModel

@Composable
fun NavigationGraph(
    navController: NavHostController,
    modifier: Modifier
) {
    NavHost(
        navController = navController,
        startDestination = BottomNavItem.MoviePopular.route
    ) {
        composable(BottomNavItem.MoviePopular.route) {

            val viewModel = hiltViewModel<MoviePopularViewModel>()

            MoviePopularScreen(
                uiState = viewModel.uiState,
                navigateToDetailMovie = {
                },
                modifier = modifier
            )
        }

        composable(BottomNavItem.MovieSearch.route) {
            val viewModel = hiltViewModel<MovieSearchViewModel>()

            MovieSearchScreen(
                uiState = viewModel.uiState,
                onEvent = viewModel::event,
                onFetch = viewModel::fetch,
                navigateToDetailMovie = {
                },
                modifier = modifier
            )
        }

        composable(BottomNavItem.MovieFavorite.route) {

        }
    }
}
