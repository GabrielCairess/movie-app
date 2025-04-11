package br.com.movieapp.movie_detail_feature.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.movieapp.R
import br.com.movieapp.core.domain.MovieDetails


@Composable
fun MovieInfoContent(
    movieDetails: MovieDetails?,
    modifier: Modifier = Modifier
) {

    Row(
        horizontalArrangement = Arrangement.SpaceEvenly,
        modifier = modifier
    ) {
        MovieDetailInfo(
            name = stringResource(id = R.string.vote_average),
            value = movieDetails?.voteAverage.toString(),
        )

        MovieDetailInfo(
            name = stringResource(id = R.string.duration),
            value = stringResource(id = R.string.duration_minutes, movieDetails?.duration.toString()),
        )

        MovieDetailInfo(
            name = stringResource(id = R.string.release_date),
            value = movieDetails?.releaseDate.toString(),
        )
    }

}

@Composable
fun MovieDetailInfo(
    name: String,
    value: String,
    modifier: Modifier = Modifier
) {
    Column {
        Text(
            text = name,
            style = MaterialTheme.typography.subtitle2.copy(fontSize = 13.sp, letterSpacing = 1.sp),
            color = Color.DarkGray,
            modifier = modifier.align(Alignment.CenterHorizontally)
        )

        Text(
            text = value,
            style = MaterialTheme.typography.subtitle1.copy(fontWeight = FontWeight.SemiBold),
            color = Color.DarkGray,
            modifier = modifier
                .align(Alignment.CenterHorizontally)
                .padding(top = 4.dp)
        )
    }
}

@Preview
@Composable
private fun MovieDetailInfoContentPreview() {
    MovieInfoContent(
        movieDetails = MovieDetails(
            id = 1,
            title = "Title",
            genres = listOf("Action", "Adventure"),
            overview = "Overview",
            backDropPath = "https://image.tmdb.org/t/p/w500/kqjL17yufvn9OVLyXYpvtyrFfak.jpg",
            releaseDate = "2022-01-01",
            voteAverage = 8.5,
            duration = 120,
            voteCount = 1000
        ),
        modifier = Modifier.fillMaxWidth()
    )
}