package pl.msiwak.multiplatform.android.ui.addExercise

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import kotlinx.coroutines.flow.collectLatest
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf
import pl.msiwak.multiplatform.android.ui.components.PopupDialog
import pl.msiwak.multiplatform.android.ui.components.ResultsTableView
import pl.msiwak.multiplatform.android.ui.components.ResultsTimeFilterView
import pl.msiwak.multiplatform.android.ui.theme.dimens
import pl.msiwak.multiplatform.android.ui.theme.font
import pl.msiwak.multiplatform.android.ui.utils.OnLifecycleEvent
import pl.msiwak.multiplatform.android.ui.widgets.openCalendar
import pl.msiwak.multiplatform.commonResources.MR
import pl.msiwak.multiplatform.ui.addExercise.AddExerciseViewModel

@Composable
fun AddExerciseScreen(id: Long) {
    val viewModel = koinViewModel<AddExerciseViewModel> { parametersOf(id) }

    val state = viewModel.viewState.collectAsState()
    val context = LocalContext.current

    val focusRequesters = List(4) { remember { FocusRequester() } }

    OnLifecycleEvent { _, event ->
        when (event) {
            Lifecycle.Event.ON_PAUSE -> viewModel.onPause()
            else -> Unit
        }
    }

    LaunchedEffect(viewModel) {
        viewModel.viewEvent.collectLatest { value ->
            when (value) {
                pl.msiwak.multiplatform.ui.addExercise.AddExerciseEvent.OpenCalendar -> openCalendar(
                    context = context,
                    onValueChanged = {
                        viewModel.onDatePicked(it)
                    })

                is pl.msiwak.multiplatform.ui.addExercise.AddExerciseEvent.FocusOnInput -> {
                    focusRequesters[value.pos].requestFocus()
                    Toast.makeText(context, "Wrong input value", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    if (state.value.isRemoveExerciseDialogVisible) {
        PopupDialog(
            title = stringResource(MR.strings.remove_result_dialog_title.resourceId),
            description = stringResource(MR.strings.remove_result_dialog_description.resourceId),
            confirmButtonTitle = stringResource(MR.strings.yes.resourceId),
            dismissButtonTitle = stringResource(MR.strings.no.resourceId),
            onConfirmClicked = {
                viewModel.onResultRemoved()
            },
            onDismissClicked = {
                viewModel.onPopupDismissed()
            }
        )
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {

        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Top
        ) {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        start = MaterialTheme.dimens.space_12,
                        end = MaterialTheme.dimens.space_24
                    )
                    .padding(vertical = MaterialTheme.dimens.space_16),
                text = state.value.exerciseTitle,
                fontSize = MaterialTheme.font.font_24,
                color = MaterialTheme.colorScheme.onPrimary,
            )

            ResultsTimeFilterView(
                modifier = Modifier
                    .width(260.dp)
                    .padding(bottom = MaterialTheme.dimens.space_16),
                tabs = state.value.filter,
                selectedPos = state.value.selectedFilterPosition,
                onTabClicked = {
                    viewModel.onTabClicked(it)
                }
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                if (!state.value.isResultFieldEnabled) {
                    Button(
                        modifier = Modifier
                            .padding(bottom = MaterialTheme.dimens.space_16)
                            .padding(horizontal = MaterialTheme.dimens.space_16),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.tertiary,
                            contentColor = MaterialTheme.colorScheme.primary
                        ),
                        onClick = {
                            viewModel.onAddNewResultClicked()
                        }) {
                        Text(
                            text = stringResource(MR.strings.add_new_result.resourceId),
                        )
                    }
                } else
                    Button(
                        modifier = Modifier
                            .padding(bottom = MaterialTheme.dimens.space_16)
                            .padding(horizontal = MaterialTheme.dimens.space_16),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.tertiary,
                            contentColor = MaterialTheme.colorScheme.primary
                        ),
                        onClick = {
                            viewModel.onSaveResultClicked()
                        }) {
                        Text(
                            text = stringResource(MR.strings.add_result_save.resourceId),
                        )
                    }
            }

            ResultsTableView(
                modifier = Modifier,
                resultDataTitles = state.value.resultDataTitles,
                unit = state.value.unit,
                results = state.value.results,
                exerciseType = state.value.exerciseType,
                sortType = state.value.sortType,
                focusRequesters = focusRequesters,
                isNewResultEnabled = state.value.isResultFieldEnabled,
                newResultData = state.value.newResultData,
                onAddNewResultClicked = {
                    viewModel.onAddNewResultClicked()
                },
                onResultValueChanged = {
                    viewModel.onResultValueChanged(it)
                }, onAmountValueChanged = {
                    viewModel.onAmountValueChanged(it)
                }, onDateValueChanged = {
                    viewModel.onDateValueChanged(it)
                }, onDateClicked = {
                    viewModel.onDateClicked()
                }, onResultLongClick = { pos ->
                    viewModel.onResultLongClicked(pos)
                }, onLabelClicked = { pos ->
                    viewModel.onLabelClicked(pos)
                })
        }
    }
}

@Preview
@Composable
fun AddExerciseScreenPreview() {
    AddExerciseScreen(0)
}
