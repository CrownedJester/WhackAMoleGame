package com.crownedjester.soft.whackamolegame.presentation.game_screen

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.lifecycle.viewmodel.compose.viewModel
import com.crownedjester.soft.whackamolegame.R
import com.crownedjester.soft.whackamolegame.presentation.game_screen.components.MoleItem

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun GameScreen() {

    val viewModel = viewModel<GameViewModel>()
    val currentRevealedMoleState by viewModel.currentRevealedMole.collectAsState()
    val timerState = viewModel.timerStateFlow.collectAsState()

    val count = viewModel.tappedMoleCount.value

    val hammerOffset = remember { mutableStateOf(Offset(0f, 0f)) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .pointerInput(Unit) {
                detectTapGestures {
                    hammerOffset.value = it
                }
            }
    ) {
        Image(
            modifier = Modifier
                .fillMaxSize()
                .zIndex(0f),
            contentScale = ContentScale.FillBounds,
            painter = painterResource(id = R.drawable.grass_2),
            contentDescription = "game background"
        )

        Image(
            modifier = Modifier
                .size(48.dp)
                .offset(hammerOffset.value.x.dp, hammerOffset.value.y.dp),
            painter = painterResource(id = R.drawable.mole_frame_5),
            contentDescription = "hammer"
        )

        Column(
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp, vertical = 6.dp),

            ) {
                Text(
                    text = "Score: $count",
                    style = MaterialTheme.typography.h6,
                    fontWeight = Bold
                )

                Text(
                    text = "00:${if (timerState.value < 10) "0${timerState.value}" else timerState.value}",
                    style = MaterialTheme.typography.h6,
                    fontWeight = Bold
                )
            }

            Spacer(modifier = Modifier.height(128.dp))

            LazyVerticalGrid(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.CenterHorizontally),
                cells = GridCells.Fixed(3),
                horizontalArrangement = Arrangement.SpaceAround,
                verticalArrangement = Arrangement.spacedBy(28.dp)
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
                modifier = Modifier
                    .fillMaxWidth(),
                text = "Smack Them!",
                style = MaterialTheme.typography.h4,
                fontWeight = Bold,
                color = Color(0xFFFFD600),
                textAlign = TextAlign.Center
            )

        }

    }

}