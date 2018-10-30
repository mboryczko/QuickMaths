package pl.michalboryczko.quickmaths.model.login

import pl.michalboryczko.quickmaths.model.BaseCommunication
import pl.michalboryczko.quickmaths.model.State

data class LoginCommunication(
        val message: String? = null
): BaseCommunication()