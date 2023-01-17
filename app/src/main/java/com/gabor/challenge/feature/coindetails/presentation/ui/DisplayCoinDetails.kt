package com.gabor.challenge.feature.coindetails.presentation.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.gabor.challenge.feature.coindetails.domain.CoinDetails


@Composable
fun DisplayCoinDetails(details: CoinDetails) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {


        Image(
            modifier = Modifier
                .size(128.dp),
            painter = rememberAsyncImagePainter(details.image),
            contentDescription = null,
        )

        Box(
            modifier = Modifier
                .height(8.dp)
        )

        Text(
            text = details.name,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .padding(top = 16.dp, start = 8.dp, end = 8.dp, bottom = 8.dp)
        )

        Text(
            text = details.symbol,
            color = Color.Gray,
            fontSize = 16.sp,
            modifier = Modifier
                .padding(8.dp)
        )

        Text(
            text = details.homepage ?: "",
            color = Color.Gray,
            fontSize = 16.sp,
            modifier = Modifier
                .padding(8.dp)
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(32.dp)
        )

        Text(
            text = details.description ?: "Description not available", // TODO
            fontSize = 16.sp,
            modifier = Modifier
                .padding(8.dp)
        )
    }
}
