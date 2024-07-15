package com.valoy.compass.presentation.emoji

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ResultScreen(viewModel: EmojiViewModel) {
    val uiState by viewModel.uiState.collectAsState()
    val onListClick: (IResultList) -> Unit = remember(viewModel) {
        { item ->
            viewModel.onHeaderClick(item)
        }
    }
    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        List(
            resultList = uiState.result,
            onClick = onListClick,
            paddingValues = innerPadding
        )
    }
}

@Composable
private fun List(
    resultList: ResultList,
    onClick: (IResultList) -> Unit,
    paddingValues: PaddingValues
) {
    val scrollState = rememberLazyListState()

    LazyColumn(
        state = scrollState, contentPadding = paddingValues,
        modifier = Modifier
            .fillMaxSize()
    ) {


        items(resultList.items.size) { index ->

            if (resultList.items[index] is HeaderItem) {
                HeaderItem(
                    resultList.items[index] as HeaderItem,
                    onClick,
                    modifier = Modifier.padding(16.dp)
                )
            } else if (resultList.items[index].isExpanded) {

                ContentItem(
                    contentItem = resultList.items[index] as ContentItem,
                    modifier = Modifier.padding(16.dp)
                )
            }
        }
    }
}

@Composable
private fun HeaderItem(
    headerItem: HeaderItem,
    onClick: (HeaderItem) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(modifier.clickable { onClick(headerItem) }) {
        Text(text = headerItem.headerName)
        Icon(
            imageVector = if (headerItem.isExpanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
            contentDescription = "Arrow"
        )
    }
}

@Composable
private fun ContentItem(contentItem: ContentItem, modifier: Modifier = Modifier) {
    Column(modifier) {
        Text(text = contentItem.content)
    }
}