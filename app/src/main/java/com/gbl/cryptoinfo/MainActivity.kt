package com.gbl.cryptoinfo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.gbl.cryptoinfo.ui.navigation.NavScreen
import com.gbl.cryptoinfo.ui.theme.CryptoInfoTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CryptoInfoTheme {
                NavScreen()
            }
        }
    }
}