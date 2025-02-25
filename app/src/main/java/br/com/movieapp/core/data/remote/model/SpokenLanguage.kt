package br.com.movieapp.core.data.remote.model

import com.google.gson.annotations.SerializedName

data class SpokenLanguage(
    val name: String,
    val iso: String ,
    @SerializedName("english_name")
    val englishName: String
)