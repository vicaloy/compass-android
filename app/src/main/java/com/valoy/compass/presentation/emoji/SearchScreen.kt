package com.valoy.compass.presentation.emoji

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.valoy.compass.R
import com.valoy.compass.util.isNetworkAvailable

@Composable
fun SearchScreen(onSearch: () -> Unit, viewModel: EmojiViewModel = hiltViewModel()) {
    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current
    val onSearchClick: () -> Unit = remember(viewModel) {
        {
            viewModel.onSearchClick(isNetworkAvailable(context))
        }
    }
    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        Row(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            Button(onClick = onSearchClick) {
                Text(text = stringResource(id = R.string.search))
            }

            if (uiState.isLoading) {
                CircularProgressIndicator()
            }

            when (uiState.isSuccessful) {
                false -> {
                    Text(text = stringResource(id = R.string.whoops))
                }

                true -> {
                    onSearch()
                    viewModel.consumeAction()
                }

                null -> Unit
            }
        }
    }
}

