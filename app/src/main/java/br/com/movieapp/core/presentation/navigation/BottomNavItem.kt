package br.com.movieapp.core.presentation.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Details
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Movie
import androidx.compose.material.icons.filled.Search
import androidx.compose.ui.graphics.vector.ImageVector
import br.com.movieapp.core.utils.Constants.MOVIE_DETAIL_ARGUMENT_KEY

sealed class BottomNavItem(val title: String, val icon: ImageVector, val route: String) {
    object MoviePopular : BottomNavItem("Filmes Populares", Icons.Default.Movie, "movie_popular_screen")
    object MovieSearch : BottomNavItem("Pesquisar", Icons.Default.Search, "movie_search_screen")
    object MovieFavorite : BottomNavItem("Favoritos", Icons.Default.Favorite, "movie_favorite_screen")
}