package pl.michalboryczko.quickmaths.model.register

data class RegisterError(
        val emailError: Int? = null,
        val usernameError: Int? = null,
        val passwordError: Int? = null
)