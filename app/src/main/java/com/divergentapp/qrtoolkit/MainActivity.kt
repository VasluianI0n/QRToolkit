package com.divergentapp.qrtoolkit

import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ProcessLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.divergentapp.qrtoolkit.core.ads.AppOpenAdManager
import com.divergentapp.qrtoolkit.core.ads.BannerAd
import com.divergentapp.qrtoolkit.core.ads.ConsentManager
import com.divergentapp.qrtoolkit.core.ui.components.FloatingBottomBar
import com.divergentapp.qrtoolkit.core.ui.theme.AppThemeViewModel
import com.divergentapp.qrtoolkit.core.ui.theme.QRToolkitTheme
import com.divergentapp.qrtoolkit.features.generator.screen.GenerateScreen
import com.divergentapp.qrtoolkit.features.history.screen.HistoryScreen
import com.divergentapp.qrtoolkit.features.scanner.screen.ScannerScreen
import com.divergentapp.qrtoolkit.features.settings.screen.SettingsScreen
import com.divergentapp.qrtoolkit.features.update.UpdateApp
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.androidx.compose.koinViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        enableEdgeToEdge()

        setContent {
            val viewModel: AppThemeViewModel = koinViewModel()
            val settings by viewModel.settings.collectAsStateWithLifecycle()

            val scope = rememberCoroutineScope()
            val pagerState = rememberPagerState(
                initialPage = 0,
                pageCount = { 4 }
            )
            QRToolkitTheme(
                settings = settings
            ) {
                Column(
                    Modifier.fillMaxSize()
                ) {
                    Box(
                        Modifier
                            .weight(1f)
                            .fillMaxWidth()
                    ) {
                        HorizontalPager(
                            state = pagerState,
                            modifier = Modifier.fillMaxSize()
                        ) { page ->
                            when (page) {
                                0 -> {
                                    ScannerScreen()
                                }

                                1 -> {
                                    GenerateScreen()
                                }

                                2 -> {
                                    HistoryScreen()
                                }

                                3 -> {
                                    SettingsScreen()
                                }
                            }
                        }

                        FloatingBottomBar(
                            Modifier.align(Alignment.BottomCenter),
                            selectedIndex = pagerState.currentPage,
                            onItemSelected = { page ->

                                scope.launch {
                                    pagerState.animateScrollToPage(page)
                                }
                            }
                        )

                        UpdateApp()
                    }

                    BannerAd(Modifier.fillMaxWidth())
                }

            }
        }

    }

    companion object {
        private val _updated = MutableStateFlow<Boolean>(false)
        val updated: StateFlow<Boolean> = _updated

        fun reverseUpdated() {
            _updated.update { !it }
        }
    }
}