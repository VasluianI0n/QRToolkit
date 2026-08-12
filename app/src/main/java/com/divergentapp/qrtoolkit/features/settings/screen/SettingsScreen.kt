package com.divergentapp.qrtoolkit.features.settings.screen

import android.content.ActivityNotFoundException
import android.content.Intent
import android.widget.Toast
import androidx.activity.compose.LocalActivity
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.divergentapp.qrtoolkit.core.ui.icons.alternate_email
import com.divergentapp.qrtoolkit.core.ui.icons.dark_mode
import com.divergentapp.qrtoolkit.core.ui.icons.delete
import com.divergentapp.qrtoolkit.core.ui.icons.info
import com.divergentapp.qrtoolkit.core.ui.icons.link
import com.divergentapp.qrtoolkit.core.ui.icons.mobile_vibrate
import com.divergentapp.qrtoolkit.core.ui.icons.palette
import com.divergentapp.qrtoolkit.core.ui.icons.privacy_tip
import com.divergentapp.qrtoolkit.core.ui.icons.qr_code_scanner
import com.divergentapp.qrtoolkit.core.ui.icons.save
import com.divergentapp.qrtoolkit.core.ui.icons.share
import com.divergentapp.qrtoolkit.core.ui.icons.star
import com.divergentapp.qrtoolkit.core.ui.icons.volume_up
import com.divergentapp.qrtoolkit.features.settings.components.SettingGroup
import com.divergentapp.qrtoolkit.features.settings.components.SettingItem
import com.divergentapp.qrtoolkit.features.settings.components.SettingSwitch
import com.divergentapp.qrtoolkit.features.settings.effect.SettingsEffect
import com.divergentapp.qrtoolkit.features.settings.intent.SettingsIntent
import com.divergentapp.qrtoolkit.features.settings.viewmodel.SettingsViewModel
import org.koin.androidx.compose.koinViewModel
import androidx.core.net.toUri
import com.divergentapp.qrtoolkit.features.settings.components.RateUsDialog
import com.google.android.play.core.review.ReviewManagerFactory

