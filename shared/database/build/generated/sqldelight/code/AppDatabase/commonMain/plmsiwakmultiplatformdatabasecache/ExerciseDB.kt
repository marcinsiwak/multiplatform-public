package plmsiwakmultiplatformdatabasecache

import app.cash.sqldelight.ColumnAdapter
import kotlin.String
import kotlin.collections.List
import pl.msiwak.multiplatform.commonObject.ExerciseType
import pl.msiwak.multiplatform.commonObject.ResultData

public data class ExerciseDB(
  public val id: String,
  public val categoryId: String,
  public val exerciseTitle: String,
  public val results: List<ResultData>,
  public val exerciseType: ExerciseType,
) {
  public class Adapter(
    public val resultsAdapter: ColumnAdapter<List<ResultData>, String>,
    public val exerciseTypeAdapter: ColumnAdapter<ExerciseType, String>,
  )
}
