package pl.michalboryczko.quickmaths.ui.game

import pl.michalboryczko.quickmaths.model.Exercise
import java.util.*

data class ExerciseInput(
        val operator: String,
        val firstRange: IntRange,
        val secondRange: IntRange
)

class ExerciseProvider(
        private val level: Int,
        private val serverSeed: Long,
        private val template: String = "X Y Z= ?"
) {

    private val currentBounds: IntRange
    private val levelOneBounds = 1 .. 20
    private val levelTwoBounds = 1 .. 100
    private val levelThreeBounds= 1 .. 1000
    private val probabilityOfAddition = 45
    private val probabilityOfSubtraction = 35
    private val firstRange = 1..probabilityOfAddition
    private val secondRange = probabilityOfAddition .. probabilityOfAddition + probabilityOfSubtraction


    private var equtionNr: Int = 1
    private var seed: Long

    init {
        if(level == 1) currentBounds = levelOneBounds
        else if(level == 2) currentBounds = levelTwoBounds
        else currentBounds = levelThreeBounds
        seed = serverSeed
    }

    private fun IntRange.getNumber(): Int{
        /*val r = Random()*/
        System.out.println("seed: $seed first: $first last: $last")
        return ((seed % (last - first)) + first).toInt()
        /*
        return r.nextInt(this.first + this.last) + this.first*/
    }

    private fun getSubtractionWithPositiveResult(): Exercise{
        val first = currentBounds.getNumber()
        val second = (1..first+1).getNumber()

        return createExercise(ExerciseInput("-", currentBounds, (1..first+1)))
    }

    private fun getAdditionExercise(): Exercise = createExercise(ExerciseInput("+", currentBounds, currentBounds))
    private fun getMultiplicationExercise(): Exercise{
        val multiplicationBounds = (1 .. currentBounds.last/5)
        return createExercise(ExerciseInput("·", currentBounds, multiplicationBounds))
    }


    private fun createExercise(obj: ExerciseInput): Exercise{
        val first = obj.firstRange.getNumber()
        val second = obj.secondRange.getNumber()

        val equation =
                template.replace("X", first.toString(), true)
                        .replace("Y", obj.operator, true)
                        .replace("Z", second.toString(), true)

        return Exercise(equation, first - second)
    }


    fun getEquation(): Exercise{
        val draw = seed % 100
        seed -= 28 * equtionNr++

        /*System.out.println("DRAW!!!!!!!! $draw")
        System.out.println("SEED!!!!!!!! $seed")*/

        return when( draw ){
            in firstRange -> getSubtractionWithPositiveResult()
            in secondRange -> getAdditionExercise()
            else -> getMultiplicationExercise()
        }
    }

}