package plmsiwakmultiplatformdatabasecache

import app.cash.sqldelight.ColumnAdapter
import kotlin.String
import kotlin.collections.List
import pl.msiwak.multiplatform.commonObject.Exercise
import pl.msiwak.multiplatform.commonObject.ExerciseType

public data class CategoryDB(
  public val id: String,
  public val name: String,
  public val exercises: List<Exercise>,
  public val exerciseType: ExerciseType,
) {
  public class Adapter(
    public val exercisesAdapter: ColumnAdapter<List<Exercise>, String>,
    public val exerciseTypeAdapter: ColumnAdapter<ExerciseType, String>,
  )
}
