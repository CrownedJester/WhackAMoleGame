package com.crownedjester.soft.whackamolegame.presentation.game_screen.components

import androidx.compose.animation.*
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.crownedjester.soft.whackamolegame.R

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun MoleItem(moleIndex: Int, isAppeared: Boolean, onClick: (Int) -> Unit) {

    Box(modifier = Modifier.size(72.dp)) {

        AnimatedContent(targetState = isAppeared,
            transitionSpec = {
                fadeIn(
                    animationSpec = tween(durationMillis = 500, easing = FastOutLinearInEasing)
                ) with fadeOut(animationSpec = tween(durationMillis = 500))
            }
        ) {

            Image(
                modifier = Modifier
                    .fillMaxSize()
                    .clickable {
                        if (isAppeared) {
                            onClick(moleIndex)
                        }
                    },
                painter = painterResource(
                    id = if (!isAppeared) R.drawable.mole_frame_1
                    else R.drawable.mole_frame_5
                ),
                contentDescription = "mole frame"
            )

        }

    }

}