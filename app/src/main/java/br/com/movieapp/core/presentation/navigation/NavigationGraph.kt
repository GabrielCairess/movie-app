package br.com.movieapp.core.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import br.com.movieapp.core.utils.Constants
import br.com.movieapp.movie_detail_feature.presentation.MovieDetailViewModel
import br.com.movieapp.movie_detail_feature.presentation.MovieDetailsScreen
import br.com.movieapp.movie_favorite_feature.presentation.MovieFavoriteScreen
import br.com.movieapp.movie_favorite_feature.presentation.MovieFavoriteViewModel
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
                    navController.navigate(BottomNavItem.MovieDetails.passMovieId(it))
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
                    navController.navigate(BottomNavItem.MovieDetails.passMovieId(it))
                },
                modifier = modifier
            )
        }

        composable(BottomNavItem.MovieFavorite.route) {
            val viewModel = hiltViewModel<MovieFavoriteViewModel>()
            val uiState = viewModel.uiState

            MovieFavoriteScreen(
                uiState = uiState,
                navigateToDetail = {
                    navController.navigate(BottomNavItem.MovieDetails.passMovieId(it))
                }
            )

        }

        composable(
            route = BottomNavItem.MovieDetails.route,
            arguments = listOf(
                navArgument(Constants.MOVIE_DETAIL_ARGUMENT_KEY) {
                    type = NavType.IntType
                    defaultValue = 0
                }
            )
        ) {
            val viewModel = hiltViewModel<MovieDetailViewModel>()
            val uiState = viewModel.uiState
            val getMovieDetail = viewModel::getMovieDetails
            MovieDetailsScreen(
                id = it.arguments?.getInt(Constants.MOVIE_DETAIL_ARGUMENT_KEY),
                uiState = uiState,
                getMovieDetails = getMovieDetail,
                modifier = modifier
            )
        }
    }
}
