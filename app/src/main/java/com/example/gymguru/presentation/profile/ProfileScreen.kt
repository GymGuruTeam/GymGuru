package com.example.gymguru.presentation.profile

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.gymguru.R
import com.example.gymguru.domain.model.UserWeight
import com.example.gymguru.presentation.composables.GymGuruButton
import com.example.gymguru.presentation.composables.GymGuruOutlinedTextField
import com.example.gymguru.presentation.ui.theme.dimensions
import kotlinx.coroutines.flow.collectLatest

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    navController: NavController,
    homeSnackBarHostState: SnackbarHostState,
    viewModel: ProfileViewModel = hiltViewModel()
) {
    val viewState = viewModel.viewState.value
    var isAddWeightDialogShown by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        viewModel.viewEvent.collectLatest { event ->
            when (event) {
                is ProfileViewEvent.ShowSnackBar -> homeSnackBarHostState.showSnackbar(
                    event.message
                )
            }
        }
    }

    if (isAddWeightDialogShown) {
        Dialog(
            onDismissRequest = { isAddWeightDialogShown = false },
            properties = DialogProperties(usePlatformDefaultWidth = false)
        ) {
            Box(
                Modifier
                    .clip(RoundedCornerShape(MaterialTheme.dimensions.m))
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.surface)
                    .padding(MaterialTheme.dimensions.xl)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .animateContentSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(MaterialTheme.dimensions.m)
                ) {
                    Text(
                        text = stringResource(R.string.add_current_weight),
                        style = MaterialTheme.typography.titleSmall
                    )

                    GymGuruOutlinedTextField(
                        modifier = Modifier.fillMaxWidth(0.7f),
                        state = viewState.weight,
                        keyboardType = KeyboardType.Decimal,
                        onValueChange = { viewModel.updateWeight(it) },
                        hint = stringResource(R.string.weight)
                    )

                    GymGuruButton(
                        onClick = {
                            viewModel.saveWeight()
                            isAddWeightDialogShown = false
                        },
                        enabled = !viewState.weight.isError && viewState.weight.value.isNotEmpty(),
                        text = stringResource(id = R.string.save)
                    )
                }
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .systemBarsPadding()
            .background(MaterialTheme.colorScheme.surface),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TopAppBar(
            title = {
                Text(
                    modifier = Modifier
                        .fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    text = "My profile",
                    style = MaterialTheme.typography.titleMedium
                )
            },
            actions = {
                IconButton(onClick = { viewModel.onEditProfileClick(!viewState.isEditMode) }) {
                    Icon(
                        imageVector = if (viewState.isEditMode) {
                            Icons.Default.Check
                        } else {
                            Icons.Default.Edit
                        },
                        contentDescription = "Edit profile"
                    )
                }
            }
        )

        Spacer(modifier = Modifier.height(MaterialTheme.dimensions.xl))

        GymGuruOutlinedTextField(
            modifier = Modifier.fillMaxWidth(0.7f),
            state = viewState.name,
            readOnly = !viewState.isEditMode,
            onValueChange = { viewModel.updateUsername(it) },
            hint = stringResource(R.string.name)
        )

        Spacer(modifier = Modifier.height(MaterialTheme.dimensions.l))

        GymGuruOutlinedTextField(
            modifier = Modifier.fillMaxWidth(0.7f),
            state = viewState.height,
            keyboardType = KeyboardType.Number,
            readOnly = !viewState.isEditMode,
            onValueChange = { viewModel.updateHeight(it) },
            hint = stringResource(R.string.height)
        )

        Spacer(modifier = Modifier.height(MaterialTheme.dimensions.l))

        UserWeightView(
            viewState = viewState,
            openAddWeightDialog = { isAddWeightDialogShown = true },
            deleteWeight = { viewModel.deleteUserWeight(it) }
        )
    }
}

@Composable
fun UserWeightView(
    viewState: ProfileScreenViewState,
    openAddWeightDialog: () -> Unit,
    deleteWeight: (UserWeight) -> Unit
) {
    val weightState = rememberLazyListState()

    Text(
        modifier = Modifier.padding(MaterialTheme.dimensions.m),
        text = stringResource(id = R.string.weight),
        style = MaterialTheme.typography.titleMedium,
        color = MaterialTheme.colorScheme.onBackground
    )
    LazyColumn(
        state = weightState,
        verticalArrangement = Arrangement.spacedBy(MaterialTheme.dimensions.xl)
    ) {
        if (viewState.isEditMode) {
            item {
                Card(
                    modifier = Modifier
                        .width(MaterialTheme.dimensions.cardMedium)
                        .padding(MaterialTheme.dimensions.m),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surfaceVariant,
                        contentColor = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                ) {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(text = "Add weight", style = MaterialTheme.typography.titleSmall)
                        IconButton(onClick = openAddWeightDialog) {
                            Icon(imageVector = Icons.Default.Add, contentDescription = "Add weight")
                        }
                    }
                }
            }
        }
        items(viewState.weightList) {
            var isWeightItemMenuShown by remember { mutableStateOf(false) }

            Card(
                modifier = Modifier
                    .width(MaterialTheme.dimensions.cardMedium)
                    .clickable {
                        if (viewState.isEditMode) {
                            isWeightItemMenuShown = true
                        }
                    }
            ) {
                DropdownMenu(
                    modifier = Modifier
                        .background(MaterialTheme.colorScheme.errorContainer),
                    expanded = isWeightItemMenuShown,
                    onDismissRequest = { isWeightItemMenuShown = false }
                ) {
                    DropdownMenuItem(
                        text = {
                            Text(
                                text = "Delete",
                                color = MaterialTheme.colorScheme.onErrorContainer
                            )
                        },
                        onClick = {
                            deleteWeight(it)
                            isWeightItemMenuShown = false
                        }
                    )
                }

                Column(
                    modifier = Modifier
                        .padding(MaterialTheme.dimensions.m)
                ) {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center,
                        text = "${it.date?.dayOfMonth} ${it.date?.month} ${it.date?.year}",
                        color = MaterialTheme.colorScheme.onPrimaryContainer,
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center,
                        color = MaterialTheme.colorScheme.onPrimaryContainer,
                        text = it.weight.toString() + "kg",
                        style = MaterialTheme.typography.titleSmall
                    )
                }
            }
        }
    }
}