@Composable
fun SettingsScreen(
    viewModel: SettingsViewModel = koinViewModel()
) {
    val state by viewModel.settingsState.collectAsStateWithLifecycle()
    val context = LocalContext.current
    val activity = LocalActivity.current
    val text = """
        Check out QR Toolkit!

        https://play.google.com/store/apps/details?id=com.divergentapp.qrtoolkit
    """.trimIndent()

    var showRateSheet by rememberSaveable {
        mutableStateOf(false)
    }

    LaunchedEffect(Unit) {
        viewModel.effect.collect { effect ->
            when (effect) {
                is SettingsEffect.OpenPlayStore -> {
                    val uri = "market://details?id=${context.packageName}".toUri()

                    try {
                        context.startActivity(
                            Intent(Intent.ACTION_VIEW, uri)
                        )
                    } catch (_: ActivityNotFoundException) {

                        context.startActivity(
                            Intent(
                                Intent.ACTION_VIEW,
                                "https://play.google.com/store/apps/details?id=${context.packageName}".toUri()
                            )
                        )

                    }
                }

                SettingsEffect.ShareApp -> {
                    val intent = Intent(Intent.ACTION_SEND).apply {
                        type = "text/plain"
                        putExtra(Intent.EXTRA_TEXT, text)
                    }

                    context.startActivity(
                        Intent.createChooser(
                            intent,
                            "Share QR Toolkit"
                        )
                    )
                }

                SettingsEffect.ContactSupport -> {
                    val intent = Intent(Intent.ACTION_SENDTO).apply {

                        data = "mailto:".toUri()

                        putExtra(
                            Intent.EXTRA_EMAIL,
                            arrayOf("contactsupport.divergentapp@gmail.com")
                        )

                        putExtra(
                            Intent.EXTRA_SUBJECT,
                            "QR Toolkit Support"
                        )

                    }

                    context.startActivity(intent)
                }
                SettingsEffect.OpenPrivacyPolicy -> {
                    Toast.makeText(context, "No Privacy Policy needed yet, enjoy!", Toast.LENGTH_SHORT).show()
                }

                SettingsEffect.OpenRateUs -> {
                    val manager = ReviewManagerFactory.create(context)

                    activity?.let {
                        manager.requestReviewFlow()
                            .addOnCompleteListener { request ->
                                if (request.isSuccessful) {
                                    val reviewInfo = request.result
                                    manager.launchReviewFlow(
                                        it,
                                        reviewInfo
                                    ).addOnCompleteListener {

                                        // Flow finished.
                                        // Google decides whether to actually show it.

                                    }

                                } else {
                                    // fallback
                                    viewModel.dispatch(
                                        SettingsIntent.RateApp
                                    )
                                }

                            }

                    }
                }
            }
        }
    }

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(top = 16.dp, start = 16.dp, end = 16.dp, bottom = 100.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {

        item {

            SettingGroup(title = "Appearance") {

                SettingSwitch(
                    icon = dark_mode,
                    title = "Dark theme",
                    checked = state.darkTheme,
                    enabled = !state.useSystemState,
                    onCheckedChange = {
                        viewModel.dispatch(
                            SettingsIntent.ToggleDarkTheme(it)
                        )
                    }
                )

                HorizontalDivider()

                SettingSwitch(
                    icon = palette,
                    title = "System default theme",
                    checked = state.useSystemState,
                    onCheckedChange = {
                        viewModel.dispatch(
                            SettingsIntent.ToggleSystemTheme
                        )
                    }
                )

            }

        }

        item {

            SettingGroup(title = "Scanner") {

                SettingSwitch(
                    icon = mobile_vibrate,
                    title = "Vibrate",
                    checked = state.vibrateOnScan,
                    onCheckedChange = {
                        viewModel.dispatch(SettingsIntent.ToggleVibration)
                    }
                )

                HorizontalDivider()

                SettingSwitch(
                    icon = volume_up,
                    title = "Play beep",
                    checked = state.beepOnScan,
                    onCheckedChange = {
                        viewModel.dispatch(SettingsIntent.ToggleBeep)
                    }
                )

                HorizontalDivider()

                SettingSwitch(
                    icon = link,
                    title = "Auto open links",
                    checked = state.autoOpenLinks,
                    onCheckedChange = {
                        viewModel.dispatch(SettingsIntent.ToggleAutoOpenLinks)
                    }
                )

            }

        }

        item {

            SettingGroup(title = "History") {

                SettingSwitch(
                    icon = save,
                    title = "Save generated QR codes",
                    checked = state.saveGeneratedHistory,
                    onCheckedChange = {
                        viewModel.dispatch(SettingsIntent.ToggleGeneratedHistory)
                    }
                )

                HorizontalDivider()

                SettingSwitch(
                    icon = qr_code_scanner,
                    title = "Save scanned QR codes",
                    checked = state.saveScannedHistory,
                    onCheckedChange = {
                        viewModel.dispatch(SettingsIntent.ToggleScannedHistory)
                    }
                )

                HorizontalDivider()

                SettingItem(
                    icon = delete,
                    title = "Clear history",
                    onClick = {
                        viewModel.dispatch(SettingsIntent.ClearHistory)
                    }
                )

            }

        }

        item {

            SettingGroup(title = "About") {

                SettingItem(
                    icon = star,
                    title = "Rate app",
                    onClick = {
                        showRateSheet = true
                    }
                )

                HorizontalDivider()

                SettingItem(
                    icon = share,
                    title = "Share app",
                    onClick = {
                        viewModel.dispatch(SettingsIntent.ShareApp)
                    }
                )

                HorizontalDivider()

                SettingItem(
                    icon = privacy_tip,
                    title = "Privacy Policy",
                    onClick = {
                        viewModel.dispatch(SettingsIntent.PrivacyPolicy)
                    }
                )

                HorizontalDivider()

                SettingItem(
                    icon = alternate_email,
                    title = "Contact support",
                    onClick = {
                        viewModel.dispatch(SettingsIntent.ContactSupport)
                    }
                )

                HorizontalDivider()

                ListItem(
                    leadingContent = {
                        Icon(
                            info,
                            null,
                            tint = MaterialTheme.colorScheme.tertiaryFixed
                        )
                    },
                    headlineContent = {
                        Text("Version",
                            color = MaterialTheme.colorScheme.tertiaryFixed)
                    },

                    colors = ListItemDefaults.colors().copy(
                        containerColor =  MaterialTheme.colorScheme.surfaceContainerHighest
                    ),

                    modifier = Modifier.clickable {
                      Toast.makeText(context, state.appVersion, Toast.LENGTH_SHORT).show()
                    }
                )

            }

        }

    }

    if (showRateSheet) {
        RateUsDialog (
            onDismiss = {
                showRateSheet = false
            },
            onRatingSelected = { rating ->
                if (rating >= 4){
                    viewModel.dispatch(
                        SettingsIntent.RateApp
                    )
                } else {
                    viewModel.dispatch(
                        SettingsIntent.ContactSupport
                    )
                }

                showRateSheet = false
            }
        )
    }
}