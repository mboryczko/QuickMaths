package pl.michalboryczko.quickmaths.model

data class User(
        val email: String,
        val password: String,
        val username: String
){
    constructor(): this("", "", "")
}

data class LoginInput(
    val email: String,
    val password: String
)