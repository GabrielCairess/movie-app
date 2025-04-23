package br.com.movieapp.core.presentation.navigation

import br.com.movieapp.core.utils.Constants.MOVIE_DETAIL_ARGUMENT_KEY

sealed class DetailsScreenNav(val route: String) {
    object DetailsScreen : DetailsScreenNav(
        route = "movie_details_destination?$MOVIE_DETAIL_ARGUMENT_KEY={${MOVIE_DETAIL_ARGUMENT_KEY}}"
    ) {
        fun passMovieId(movieId: Int) =
            "movie_details_destination?$MOVIE_DETAIL_ARGUMENT_KEY=$movieId"

    }
}