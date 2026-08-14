package com.divergentapp.qrtoolkit.core.ads

import android.app.Activity
import android.content.Context
import android.util.Log
import com.google.android.ump.ConsentInformation
import com.google.android.ump.ConsentRequestParameters
import com.google.android.ump.UserMessagingPlatform

class ConsentManager(
    context: Context
) {

    companion object {
        private const val TAG = "ConsentManager"
    }

    private val consentInformation: ConsentInformation =
        UserMessagingPlatform.getConsentInformation(
            context.applicationContext
        )

    /**
     * Requests the latest consent information and shows
     * the consent form if Google determines that it is required.
     */
    fun requestConsent(
        activity: Activity,
        onComplete: (canRequestAds: Boolean) -> Unit
    ) {

        val params =
            ConsentRequestParameters.Builder()
                .build()

        consentInformation.requestConsentInfoUpdate(
            activity,
            params,
            {

                UserMessagingPlatform.loadAndShowConsentFormIfRequired(
                    activity
                ) { formError ->

                    if (formError != null) {
                        Log.e(
                            TAG,
                            "Consent form error: ${formError.message}"
                        )
                    }

                    onComplete(
                        consentInformation.canRequestAds()
                    )
                }
            },
            { requestError ->

                Log.e(
                    TAG,
                    "Consent info update failed: ${requestError.message}"
                )

                // Google recommends checking the previous
                // consent state if the update fails.
                onComplete(
                    consentInformation.canRequestAds()
                )
            }
        )
    }

    fun canRequestAds(): Boolean {
        return consentInformation.canRequestAds()
    }
}