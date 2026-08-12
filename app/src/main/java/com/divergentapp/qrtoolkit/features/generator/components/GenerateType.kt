package com.divergentapp.qrtoolkit.features.generator.components

enum class GenerateType(
    val title: String
) {
    IMAGE("Image"),
    VIDEO("Video"),
    AUDIO("Audio"),
    PDF("PDF"),
    URL("Website"),
    TEXT("Text"),
    WIFI("Wi-Fi"),
    EMAIL("Email"),
    PHONE("Phone"),
    SMS("SMS"),
    CONTACT("Contact"),
    LOCATION("Location"),
    CALENDAR("Calendar"),
}