package com.devtides.coroutinesroom.model

object LoginState {
    var isLoggedIn = false
    var user: User? = null

    fun logout() {
        isLoggedIn = false
        user = null
    }

    fun login(user: User) {
        isLoggedIn = true
        this.user = user
    }
}