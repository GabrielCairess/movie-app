package br.com.movieapp.movie_detail_feature.presentation.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.com.movieapp.R
import coil.compose.AsyncImage
import coil.request.ImageRequest

@Composable
fun MovieDetailBackdropImage(
    backdropPath: String,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(backdropPath)
                .crossfade(true)
                .error(R.drawable.ic_error_image)
                .placeholder(R.drawable.ic_placeholder)
                .build(),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Preview
@Composable
private fun MovieDetailBackdropImagePreview() {
    MovieDetailBackdropImage(
        backdropPath = "https://image.tmdb.org/t/p/w500/kqjL17yufvn9OVLyXYpvtyrFfak.jpg",
        modifier = Modifier.fillMaxWidth().height(200.dp)
    )
}