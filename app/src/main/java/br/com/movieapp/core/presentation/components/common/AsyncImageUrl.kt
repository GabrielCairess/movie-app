package br.com.movieapp.core.presentation.components.common

import androidx.annotation.DrawableRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import br.com.movieapp.R
import coil.compose.AsyncImage
import coil.request.ImageRequest

@Composable
fun AsyncImageUrl(
    imageUrl: String,
    crossFade: Boolean = true,
    @DrawableRes placeholder: Int = R.drawable.ic_placeholder,
    @DrawableRes errorImage: Int = R.drawable.ic_error_image,
    contentScale: ContentScale = ContentScale.FillHeight,
    modifier: Modifier = Modifier
) {
    AsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
            .data(imageUrl)
            .crossfade(crossFade)
            .error(errorImage)
            .placeholder(placeholder)
            .build(),
        contentDescription = "",
        contentScale = contentScale,
        modifier = modifier
    )
}