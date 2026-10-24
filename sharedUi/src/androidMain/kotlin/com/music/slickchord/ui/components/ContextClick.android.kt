package com.music.slickchord.ui.components

import androidx.compose.ui.Modifier

actual fun Modifier.contextClick(onClick: (() -> Unit)?): Modifier = this
