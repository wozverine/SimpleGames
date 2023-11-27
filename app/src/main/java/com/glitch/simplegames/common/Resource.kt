package com.glitch.simplegames.common

sealed class Resource<out T : Any> {
    data class SaveScore<out T : Any>(val data: T) : Resource<T>()
    data class Error(val errorMessage: String) : Resource<Nothing>()
    data class Fail(val failMessage: String) : Resource<Nothing>()
    data class GamingState(val isGaming: Boolean) : Resource<Nothing>()
    data class IsWonScreen(val wonGame: Boolean) : Resource<Nothing>()
    data class IsWaiting(val waiting: Boolean) : Resource<Nothing>()
}
