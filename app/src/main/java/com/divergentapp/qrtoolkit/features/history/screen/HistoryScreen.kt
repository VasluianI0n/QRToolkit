package com.divergentapp.qrtoolkit.features.history.screen

import android.widget.Toast
import androidx.activity.compose.LocalActivity
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.divergentapp.qrtoolkit.core.ads.InterstitialAdManager
import com.divergentapp.qrtoolkit.core.common.FeedbackManager
import com.divergentapp.qrtoolkit.core.qr.ParsedQR
import com.divergentapp.qrtoolkit.core.ui.copyQr
import com.divergentapp.qrtoolkit.core.ui.openQrContent
import com.divergentapp.qrtoolkit.core.ui.shareQr
import com.divergentapp.qrtoolkit.features.history.components.EmptyHistory
import com.divergentapp.qrtoolkit.features.history.components.HistoryList
import com.divergentapp.qrtoolkit.features.history.components.HistorySearchBar
import com.divergentapp.qrtoolkit.features.history.components.HistoryTopBar
import com.divergentapp.qrtoolkit.features.history.effect.HistoryEffect
import com.divergentapp.qrtoolkit.features.history.intent.HistoryIntent
import com.divergentapp.qrtoolkit.features.history.viewmodel.HistoryViewModel
import com.divergentapp.qrtoolkit.features.scanner.ui.QrResultBottomSheet
import org.koin.androidx.compose.koinViewModel
import org.koin.compose.koinInject

@Composable
fun HistoryScreen(
    viewModel: HistoryViewModel = koinViewModel()
) {
    val adManager: InterstitialAdManager = koinInject()
    val activity = LocalActivity.current
    val context = LocalContext.current

    val state by viewModel.state.collectAsStateWithLifecycle()
    val feedbackManager: FeedbackManager = koinInject()
    val settings by viewModel.settings.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.effect.collect { effect ->
            when (effect) {
                is HistoryEffect.Open ->
                    context.openQrContent(effect.content)

                is HistoryEffect.Copy ->
                    context.copyQr(effect.rawValue)

                is HistoryEffect.Share ->
                    context.shareQr(effect.rawValue)

                is HistoryEffect.ShowMessage -> {
                    Toast.makeText(
                        context,
                        effect.message,
                        Toast.LENGTH_SHORT
                    ).show()
                }

                HistoryEffect.ShowInterstitial -> {
                    if (adManager.recordAction()) {
                        activity?.let {
                            adManager.showIfAvailable(it)
                        }
                    }
                }
            }
        }
    }

    Scaffold(
        topBar = {
            HistoryTopBar(
                showingFavorites = state.showingFavoritesOnly,
                onFavoriteFilterClick = {
                    viewModel.dispatch(
                        HistoryIntent.ToggleFavoritesFilter
                    )
                },
                onDeleteAllClick = {
                    feedbackManager.play(settings)
                    viewModel.dispatch(
                        HistoryIntent.DeleteAll
                    )
                }
            )
        }

    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = padding.calculateTopPadding())
        ) {

            HistorySearchBar(
                value = state.searchQuery,
                onValueChange = {
                    viewModel.dispatch(
                        HistoryIntent.Search(it)
                    )
                }
            )

            if (state.sections.isEmpty()) {
                EmptyHistory(
                    modifier = Modifier.weight(1f)
                )
            } else {
                HistoryList(
                    modifier = Modifier.weight(1f),
                    sections = state.sections,
                    onItemClick = {
                        viewModel.dispatch(
                            HistoryIntent.Open(it)
                        )
                    },
                    onDelete = {
                        feedbackManager.play(settings)
                        viewModel.dispatch(
                            HistoryIntent.Delete(it.id)
                        )
                    },
                    onFavoriteClick = {
                        viewModel.dispatch(
                            HistoryIntent.ToggleFavorite(it)
                        )
                    }
                )
            }
        }
    }

    state.selectedItem?.let { item ->
        QrResultBottomSheet(
            parsedQr = ParsedQR(
                content = item.content,
                format = item.format,
                rawValue = item.rawValue
            ),
            onDismiss = {
                viewModel.dispatch(
                    HistoryIntent.DismissDetails
                )
            },
            onOpen = {
                viewModel.dispatch(
                    HistoryIntent.OpenContent(item)
                )
            },
            onCopy = {
                viewModel.dispatch(
                    HistoryIntent.Copy(item)
                )
            },
            onShare = {
                viewModel.dispatch(
                    HistoryIntent.Share(item)
                )
            },
            onFavorite = {
                viewModel.dispatch(
                    HistoryIntent.ToggleFavorite(item)
                )
            },
        )
    }

}