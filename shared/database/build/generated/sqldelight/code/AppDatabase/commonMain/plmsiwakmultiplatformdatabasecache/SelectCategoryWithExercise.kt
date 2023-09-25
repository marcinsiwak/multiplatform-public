package plmsiwakmultiplatformdatabasecache

import kotlin.String
import kotlin.collections.List
import pl.msiwak.multiplatform.commonObject.Exercise
import pl.msiwak.multiplatform.commonObject.ExerciseType
import pl.msiwak.multiplatform.commonObject.ResultData

public data class SelectCategoryWithExercise(
  public val id: String,
  public val name: String,
  public val exercises: List<Exercise>,
  public val exerciseType: ExerciseType,
  public val id_: String,
  public val categoryId: String,
  public val exerciseTitle: String,
  public val results: List<ResultData>,
  public val exerciseType_: ExerciseType,
)
