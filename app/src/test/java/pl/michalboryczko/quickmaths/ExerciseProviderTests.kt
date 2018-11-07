package pl.michalboryczko.quickmaths

import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import pl.michalboryczko.quickmaths.ui.game.ExerciseProvider


class ExerciseProviderTests: BaseTest() {

    fun IntRange.getNumber(seed: Long): Int{
        /*val r = Random()*/
        return ((seed % (last - first)) + first).toInt()
        /*
        return r.nextInt(this.first + this.last) + this.first*/
    }

    @Test
    fun testRange(){
        val seed = random.generateRandomLong()
        System.out.println(seed)
        val range = 1 .. 4
        System.out.println("${range.getNumber(seed)}")

        val range2 = 6 .. 10
        System.out.println("${range2.getNumber(seed)}")

        val range3 = 100 .. 111
        System.out.println("${range3.getNumber(seed)}")
    }

    @Test
    fun assertSameResultsForSameSeed(){
        val randomServerSeed = random.generateRandomLong()
        val ep1 = ExerciseProvider(1, randomServerSeed)
        val ep2 = ExerciseProvider(1, randomServerSeed)

        for(i in 1 .. 1000){
            val eq1 = ep1.getEquation()
            val eq2 = ep2.getEquation()
            //System.out.println("$eq1 | $eq2")
            assertEquals(eq1, eq2)
        }
    }



}
