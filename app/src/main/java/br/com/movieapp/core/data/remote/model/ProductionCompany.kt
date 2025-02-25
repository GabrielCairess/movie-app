package br.com.movieapp.core.data.remote.model

import com.google.gson.annotations.SerializedName

data class ProductionCompany(
    @SerializedName("logo_path")
    val logoPath: String,
    val name: String,
    val id: Int = 0,
    @SerializedName("origin_country")
    val originCountry: String
)