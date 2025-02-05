@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    alias(libs.plugins.pissartel.android.library)
    alias(libs.plugins.pissartel.android.hilt)
    alias(libs.plugins.pissartel.android.retrofit)
}
android {
    namespace = "com.pissartel.di"
}
dependencies {
    api(libs.log.timber)
    api(libs.bundles.network)
}
