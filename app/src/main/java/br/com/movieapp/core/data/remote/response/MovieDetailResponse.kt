package br.com.movieapp.core.data.remote.response

import br.com.movieapp.core.data.remote.model.BelongsToCollection
import br.com.movieapp.core.data.remote.model.Genre
import br.com.movieapp.core.data.remote.model.ProductionCompany
import br.com.movieapp.core.data.remote.model.ProductionCountry
import br.com.movieapp.core.data.remote.model.SpokenLanguage
import com.google.gson.annotations.SerializedName

data class MovieDetailResponse(
    @SerializedName("original_language")
    val originalLanguage: String,
    @SerializedName("imdb_id")
    val imdbId: String,
    val video: Boolean,
    val title: String,
    @SerializedName("backdrop_path")
    val backdropPath: String,
    val revenue: Long,
    val genres: List<Genre>,
    val popularity: Double,
    @SerializedName("production_countries")
    val productionCountries: List<ProductionCountry>,
    val id: Int,
    @SerializedName("vote_count")
    val voteCount: Int,
    val budget: Int,
    val overview: String,
    @SerializedName("original_title")
    val originalTitle: String,
    val runtime: Int,
    @SerializedName("poster_path")
    val posterPath: String,
    @SerializedName("origin_country")
    val originCountry: List<String>,
    @SerializedName("spoken_languages")
    val spokenLanguages: List<SpokenLanguage>,
    @SerializedName("production_companies")
    val productionCompanies: List<ProductionCompany>,
    @SerializedName("release_date")
    val releaseDate: String,
    @SerializedName("vote_average")
    val voteAverage: Double,
    @SerializedName("belongs_to_collection")
    val belongsToCollection: BelongsToCollection,
    val tagline: String,
    val adult: Boolean,
    val homepage: String,
    val status: String
)