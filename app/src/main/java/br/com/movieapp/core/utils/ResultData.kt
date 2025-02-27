package br.com.movieapp.core.utils

sealed class ResultData<out T> {
    object Loading : ResultData<Nothing>()
    data class Success<out T>(val data: T) : ResultData<T>()
    data class Failure(val exception: Exception) : ResultData<Nothing>()
}