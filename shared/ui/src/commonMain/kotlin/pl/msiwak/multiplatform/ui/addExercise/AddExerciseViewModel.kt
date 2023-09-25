package pl.msiwak.multiplatform.ui.addExercise

import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant
import kotlinx.datetime.toLocalDateTime
import pl.msiwak.multiplatform.core.ViewModel
import pl.msiwak.multiplatform.commonObject.DateFilterType
import pl.msiwak.multiplatform.commonObject.Exercise
import pl.msiwak.multiplatform.commonObject.ExerciseType
import pl.msiwak.multiplatform.commonObject.FormattedResultData
import pl.msiwak.multiplatform.commonObject.ResultData
import pl.msiwak.multiplatform.commonObject.SortType
import pl.msiwak.multiplatform.domain.summaries.FormatDateUseCase
import pl.msiwak.multiplatform.domain.summaries.FormatResultsUseCase
import pl.msiwak.multiplatform.domain.summaries.FormatStringToDateUseCase
import pl.msiwak.multiplatform.domain.summaries.GetExerciseUseCase
import pl.msiwak.multiplatform.domain.summaries.UpdateExerciseUseCase
import pl.msiwak.multiplatform.utils.extensions.isNumber
import pl.msiwak.multiplatform.utils.extensions.isTime
import pl.msiwak.multiplatform.utils.extensions.safeToDouble
import pl.msiwak.multiplatform.utils.DATE_REGEX
import pl.msiwak.multiplatform.utils.NUMBER_REGEX_COMMA
import pl.msiwak.multiplatform.utils.NUMBER_REGEX_DOT
import pl.msiwak.multiplatform.utils.TIME_REGEX

