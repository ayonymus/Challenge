package com.gabor.challenge.feature.coindetails.presentation.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.valentinilk.shimmer.ShimmerBounds
import com.valentinilk.shimmer.rememberShimmer
import com.valentinilk.shimmer.shimmer


@Composable
fun CoinDetailsLoading() {
    val shimmerInstance = rememberShimmer(shimmerBounds = ShimmerBounds.Window)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .shimmer(shimmerInstance),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {


        Box(
            modifier = Modifier
                .size(128.dp)
                .background(Color.LightGray)
        )

        Box(
            modifier = Modifier
                .height(48.dp)
                .fillMaxWidth()
                .padding(top = 16.dp, start = 8.dp, end = 8.dp, bottom = 8.dp)
                .background(Color.LightGray)
        )

        Box(
            modifier = Modifier
                .height(40.dp)
                .width(128.dp)
                .padding(8.dp)
                .background(Color.LightGray)
        )

        Box(
            modifier = Modifier
                .height(40.dp)
                .width(256.dp)
                .padding(8.dp)
                .background(Color.LightGray)
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(32.dp)
        )

        for (i in 1..5) {
            Box(
                modifier = Modifier
                    .height(32.dp)
                    .fillMaxWidth()
                    .padding(8.dp)
                    .background(Color.LightGray)
            )
        }
    }
}