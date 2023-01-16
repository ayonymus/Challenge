package com.gabor.challenge

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import com.gabor.challenge.feature.market.presentation.MarketIntent
import com.gabor.challenge.feature.market.presentation.MarketViewModel
import com.gabor.challenge.feature.market.presentation.ui.MarketScreen
import com.gabor.challenge.ui.theme.ChallengeTheme
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : ComponentActivity() {

    private val viewModel: MarketViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ChallengeTheme {
                // A surface container using the 'background' color from the theme
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    val state = viewModel.marketFlow.collectAsState()
                    MarketScreen(state)
                    viewModel.handleIntent(MarketIntent.Fetch)
                }
            }
        }
    }
}
