package pl.michalboryczko.quickmaths.model

data class User(
        val email: String,
        val password: String,
        val username: String
)

data class LoginInput(
    val email: String,
    val password: String
)