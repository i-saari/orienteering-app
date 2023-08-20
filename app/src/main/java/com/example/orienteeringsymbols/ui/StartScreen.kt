package com.example.orienteeringsymbols.ui

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.orienteeringsymbols.R
import com.example.orienteeringsymbols.data.DataSource
import com.example.orienteeringsymbols.data.Symbol

@Composable
fun StartScreen(
    symbolGroups: List<Int>,
    onGroupButtonClicked: (Int) -> Unit,
    modifier: Modifier = Modifier
){
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_small))
    ) {
        Text(
            text = "Orienteering Symbols",
            style = MaterialTheme.typography.headlineLarge
        )
        symbolGroups.forEach { item ->
            SelectSymbolGroupButton(
                labelResourceId = item,
                onClick = { onGroupButtonClicked(item) })
        }
    }
}

@Composable
fun SelectSymbolGroupButton(
    @StringRes labelResourceId: Int,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
){
    Button(
        onClick = onClick,
        modifier = modifier.widthIn(min = 200.dp)
    ) {
        Text(stringResource(id = labelResourceId))
    }
}

//@Preview
//@Composable
//fun StartPreview(){
//    StartScreen(
//        symbolGroups = DataSource.symbolGroups,
//        onGroupButtonClicked = {},
//        modifier = Modifier
//            .fillMaxSize()
//            .padding(dimensionResource(id = R.dimen.padding_medium))
//    )
//}