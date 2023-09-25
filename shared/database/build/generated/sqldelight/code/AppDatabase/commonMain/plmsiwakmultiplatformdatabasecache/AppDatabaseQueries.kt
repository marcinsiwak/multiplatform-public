package plmsiwakmultiplatformdatabasecache

import app.cash.sqldelight.Query
import app.cash.sqldelight.TransacterImpl
import app.cash.sqldelight.db.QueryResult
import app.cash.sqldelight.db.SqlCursor
import app.cash.sqldelight.db.SqlDriver
import kotlin.Any
import kotlin.String
import kotlin.collections.List
import pl.msiwak.multiplatform.commonObject.Exercise
import pl.msiwak.multiplatform.commonObject.ExerciseType
import pl.msiwak.multiplatform.commonObject.ResultData

public class AppDatabaseQueries(
  driver: SqlDriver,
  private val ExerciseDBAdapter: ExerciseDB.Adapter,
  private val CategoryDBAdapter: CategoryDB.Adapter,
) : TransacterImpl(driver) {
  public fun <T : Any> selectFromExercise(id: String, mapper: (
    id: String,
    categoryId: String,
    exerciseTitle: String,
    results: List<ResultData>,
    exerciseType: ExerciseType,
  ) -> T): Query<T> = SelectFromExerciseQuery(id) { cursor ->
    mapper(
      cursor.getString(0)!!,
      cursor.getString(1)!!,
      cursor.getString(2)!!,
      ExerciseDBAdapter.resultsAdapter.decode(cursor.getString(3)!!),
      ExerciseDBAdapter.exerciseTypeAdapter.decode(cursor.getString(4)!!)
    )
  }

  public fun selectFromExercise(id: String): Query<ExerciseDB> = selectFromExercise(id) { id_,
      categoryId, exerciseTitle, results, exerciseType ->
    ExerciseDB(
      id_,
      categoryId,
      exerciseTitle,
      results,
      exerciseType
    )
  }

  public fun <T : Any> selectAllFromExercise(mapper: (
    id: String,
    categoryId: String,
    exerciseTitle: String,
    results: List<ResultData>,
    exerciseType: ExerciseType,
  ) -> T): Query<T> = Query(1_856_265_186, arrayOf("ExerciseDB"), driver, "AppDatabase.sq",
      "selectAllFromExercise", "SELECT * FROM ExerciseDB") { cursor ->
    mapper(
      cursor.getString(0)!!,
      cursor.getString(1)!!,
      cursor.getString(2)!!,
      ExerciseDBAdapter.resultsAdapter.decode(cursor.getString(3)!!),
      ExerciseDBAdapter.exerciseTypeAdapter.decode(cursor.getString(4)!!)
    )
  }

  public fun selectAllFromExercise(): Query<ExerciseDB> = selectAllFromExercise { id, categoryId,
      exerciseTitle, results, exerciseType ->
    ExerciseDB(
      id,
      categoryId,
      exerciseTitle,
      results,
      exerciseType
    )
  }

  public fun <T : Any> selectFromCategory(id: String, mapper: (
    id: String,
    name: String,
    exercises: List<Exercise>,
    exerciseType: ExerciseType,
  ) -> T): Query<T> = SelectFromCategoryQuery(id) { cursor ->
    mapper(
      cursor.getString(0)!!,
      cursor.getString(1)!!,
      CategoryDBAdapter.exercisesAdapter.decode(cursor.getString(2)!!),
      CategoryDBAdapter.exerciseTypeAdapter.decode(cursor.getString(3)!!)
    )
  }

  public fun selectFromCategory(id: String): Query<CategoryDB> = selectFromCategory(id) { id_, name,
      exercises, exerciseType ->
    CategoryDB(
      id_,
      name,
      exercises,
      exerciseType
    )
  }

  public fun <T : Any> selectAllFromCategory(mapper: (
    id: String,
    name: String,
    exercises: List<Exercise>,
    exerciseType: ExerciseType,
  ) -> T): Query<T> = Query(-149_547_256, arrayOf("CategoryDB"), driver, "AppDatabase.sq",
      "selectAllFromCategory", "SELECT * FROM CategoryDB") { cursor ->
    mapper(
      cursor.getString(0)!!,
      cursor.getString(1)!!,
      CategoryDBAdapter.exercisesAdapter.decode(cursor.getString(2)!!),
      CategoryDBAdapter.exerciseTypeAdapter.decode(cursor.getString(3)!!)
    )
  }

  public fun selectAllFromCategory(): Query<CategoryDB> = selectAllFromCategory { id, name,
      exercises, exerciseType ->
    CategoryDB(
      id,
      name,
      exercises,
      exerciseType
    )
  }

  public fun <T : Any> selectCategoryWithExercise(id: String, mapper: (
    id: String,
    name: String,
    exercises: List<Exercise>,
    exerciseType: ExerciseType,
    id_: String,
    categoryId: String,
    exerciseTitle: String,
    results: List<ResultData>,
    exerciseType_: ExerciseType,
  ) -> T): Query<T> = SelectCategoryWithExerciseQuery(id) { cursor ->
    mapper(
      cursor.getString(0)!!,
      cursor.getString(1)!!,
      CategoryDBAdapter.exercisesAdapter.decode(cursor.getString(2)!!),
      CategoryDBAdapter.exerciseTypeAdapter.decode(cursor.getString(3)!!),
      cursor.getString(4)!!,
      cursor.getString(5)!!,
      cursor.getString(6)!!,
      ExerciseDBAdapter.resultsAdapter.decode(cursor.getString(7)!!),
      ExerciseDBAdapter.exerciseTypeAdapter.decode(cursor.getString(8)!!)
    )
  }

  public fun selectCategoryWithExercise(id: String): Query<SelectCategoryWithExercise> =
      selectCategoryWithExercise(id) { id_, name, exercises, exerciseType, id__, categoryId,
      exerciseTitle, results, exerciseType_ ->
    SelectCategoryWithExercise(
      id_,
      name,
      exercises,
      exerciseType,
      id__,
      categoryId,
      exerciseTitle,
      results,
      exerciseType_
    )
  }

  public fun <T : Any> selectAllCategoriesWithExercise(mapper: (
    id: String,
    name: String,
    exercises: List<Exercise>,
    exerciseType: ExerciseType,
    id_: String,
    categoryId: String,
    exerciseTitle: String,
    results: List<ResultData>,
    exerciseType_: ExerciseType,
  ) -> T): Query<T> = Query(1_808_275_770, arrayOf("CategoryDB", "ExerciseDB"), driver,
      "AppDatabase.sq", "selectAllCategoriesWithExercise", """
  |SELECT *
  |FROM CategoryDB JOIN ExerciseDB
  """.trimMargin()) { cursor ->
    mapper(
      cursor.getString(0)!!,
      cursor.getString(1)!!,
      CategoryDBAdapter.exercisesAdapter.decode(cursor.getString(2)!!),
      CategoryDBAdapter.exerciseTypeAdapter.decode(cursor.getString(3)!!),
      cursor.getString(4)!!,
      cursor.getString(5)!!,
      cursor.getString(6)!!,
      ExerciseDBAdapter.resultsAdapter.decode(cursor.getString(7)!!),
      ExerciseDBAdapter.exerciseTypeAdapter.decode(cursor.getString(8)!!)
    )
  }

  public fun selectAllCategoriesWithExercise(): Query<SelectAllCategoriesWithExercise> =
      selectAllCategoriesWithExercise { id, name, exercises, exerciseType, id_, categoryId,
      exerciseTitle, results, exerciseType_ ->
    SelectAllCategoriesWithExercise(
      id,
      name,
      exercises,
      exerciseType,
      id_,
      categoryId,
      exerciseTitle,
      results,
      exerciseType_
    )
  }

  public fun updateExercise(
    id: String,
    categoryId: String,
    exerciseTitle: String,
    results: List<ResultData>,
    exerciseType: ExerciseType,
  ) {
    driver.execute(1_855_133_062, """
        |INSERT OR REPLACE INTO ExerciseDB(id, categoryId, exerciseTitle, results, exerciseType)
        |VALUES (?, ?,?,?, ?)
        """.trimMargin(), 5) {
          bindString(0, id)
          bindString(1, categoryId)
          bindString(2, exerciseTitle)
          bindString(3, ExerciseDBAdapter.resultsAdapter.encode(results))
          bindString(4, ExerciseDBAdapter.exerciseTypeAdapter.encode(exerciseType))
        }
    notifyQueries(1_855_133_062) { emit ->
      emit("ExerciseDB")
    }
  }

  public fun insertExercise(
    id: String,
    categoryId: String,
    exerciseTitle: String,
    results: List<ResultData>,
    exerciseType: ExerciseType,
  ) {
    driver.execute(-1_986_875_530, """
        |INSERT INTO ExerciseDB(id, categoryId, exerciseTitle, results, exerciseType)
        |VALUES (?, ?,?,?, ?)
        """.trimMargin(), 5) {
          bindString(0, id)
          bindString(1, categoryId)
          bindString(2, exerciseTitle)
          bindString(3, ExerciseDBAdapter.resultsAdapter.encode(results))
          bindString(4, ExerciseDBAdapter.exerciseTypeAdapter.encode(exerciseType))
        }
    notifyQueries(-1_986_875_530) { emit ->
      emit("ExerciseDB")
    }
  }

  public fun removeAllExercises() {
    driver.execute(670_478_563, """DELETE FROM ExerciseDB""", 0)
    notifyQueries(670_478_563) { emit ->
      emit("ExerciseDB")
    }
  }

  public fun removeExercise(id: String) {
    driver.execute(-609_490_367, """
        |DELETE FROM ExerciseDB
        |WHERE id = ?
        """.trimMargin(), 1) {
          bindString(0, id)
        }
    notifyQueries(-609_490_367) { emit ->
      emit("ExerciseDB")
    }
  }

  public fun updateCategory(
    id: String,
    name: String,
    exercises: List<Exercise>,
    exerciseType: ExerciseType,
  ) {
    driver.execute(-150_679_380, """
        |INSERT OR REPLACE INTO CategoryDB(id, name, exercises, exerciseType)
        |VALUES (?, ?,?, ?)
        """.trimMargin(), 4) {
          bindString(0, id)
          bindString(1, name)
          bindString(2, CategoryDBAdapter.exercisesAdapter.encode(exercises))
          bindString(3, CategoryDBAdapter.exerciseTypeAdapter.encode(exerciseType))
        }
    notifyQueries(-150_679_380) { emit ->
      emit("CategoryDB")
    }
  }

  public fun insertCategory(
    id: String,
    name: String,
    exercises: List<Exercise>,
    exerciseType: ExerciseType,
  ) {
    driver.execute(302_279_324, """
        |INSERT INTO CategoryDB(id, name, exercises, exerciseType)
        |VALUES (?, ?,?, ?)
        """.trimMargin(), 4) {
          bindString(0, id)
          bindString(1, name)
          bindString(2, CategoryDBAdapter.exercisesAdapter.encode(exercises))
          bindString(3, CategoryDBAdapter.exerciseTypeAdapter.encode(exerciseType))
        }
    notifyQueries(302_279_324) { emit ->
      emit("CategoryDB")
    }
  }

  public fun removeAllCategories() {
    driver.execute(164_542_420, """DELETE FROM CategoryDB""", 0)
    notifyQueries(164_542_420) { emit ->
      emit("CategoryDB")
    }
  }

  public fun removeCategory(id: String) {
    driver.execute(1_679_664_487, """
        |DELETE FROM CategoryDB
        |WHERE id = ?
        """.trimMargin(), 1) {
          bindString(0, id)
        }
    notifyQueries(1_679_664_487) { emit ->
      emit("CategoryDB")
    }
  }

  private inner class SelectFromExerciseQuery<out T : Any>(
    public val id: String,
    mapper: (SqlCursor) -> T,
  ) : Query<T>(mapper) {
    override fun addListener(listener: Query.Listener) {
      driver.addListener("ExerciseDB", listener = listener)
    }

    override fun removeListener(listener: Query.Listener) {
      driver.removeListener("ExerciseDB", listener = listener)
    }

    override fun <R> execute(mapper: (SqlCursor) -> QueryResult<R>): QueryResult<R> =
        driver.executeQuery(-1_881_536_509, """
    |SELECT * FROM ExerciseDB
    |WHERE id = ?
    """.trimMargin(), mapper, 1) {
      bindString(0, id)
    }

    override fun toString(): String = "AppDatabase.sq:selectFromExercise"
  }

  private inner class SelectFromCategoryQuery<out T : Any>(
    public val id: String,
    mapper: (SqlCursor) -> T,
  ) : Query<T>(mapper) {
    override fun addListener(listener: Query.Listener) {
      driver.addListener("CategoryDB", listener = listener)
    }

    override fun removeListener(listener: Query.Listener) {
      driver.removeListener("CategoryDB", listener = listener)
    }

    override fun <R> execute(mapper: (SqlCursor) -> QueryResult<R>): QueryResult<R> =
        driver.executeQuery(407_618_345, """
    |SELECT * FROM CategoryDB
    |WHERE id = ?
    """.trimMargin(), mapper, 1) {
      bindString(0, id)
    }

    override fun toString(): String = "AppDatabase.sq:selectFromCategory"
  }

  private inner class SelectCategoryWithExerciseQuery<out T : Any>(
    public val id: String,
    mapper: (SqlCursor) -> T,
  ) : Query<T>(mapper) {
    override fun addListener(listener: Query.Listener) {
      driver.addListener("CategoryDB", "ExerciseDB", listener = listener)
    }

    override fun removeListener(listener: Query.Listener) {
      driver.removeListener("CategoryDB", "ExerciseDB", listener = listener)
    }

    override fun <R> execute(mapper: (SqlCursor) -> QueryResult<R>): QueryResult<R> =
        driver.executeQuery(-2_044_504_003, """
    |SELECT *
    |FROM CategoryDB
    |INNER JOIN ExerciseDB ON CategoryDB.id = ExerciseDB.categoryId
    |WHERE CategoryDB.id=?
    """.trimMargin(), mapper, 1) {
      bindString(0, id)
    }

    override fun toString(): String = "AppDatabase.sq:selectCategoryWithExercise"
  }
}
