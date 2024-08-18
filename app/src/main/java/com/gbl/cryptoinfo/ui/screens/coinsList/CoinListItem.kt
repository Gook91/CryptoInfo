package com.gbl.cryptoinfo.ui.screens.coinsList

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.gbl.cryptoinfo.R
import com.gbl.cryptoinfo.entity.CoinWithMarketData
import com.gbl.cryptoinfo.entity.Currency

@Composable
fun CoinListItem(
    coinWithMarketData: CoinWithMarketData,
    currency: Currency,
    onItemClick: () -> Unit
) {
    Box(
        modifier = Modifier.clickable { onItemClick() }
    ) {
        Row(
            modifier = Modifier.padding(vertical = 8.dp)
        ) {
            AsyncImage(
                model = coinWithMarketData.image,
                contentDescription = null,
                modifier = Modifier.size(40.dp),
                contentScale = ContentScale.FillWidth
            )
            NameBlock(
                coinWithMarketData = coinWithMarketData,
                modifier = Modifier
                    .padding(horizontal = 8.dp)
                    .fillMaxWidth()
                    .weight(1f)
            )
            PriceBlock(
                coinWithMarketData = coinWithMarketData,
                currency = currency
            )
        }
    }
}

@Composable
private fun NameBlock(
    coinWithMarketData: CoinWithMarketData,
    modifier: Modifier
) {
    Column(modifier = modifier) {
        Text(
            text = coinWithMarketData.name,
            fontSize = 16.sp
        )
        Text(
            text = coinWithMarketData.symbol,
            fontSize = 14.sp,
            color = Color(0xFF9B9B9B)
        )
    }
}

@Composable
private fun PriceBlock(
    coinWithMarketData: CoinWithMarketData,
    currency: Currency
) {
    Column(horizontalAlignment = Alignment.End) {
        Text(
            text = stringResource(
                id = R.string.price_template,
                currency.symbol,
                coinWithMarketData.currentPrice
            ),
            fontSize = 16.sp
        )
        val changePriceColor = when {
            coinWithMarketData.priceChangePercentage24h > 0 -> Color(0xFF2A9D8F)
            coinWithMarketData.priceChangePercentage24h < 0 -> Color(0xFFEB5757)
            else -> Color.Unspecified
        }

        Text(
            text = stringResource(
                id = R.string.price_change_template,
                coinWithMarketData.priceChangePercentage24h
            ),
            fontSize = 14.sp,
            color = changePriceColor
        )
    }
}

@Preview
@Composable
private fun PreviewCoinListItem() {
    val someCoin: CoinWithMarketData = object : CoinWithMarketData {
        override val id: String = "id"
        override val symbol: String = "SMB"
        override val name: String = "Name"
        override val image: String = ""
        override val currentPrice: Float = 0f
        override val priceChangePercentage24h: Float = 10.0f
    }
    CoinListItem(
        currency = Currency.USD,
        coinWithMarketData = someCoin,
        onItemClick = {}
    )
}