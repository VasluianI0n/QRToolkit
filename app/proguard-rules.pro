###########################################
# Keep source info for crash reports
###########################################

-keepattributes SourceFile,LineNumberTable

###########################################
# Kotlin
###########################################

-keep class kotlin.Metadata { *; }

###########################################
# Koin
###########################################

-keep class org.koin.** { *; }

###########################################
# Room
###########################################

-keep class androidx.room.** { *; }

###########################################
# Retrofit
###########################################

-keepattributes Signature
-keepattributes RuntimeVisibleAnnotations
-keepattributes RuntimeVisibleParameterAnnotations

###########################################
# Enum values
###########################################

-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}

###########################################
# Parcelable
###########################################

-keepclassmembers class * implements android.os.Parcelable {
    public static final android.os.Parcelable$Creator CREATOR;
}

# ML Kit
-keep class com.google.mlkit.** { *; }
-dontwarn com.google.mlkit.**

# Google Play Services
-keep class com.google.android.gms.** { *; }
-dontwarn com.google.android.gms.**

# Vision
-keep class com.google.android.odml.** { *; }
-dontwarn com.google.android.odml.**