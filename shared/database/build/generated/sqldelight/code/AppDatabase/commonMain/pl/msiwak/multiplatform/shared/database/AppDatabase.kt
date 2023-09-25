package pl.msiwak.multiplatform.shared.database

import app.cash.sqldelight.Transacter
import app.cash.sqldelight.db.QueryResult
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.db.SqlSchema
import kotlin.Unit
import pl.msiwak.multiplatform.shared.database.database.newInstance
import pl.msiwak.multiplatform.shared.database.database.schema
import plmsiwakmultiplatformdatabasecache.AppDatabaseQueries
import plmsiwakmultiplatformdatabasecache.CategoryDB
import plmsiwakmultiplatformdatabasecache.ExerciseDB

public interface AppDatabase : Transacter {
  public val appDatabaseQueries: AppDatabaseQueries

  public companion object {
    public val Schema: SqlSchema<QueryResult.Value<Unit>>
      get() = AppDatabase::class.schema

    public operator fun invoke(
      driver: SqlDriver,
      CategoryDBAdapter: CategoryDB.Adapter,
      ExerciseDBAdapter: ExerciseDB.Adapter,
    ): AppDatabase = AppDatabase::class.newInstance(driver, CategoryDBAdapter, ExerciseDBAdapter)
  }
}
