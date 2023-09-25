package pl.msiwak.multiplatform.database.dao

import pl.msiwak.multiplatform.commonObject.Exercise
import pl.msiwak.multiplatform.commonObject.ExerciseType
import pl.msiwak.multiplatform.commonObject.ResultData
import pl.msiwak.multiplatform.database.Database

class ExerciseDao(database: Database) {

    private val dbQuery = database.getDatabaseQueries()

    fun clearDatabase() = dbQuery.transaction {
        dbQuery.removeAllExercises()
    }

    fun getExercise(id: String): Exercise? {
        return dbQuery.selectFromExercise(id, ::mapExercise).executeAsOneOrNull()
    }

    fun getAllExercises(): List<Exercise> {
        return dbQuery.selectAllFromExercise(::mapExercise).executeAsList()
    }

    fun insertExercises(exercises: List<Exercise>) {
        dbQuery.transaction {
            exercises.forEach {
                insert(it)
            }
        }
    }

    fun insertExercise(exercise: Exercise) {
        return dbQuery.transactionWithResult {
            insert(exercise)
        }
    }

    private fun insert(exercise: Exercise) {
        dbQuery.insertExercise(
            id = exercise.id,
            categoryId = exercise.categoryId,
            exerciseTitle = exercise.exerciseTitle,
            results = exercise.results,
            exerciseType = exercise.exerciseType,
        )
    }

    fun updateExercise(exercise: Exercise) {
        dbQuery.updateExercise(
            exercise.id,
            exercise.categoryId,
            exercise.exerciseTitle,
            exercise.results,
            exercise.exerciseType
        )
    }

    fun removeExercise(id: String) {
        dbQuery.removeExercise(id)
    }

    private fun mapExercise(
        id: String,
        categoryId: String,
        exerciseTitle: String,
        results: List<ResultData>,
        exerciseType: ExerciseType
    ): Exercise {
        return Exercise(id, categoryId, exerciseTitle, results, exerciseType)
    }
}