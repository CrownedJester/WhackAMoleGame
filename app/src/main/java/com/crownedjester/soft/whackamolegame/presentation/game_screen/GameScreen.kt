package com.crownedjester.soft.whackamolegame.presentation.game_screen

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.lifecycle.viewmodel.compose.viewModel
import com.crownedjester.soft.whackamolegame.R
import com.crownedjester.soft.whackamolegame.presentation.game_screen.components.MoleItem
import com.crownedjester.soft.whackamolegame.ui.theme.LightOrange

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun GameScreen() {

    val viewModel = viewModel<GameViewModel>()

    val currentRevealedMoleState by viewModel.currentRevealedMole.collectAsState()
    val timerState = viewModel.timerStateFlow.collectAsState()

    val count = viewModel.tappedMoleCount.value

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Image(
            modifier = Modifier
                .fillMaxSize()
                .zIndex(0f),
            contentScale = ContentScale.FillBounds,
            painter = painterResource(id = R.drawable.grass_2),
            contentDescription = "game background"
        )

        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp, vertical = 6.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Text(
                    text = "Score: $count", style = MaterialTheme.typography.h6, fontWeight = Bold
                )

                Text(
                    text = "00:" + timerState.value.toSecondsString(),
                    style = MaterialTheme.typography.h6,
                    fontWeight = Bold
                )
            }

            Spacer(modifier = Modifier.height(128.dp))

            LazyVerticalGrid(
                modifier = Modifier
                    .padding(horizontal = 12.dp)
                    .fillMaxWidth(),
                cells = GridCells.Fixed(3),
                horizontalArrangement = Arrangement.spacedBy(24.dp),
                verticalArrangement = Arrangement.spacedBy(20.dp)
            ) {

                items(9) { i ->
                    MoleItem(i, i == currentRevealedMoleState) {
                        if (currentRevealedMoleState == it) {
                            viewModel.onMoleTap()
                        }
                    }
                }
            }

            Text(
                modifier = Modifier.fillMaxWidth(),
                text = "Beat Them!",
                style = MaterialTheme.typography.h4,
                fontWeight = Bold,
                color = LightOrange,
                textAlign = TextAlign.Center
            )

        }

    }

}

private fun Int.toSecondsString(): String =
    if (this < 10)
        "0$this"
    else
        this.toString()
