package com.gbl.cryptoinfo.ui.screens.coinInfo

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.gbl.cryptoinfo.R
import com.gbl.cryptoinfo.entity.CoinInfo
import com.gbl.cryptoinfo.entity.Description
import com.gbl.cryptoinfo.entity.Image
import com.gbl.cryptoinfo.ui.views.ErrorMessageView
import com.gbl.cryptoinfo.ui.views.LoadMessageView

@Composable
fun CoinInfoScreen(
    coinInfoUiState: CoinInfoUiState,
    reloadInfo: () -> Unit,
    onNavigateToPreviousScreen: () -> Unit
) {
    Scaffold(
        topBar = {
            TopBar(
                title = coinInfoUiState.coinInfo?.name ?: "",
                onNavigateToPreviousScreen = onNavigateToPreviousScreen
            )
        }
    ) { innerPaddings ->
        val modifier = Modifier
            .padding(innerPaddings)
            .fillMaxSize()

        when (coinInfoUiState.infoLoadState) {
            InfoLoadState.Loading ->
                LoadMessageView(modifier = modifier)

            InfoLoadState.Error ->
                ErrorMessageView(modifier = modifier, reloadData = reloadInfo)

            InfoLoadState.Success ->
                coinInfoUiState.coinInfo?.let { coinInfo ->
                    CoinInfoContent(
                        modifier = modifier
                            .verticalScroll(rememberScrollState())
                            .padding(horizontal = 16.dp)
                            .padding(bottom = 34.dp),
                        coinInfo = coinInfo
                    )
                }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TopBar(
    title: String,
    onNavigateToPreviousScreen: () -> Unit
) {
    TopAppBar(
        navigationIcon = {
            IconButton(onClick = onNavigateToPreviousScreen) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = stringResource(id = R.string.back_navigation)
                )
            }
        },
        title = { Text(text = title) }
    )
}

@Composable
private fun CoinInfoContent(
    modifier: Modifier,
    coinInfo: CoinInfo
) {
    Column(
        modifier = modifier
    ) {
        AsyncImage(
            model = coinInfo.image.large,
            contentDescription = null,
            modifier = Modifier
                .size(90.dp)
                .align(Alignment.CenterHorizontally)
        )

        InfoBlockWithTitle(
            title = stringResource(id = R.string.description_title),
            content = with(coinInfo.description) { ru.ifEmpty { en } }
        )

        InfoBlockWithTitle(
            title = stringResource(id = R.string.categoties_title),
            content = coinInfo.categories.joinToString(", ")
        )
    }
}

@Composable
private fun InfoBlockWithTitle(
    title: String,
    content: String
) {
    Column {
        Text(
            text = title,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(top = 16.dp)
        )
        Text(
            text = content,
            fontSize = 16.sp,
            modifier = Modifier.padding(top = 8.dp)
        )
    }
}

@Preview(showSystemUi = true)
@Composable
private fun PreviewCoinInfoScreen() {
    val coinInfo = object : CoinInfo {
        override val id: String = "bitcoin"
        override val name: String = "Bitcoin"
        override val categories: List<String> = listOf(
            "Cryptocurrency",
            "Layer 1 (L1)",
            "FTX Holdings",
            "Proof of Work (PoW)",
            "GMCI 30 Index"
        )
        override val description: Description = object : Description {
            override val en: String =
                "Bitcoin is the first successful internet money based on peer-to-peer technology; whereby no central bank or authority is involved in the transaction and production of the Bitcoin currency. It was created by an anonymous individual/group under the name, Satoshi Nakamoto. The source code is available publicly as an open source project, anybody can look at it and be part of the developmental process.\r\n\r\nBitcoin is changing the way we see money as we speak. The idea was to produce a means of exchange, independent of any central authority, that could be transferred electronically in a secure, verifiable and immutable way. It is a decentralized peer-to-peer internet currency making mobile payment easy, very low transaction fees, protects your identity, and it works anywhere all the time with no central authority and banks."
            override val ru: String = ""
        }
        override val image: Image = object : Image {
            override val large: String =
                "https://coin-images.coingecko.com/coins/images/1/large/bitcoin.png?1696501400"
        }
    }
    CoinInfoScreen(
        coinInfoUiState = CoinInfoUiState(InfoLoadState.Success, coinInfo),
        reloadInfo = {},
        onNavigateToPreviousScreen = {})
}