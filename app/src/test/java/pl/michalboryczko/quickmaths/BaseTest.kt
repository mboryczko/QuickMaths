package pl.michalboryczko.quickmaths

import android.arch.core.executor.testing.InstantTaskExecutorRule
import org.junit.Rule
import org.mockito.Mockito
import org.mockito.stubbing.OngoingStubbing
import pl.michalboryczko.quickmaths.helper.RandomInputs

open class BaseTest {

    @Rule
    @JvmField
    val rule = InstantTaskExecutorRule()

    val random = RandomInputs()

    inline fun <reified T> mock() = Mockito.mock(T::class.java)
    inline fun <T> whenever(methodCall: T) : OngoingStubbing<T> =
            Mockito.`when`(methodCall)

}