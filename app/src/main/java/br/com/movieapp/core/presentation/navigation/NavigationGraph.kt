package br.com.movieapp.core.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import br.com.movieapp.movie_popular_feature.presentation.MoviePopularScreen
import br.com.movieapp.movie_popular_feature.presentation.MoviePopularViewModel

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

        }

        composable(BottomNavItem.MovieFavorite.route) {

        }
    }
}
