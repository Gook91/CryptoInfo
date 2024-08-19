package com.gbl.cryptoinfo.ui.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gbl.cryptoinfo.R
import com.gbl.cryptoinfo.ui.theme.CryptoInfoTheme


@Composable
fun LoadMessageView(
    modifier: Modifier
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
    ) { CircularProgressIndicator() }
}

@Preview
@Composable
private fun PreviewLoadMessageView() {
    CryptoInfoTheme {
        LoadMessageView(modifier = Modifier.fillMaxWidth())
    }
}

@Composable
fun ErrorMessageView(
    modifier: Modifier,
    reloadData: () -> Unit
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(painter = painterResource(id = R.drawable.logo), contentDescription = null)
        Text(
            modifier = Modifier.padding(top = 13.dp, bottom = 30.dp),
            text = stringResource(id = R.string.first_load_error_message)
        )
        Button(
            onClick = reloadData,
            shape = RoundedCornerShape(4.dp)
        ) {
            Text(text = stringResource(id = R.string.try_reload_button))
        }
    }
}

@Preview
@Composable
private fun PreviewMessageView() {
    CryptoInfoTheme {
        ErrorMessageView(
            modifier = Modifier.fillMaxSize(),
            reloadData = {}
        )
    }
}