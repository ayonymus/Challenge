package com.gabor.challenge.feature.market.presentation.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.valentinilk.shimmer.ShimmerBounds
import com.valentinilk.shimmer.rememberShimmer
import com.valentinilk.shimmer.shimmer

@Composable
fun MarketDataPlaceholderItem() {
    ElevatedCard(
        shape = MaterialTheme.shapes.small,
        modifier = Modifier.padding(4.dp),
    ) {
        val shimmerInstance = rememberShimmer(shimmerBounds = ShimmerBounds.Window)

        ConstraintLayout(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .shimmer(shimmerInstance)
        ) {

            val (icon, tokenName, symbol) = createRefs()

            Box(
                modifier = Modifier.size(64.dp)
                    .constrainAs(icon) {
                        start.linkTo(parent.start)
                        top.linkTo(parent.top)
                    }
                    .background(Color.LightGray)
            )

            Box(
                modifier = Modifier
                    .height(32.dp)
                    .fillMaxWidth()
                    .padding(8.dp)
                    .constrainAs(tokenName) {
                        start.linkTo(icon.end)
                        top.linkTo(icon.top)
                    }
                    .background(Color.LightGray)
            )

            Box(
                modifier = Modifier
                    .height(32.dp)
                    .fillMaxWidth()
                    .padding(8.dp)
                    .constrainAs(symbol) {
                        start.linkTo(icon.end)
                        bottom.linkTo(icon.bottom)
                    }
                    .background(Color.LightGray)
            )
        }
    }
}
