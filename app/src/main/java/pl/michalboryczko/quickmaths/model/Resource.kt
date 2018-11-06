package pl.michalboryczko.quickmaths.model


enum class Status {
    SUCCESS,
    ERROR,
    LOADING;

    /**
     * Returns `true` if the [Status] is loading else `false`.
     */
    fun isLoading() = this == LOADING
}

/**
 * A generic class that holds a value with its loading status.
 * @param <T>
</T> */
data class Resource<T>(var status: Status, var data: T? = null, var message: String? = null) {

    companion object {
        /**
         * Creates [Resource] object with `SUCCESS` status and [data].
         */
        fun <T> success(data: T): Resource<T> = Resource(Status.SUCCESS, data)

        /**
         * Creates [Resource] object with `LOADING` status to notify
         * the UI to showing loading.
         */
        fun <T> loading(): Resource<T> = Resource(Status.LOADING)

        /**
         * Creates [Resource] object with `ERROR` status and [message].
         */
        fun <T> error(message: String?): Resource<T> = Resource(Status.ERROR, message = message)
    }
}

data class EmptyResource(var status: Status, var message: String? = null) {

    companion object {
        /**
         * Creates [Resource] object with `SUCCESS` status and [data].
         */
        fun success() = EmptyResource(Status.SUCCESS)

        /**
         * Creates [Resource] object with `LOADING` status to notify
         * the UI to showing loading.
         */
        fun loading()  = EmptyResource(Status.LOADING)

        /**
         * Creates [Resource] object with `ERROR` status and [message].
         */
        fun error(message: String?) = EmptyResource(Status.ERROR, message = message)
    }
}