package com.example.gymguru.presentation.composables

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.input.KeyboardType

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GymGuruOutlinedTextField(
    modifier: Modifier = Modifier,
    keyboardType: KeyboardType = KeyboardType.Text,
    state: GymGuruTextFieldState,
    readOnly: Boolean = false,
    onValueChange: (String) -> Unit,
    hint: String = "",
    shape: Shape = OutlinedTextFieldDefaults.shape
) {
    OutlinedTextField(
        modifier = modifier,
        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = keyboardType),
        value = state.value,
        onValueChange = onValueChange,
        textStyle = MaterialTheme.typography.bodyMedium,
        label = { Text(hint) },
        isError = state.isError,
        readOnly = readOnly,
        supportingText = {
            if (state.isError) {
                Text(
                    modifier = modifier,
                    text = state.errorMessage,
                    color = MaterialTheme.colorScheme.error
                )
            }
        },
        shape = shape,
        maxLines = 1,
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = MaterialTheme.colorScheme.primary,
            unfocusedBorderColor = MaterialTheme.colorScheme.onBackground,
            errorSupportingTextColor = MaterialTheme.colorScheme.error
        )
    )
}
