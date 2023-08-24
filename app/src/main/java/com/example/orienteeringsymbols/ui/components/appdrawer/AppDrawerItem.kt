package com.example.orienteeringsymbols.ui.components.appdrawer

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.orienteeringsymbols.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun <T> AppDrawerItem(
    item: AppDrawerItemInfo<T>,
    onClick: (options: T) -> Unit
) {
    Surface(
//        color = MaterialTheme.colorScheme.onPrimary,
        modifier = Modifier
            .width(200.dp)
            .padding(start = dimensionResource(id = R.dimen.padding_medium)),
        onClick = { onClick(item.drawerOption) }
    ) {
        Row(
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
        ) {
            Icon(
                painter = painterResource(id = item.drawableId),
                contentDescription = stringResource(id = item.descriptionId),
                modifier = Modifier
                    .size(dimensionResource(id = R.dimen.icon_size))
            )
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = stringResource(id = item.title),
                style = MaterialTheme.typography.bodyLarge,
                textAlign = TextAlign.Center
            )
        }
    }
}
