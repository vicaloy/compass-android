package com.valoy.compass.presentation.screens.search

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.valoy.compass.R
import com.valoy.compass.presentation.theme.dp_16

@Composable
fun SearchScreen(onNavigate: () -> Unit, viewModel: SearchViewModel = hiltViewModel()) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val onSearchClick: () -> Unit = remember(viewModel) {
        {
            viewModel.onSearchClick()
        }
    }
    val onSuccessfulNav: () -> Unit = remember(viewModel) {
        {
            viewModel.consumeAction()
            onNavigate()
        }
    }

    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            TitleText()
            SearchButton(!uiState.isLoading, onSearchClick)
            Navigate(uiState.shouldNav, onSuccessfulNav)
            ProgressIndicator(uiState.isLoading)
        }
    }
}

@Composable
private fun ProgressIndicator(hasShow: Boolean, modifier: Modifier = Modifier) {
    if (hasShow) {
        CircularProgressIndicator(modifier = modifier)
    }
}

@Composable
private fun TitleText(modifier: Modifier = Modifier) {
    Text(
        modifier = modifier.padding(dp_16),
        text = stringResource(id = R.string.search_title),
        style = MaterialTheme.typography.titleLarge
    )
}

@Composable
private fun Navigate(
    hasNavigate: Boolean?,
    onNavigate: () -> Unit,
    modifier: Modifier = Modifier
) {
    when (hasNavigate) {
        false -> Text(modifier = modifier, text = stringResource(id = R.string.whoops))

        true -> onNavigate()

        null -> Unit
    }
}

@Composable
private fun SearchButton(
    hasShow: Boolean,
    onSearchClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    if (hasShow) {
        Button(modifier = modifier, onClick = onSearchClick) {
            Text(text = stringResource(id = R.string.search))
        }
    }
}
