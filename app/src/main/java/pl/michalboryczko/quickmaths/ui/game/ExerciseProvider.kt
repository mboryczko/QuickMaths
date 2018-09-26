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
        private val template: String = "X Y Z= ?"
) {

    private val currentBounds: IntRange
    private val levelOneBounds = 0 .. 20
    private val levelTwoBounds = 0 .. 100
    private val levelThreeBounds= 0 .. 1000

    private val probabilityOfAddition = 45
    private val probabilityOfSubtraction = 35


    init {
        if(level == 1) currentBounds = levelOneBounds
        else if(level == 2) currentBounds = levelTwoBounds
        else currentBounds = levelThreeBounds
    }

    private fun IntRange.getRandomNr(): Int{
        val r = Random()
        return r.nextInt(this.first + this.last) + this.first
    }

    fun getSubtractionWithPositiveResult(): Exercise{
        val first = currentBounds.getRandomNr()
        val second = (0..first).getRandomNr()

        return createExercise(ExerciseInput("-", currentBounds, (0..first)))
    }

    fun getAdditionExercise(): Exercise = createExercise(ExerciseInput("+", currentBounds, currentBounds))
    fun getMultiplicationExercise(): Exercise{
        val multiplicationBounds = (0 .. currentBounds.last/5)
        return createExercise(ExerciseInput("·", currentBounds, multiplicationBounds))
    }


    fun createExercise(obj: ExerciseInput): Exercise{
        val first = obj.firstRange.getRandomNr()
        val second = obj.secondRange.getRandomNr()

        val equation =
                template.replace("X", first.toString(), true)
                        .replace("Y", obj.operator, true)
                        .replace("Z", second.toString(), true)

        return Exercise(equation, first - second)
    }


    fun getEquation(): Exercise{
        val draw = Random().nextInt(100)
        val firstRange = 0..probabilityOfAddition
        val secondRange = probabilityOfAddition .. probabilityOfAddition + probabilityOfSubtraction

        when( draw ){
            in firstRange -> return getSubtractionWithPositiveResult()
            in secondRange -> return getAdditionExercise()
            else -> return getMultiplicationExercise()
        }
    }

}