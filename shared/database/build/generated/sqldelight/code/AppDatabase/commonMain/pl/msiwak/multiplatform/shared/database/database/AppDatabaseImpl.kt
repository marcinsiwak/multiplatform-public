package pl.msiwak.multiplatform.shared.database.database

import app.cash.sqldelight.TransacterImpl
import app.cash.sqldelight.db.AfterVersion
import app.cash.sqldelight.db.QueryResult
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.db.SqlSchema
import kotlin.Long
import kotlin.Unit
import kotlin.reflect.KClass
import pl.msiwak.multiplatform.shared.database.AppDatabase
import plmsiwakmultiplatformdatabasecache.AppDatabaseQueries
import plmsiwakmultiplatformdatabasecache.CategoryDB
import plmsiwakmultiplatformdatabasecache.ExerciseDB

internal val KClass<AppDatabase>.schema: SqlSchema<QueryResult.Value<Unit>>
  get() = AppDatabaseImpl.Schema

internal fun KClass<AppDatabase>.newInstance(
  driver: SqlDriver,
  CategoryDBAdapter: CategoryDB.Adapter,
  ExerciseDBAdapter: ExerciseDB.Adapter,
): AppDatabase = AppDatabaseImpl(driver, CategoryDBAdapter, ExerciseDBAdapter)

private class AppDatabaseImpl(
  driver: SqlDriver,
  CategoryDBAdapter: CategoryDB.Adapter,
  ExerciseDBAdapter: ExerciseDB.Adapter,
) : TransacterImpl(driver), AppDatabase {
  override val appDatabaseQueries: AppDatabaseQueries = AppDatabaseQueries(driver,
      ExerciseDBAdapter, CategoryDBAdapter)

  public object Schema : SqlSchema<QueryResult.Value<Unit>> {
    override val version: Long
      get() = 1

    override fun create(driver: SqlDriver): QueryResult.Value<Unit> {
      driver.execute(null, """
          |CREATE TABLE ExerciseDB (
          |    id TEXT NOT NULL,
          |    categoryId TEXT NOT NULL,
          |    exerciseTitle    TEXT NOT NULL,
          |    results     TEXT    NOT NULL,
          |    exerciseType TEXT NOT NULL
          |)
          """.trimMargin(), 0)
      driver.execute(null, """
          |CREATE TABLE CategoryDB (
          |    id TEXT NOT NULL,
          |    name    TEXT NOT NULL,
          |    exercises     TEXT    NOT NULL,
          |    exerciseType TEXT NOT NULL
          |)
          """.trimMargin(), 0)
      return QueryResult.Unit
    }

    override fun migrate(
      driver: SqlDriver,
      oldVersion: Long,
      newVersion: Long,
      vararg callbacks: AfterVersion,
    ): QueryResult.Value<Unit> = QueryResult.Unit
  }
}
