package com.plcoding.orangetask.feature_movie.presentation.movies.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.plcoding.orangetask.feature_movie.presentation.movies.MoviesViewModel

@Composable
fun BasicTextField(
    modifier: Modifier = Modifier,
    viewModel: MoviesViewModel = hiltViewModel()) {
    val searchText = viewModel.searchText.collectAsState()
    val focusManager = LocalFocusManager.current

    TextField(
        value = searchText.value,
        onValueChange = viewModel::setSearchText,
        placeholder = { Text(text = "Search", color = Color.LightGray) },
        textStyle = TextStyle(color = Color.White),
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        colors = TextFieldDefaults.textFieldColors(
            backgroundColor = MaterialTheme.colorScheme.secondary,
            cursorColor = Color.White,
            disabledLabelColor = Color(0xffd8e6ff),
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent
        ),
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
        keyboardActions = KeyboardActions(
            onDone = { focusManager.clearFocus() }),
        trailingIcon = {
            if (searchText.value.isNotBlank()) {
                IconButton(onClick = {
                    viewModel.setSearchText("")
                    focusManager.clearFocus()

                }) {
                    Icon(
                        imageVector = Icons.Outlined.Close,
                        tint = Color.White,
                        contentDescription = null
                    )
                }
            } else {
                Icon(
                    imageVector = Icons.Outlined.Search,
                    tint = Color.White,
                    contentDescription = null
                )
            }
        }
    )
}