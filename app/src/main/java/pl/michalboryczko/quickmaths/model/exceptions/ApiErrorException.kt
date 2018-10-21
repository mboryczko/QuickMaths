package pl.michalboryczko.quickmaths.model.exceptions

import java.lang.Exception

class ApiErrorException(msg: String, errorBody: String?, code: Int): Exception() {

}