class AddExerciseViewModel(
    id: String,
    private val updateExerciseUseCase: UpdateExerciseUseCase,
    private val formatDateUseCase: FormatDateUseCase,
    private val formatResultsUseCase: FormatResultsUseCase,
    private val getExerciseUseCase: GetExerciseUseCase,
    private val formatStringToDateUseCase: FormatStringToDateUseCase,
) : ViewModel() {

    private val _viewState = MutableStateFlow(pl.msiwak.multiplatform.ui.addExercise.AddExerciseState())
    val viewState: StateFlow<pl.msiwak.multiplatform.ui.addExercise.AddExerciseState> = _viewState

    private val _viewEvent = MutableSharedFlow<pl.msiwak.multiplatform.ui.addExercise.AddExerciseEvent>(extraBufferCapacity = 1)
    val viewEvent: SharedFlow<pl.msiwak.multiplatform.ui.addExercise.AddExerciseEvent> = _viewEvent

    private var pickedDate: LocalDateTime =
        Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())

    private val currentResults: MutableList<ResultData> = mutableListOf()

    private val currentExercise = MutableStateFlow(Exercise())

    private val exerciseId = id

    private var exerciseToRemovePosition: Int? = null

    private var exerciseName: String? = null

    init {
        viewModelScope.launch {
            val exerciseWithUnit = getExerciseUseCase(exerciseId)
            currentExercise.value = exerciseWithUnit.exercise ?: Exercise()
            currentResults.addAll(currentExercise.value.results)
            val results = formatResultsUseCase(currentExercise.value.results)
            exerciseName = currentExercise.value.exerciseTitle
            _viewState.value = _viewState.value.copy(
                exerciseTitle = currentExercise.value.exerciseTitle,
                exerciseType = currentExercise.value.exerciseType,
                results = results,
                resultDataTitles = setTableTitles(exerciseWithUnit.exercise?.exerciseType),
                unit = exerciseWithUnit.unit ?: "",
                newResultData = _viewState.value.newResultData.copy(
                    date = formatDateUseCase(
                        Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
                    )
                )
            )
        }
    }

    private fun setTableTitles(exerciseType: ExerciseType?): List<String> {
        return when (exerciseType) {
            ExerciseType.RUNNING -> listOf("Distance", "Time", "Date")
            ExerciseType.GYM -> listOf("Weight", "Reps", "Date")
//            ExerciseType.OTHER -> emptyList()
            else -> listOf("Distance", "Time", "Date")
        }
    }

    fun onPause() {
        viewModelScope.launch {
            val newTitle = viewState.value.exerciseTitle
            if (exerciseName != newTitle) {
                updateExerciseUseCase(currentExercise.value.copy(exerciseTitle = newTitle))
            }
        }
    }

    fun onExerciseTitleChanged(title: String) {
        _viewState.value = _viewState.value.copy(exerciseTitle = title)
    }

    fun onAddNewResultClicked() {
        _viewState.value = _viewState.value.copy(isResultFieldEnabled = true)
    }

    fun onSaveResultClicked() {
        val exerciseType = _viewState.value.exerciseType
        val savedResult = _viewState.value.newResultData.result
        val savedAmount = _viewState.value.newResultData.amount
        val savedDate = _viewState.value.newResultData.date

        if (savedResult.isEmpty()) {
            _viewEvent.tryEmit(
                pl.msiwak.multiplatform.ui.addExercise.AddExerciseEvent.FocusOnInput(
                    1
                )
            )
            return
        }
        if (savedAmount.isEmpty()) {
            _viewEvent.tryEmit(
                pl.msiwak.multiplatform.ui.addExercise.AddExerciseEvent.FocusOnInput(
                    2
                )
            )
            return
        }
        if (savedDate.isEmpty()) {
            _viewEvent.tryEmit(
                pl.msiwak.multiplatform.ui.addExercise.AddExerciseEvent.FocusOnInput(
                    3
                )
            )
            return
        }
        if (!savedDate.matches(Regex(DATE_REGEX))) {
            _viewState.value = _viewState.value.copy(
                newResultData = _viewState.value.newResultData.copy(isDateError = true)
            )
            _viewEvent.tryEmit(
                pl.msiwak.multiplatform.ui.addExercise.AddExerciseEvent.FocusOnInput(
                    3
                )
            )
            return
        }


        if (!(savedResult.matches(Regex(NUMBER_REGEX_DOT)) || savedResult.matches(
                Regex(
                    NUMBER_REGEX_COMMA
                )
            ) && exerciseType == ExerciseType.GYM)
        ) {
            _viewState.value = _viewState.value.copy(
                newResultData = _viewState.value.newResultData.copy(isResultError = true)
            )
            _viewEvent.tryEmit(
                pl.msiwak.multiplatform.ui.addExercise.AddExerciseEvent.FocusOnInput(
                    1
                )
            )
            return
        }

        if (!(savedAmount.matches(Regex(NUMBER_REGEX_DOT)) || savedAmount.matches(
                Regex(
                    NUMBER_REGEX_DOT
                )
            )) && exerciseType == ExerciseType.GYM
        ) {
            _viewState.value = _viewState.value.copy(
                newResultData = _viewState.value.newResultData.copy(isAmountError = true)
            )
            _viewEvent.tryEmit(
                pl.msiwak.multiplatform.ui.addExercise.AddExerciseEvent.FocusOnInput(
                    2
                )
            )
            return
        }

        if (!(savedAmount.matches(Regex(TIME_REGEX))) && exerciseType == ExerciseType.RUNNING
        ) {
            _viewState.value = _viewState.value.copy(
                newResultData = _viewState.value.newResultData.copy(isAmountError = true)
            )
            _viewEvent.tryEmit(
                pl.msiwak.multiplatform.ui.addExercise.AddExerciseEvent.FocusOnInput(
                    2
                )
            )
            return
        }

        viewModelScope.launch {
            val data = ResultData(
                savedResult.safeToDouble(),
                savedAmount,
                formatStringToDateUseCase(savedDate)
            )
            currentResults.add(0, data)
            saveResult()

            _viewState.value = _viewState.value.copy(
                results = formatResultsUseCase(currentResults),
                isResultFieldEnabled = false,
                newResultData = FormattedResultData(date = _viewState.value.newResultData.date)
            )
        }
    }

    private suspend fun saveResult() {
        val newExercise = currentExercise.value.copy(
            results = currentResults
        )
        updateExerciseUseCase(newExercise)
    }

    fun onDateClicked() {
        _viewEvent.tryEmit(pl.msiwak.multiplatform.ui.addExercise.AddExerciseEvent.OpenCalendar)
    }

    fun onDatePicked(date: LocalDateTime) {
        val formattedDate = formatDateUseCase(date)
        pickedDate = date
        _viewState.value =
            _viewState.value.copy(newResultData = _viewState.value.newResultData.copy(date = formattedDate))
    }

    fun onResultLongClicked(resultIndex: Int) {
        exerciseToRemovePosition = resultIndex
        _viewState.value = _viewState.value.copy(isRemoveExerciseDialogVisible = true)
    }

    fun onLabelClicked(labelPosition: Int) {
        val currentSortType = _viewState.value.sortType
        val sortType = when {
            labelPosition == 0 && currentSortType != SortType.RESULT_DECREASING -> SortType.RESULT_DECREASING
            labelPosition == 0 && currentSortType != SortType.RESULT_INCREASING -> SortType.RESULT_INCREASING
            labelPosition == 1 && currentSortType != SortType.AMOUNT_DECREASING -> SortType.AMOUNT_DECREASING
            labelPosition == 1 && currentSortType != SortType.AMOUNT_INCREASING -> SortType.AMOUNT_INCREASING
            labelPosition == 2 && currentSortType != SortType.DATE_DECREASING -> SortType.DATE_DECREASING
            labelPosition == 2 && currentSortType != SortType.DATE_INCREASING -> SortType.DATE_INCREASING
            else -> SortType.DATE_DECREASING
        }
        _viewState.value = _viewState.value.copy(sortType = sortType)
        when (sortType) {
            SortType.RESULT_INCREASING -> currentResults.sortBy { it.result }
            SortType.AMOUNT_INCREASING -> currentResults.sortBy { it.amount }
            SortType.DATE_INCREASING -> currentResults.sortBy { it.date }
            SortType.RESULT_DECREASING -> currentResults.sortByDescending { it.result }
            SortType.AMOUNT_DECREASING -> currentResults.sortByDescending { it.amount }
            SortType.DATE_DECREASING -> currentResults.sortByDescending { it.date }
        }
        _viewState.value = _viewState.value.copy(results = formatResultsUseCase(currentResults))
    }

    fun onResultRemoved() {
        viewModelScope.launch {
            exerciseToRemovePosition?.let {
                currentResults.removeAt(it)
                val results = formatResultsUseCase(currentResults)
                val newExercise = currentExercise.value.copy(
                    results = currentResults
                )
                updateExerciseUseCase(newExercise)
                _viewState.value = _viewState.value.copy(results = results)
            }
            _viewState.value = _viewState.value.copy(isRemoveExerciseDialogVisible = false)
        }
    }

    fun onPopupDismissed() {
        _viewState.value = _viewState.value.copy(isRemoveExerciseDialogVisible = false)
    }


    fun onResultValueChanged(text: String) {
        _viewState.value =
            _viewState.value.copy(
                newResultData = _viewState.value.newResultData.copy(
                    result = text.filter { it.isNumber() },
                    isResultError = false
                )
            )
    }

    fun onAmountValueChanged(text: String) {
        val amount = text.filter {
            if (_viewState.value.exerciseType == ExerciseType.RUNNING) {
                it.isTime()
            } else {
                it.isNumber()
            }
        }
        _viewState.value =
            _viewState.value.copy(
                newResultData = _viewState.value.newResultData.copy(
                    amount = amount,
                    isAmountError = false
                )
            )
    }

    fun onDateValueChanged(text: String) {
        _viewState.value =
            _viewState.value.copy(newResultData = _viewState.value.newResultData.copy(date = text))

    }

    fun onTabClicked(item: DateFilterType) {
        val pos = _viewState.value.filter.indexOf(item)
        viewState.value.filter.forEach { dateFilter ->
            if (dateFilter == item) {
                filterResults(dateFilter)
            }
        }
        _viewState.value = viewState.value.copy(selectedFilterPosition = pos)
    }

    private fun filterResults(dateFilter: DateFilterType) {
        when (dateFilter) {
            DateFilterType.ALL -> filterAll()
            DateFilterType.DAY -> filterDay()
            DateFilterType.WEEK -> filter(7)
            DateFilterType.MONTH -> filter(31)
            DateFilterType.YEAR -> filter(365)
        }
    }

    private fun filterAll() {
        val newResults = formatResultsUseCase(currentResults)
        _viewState.value = _viewState.value.copy(results = newResults)
    }

    private fun filterDay() {
        val currentDate = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
        val newResults = formatResultsUseCase(currentResults.filter {
            it.date.dayOfYear == currentDate.dayOfYear
        })
        _viewState.value = _viewState.value.copy(results = newResults)
    }

    private fun filter(previousDaysCount: Int) {
        val currentDate = Clock.System.now()
        val newResults = formatResultsUseCase(currentResults.filter {
            val diff =
                currentDate.minus(it.date.toInstant(TimeZone.currentSystemDefault())).inWholeDays
            diff in 0..previousDaysCount
        })
        _viewState.value = _viewState.value.copy(results = newResults)
    }
}