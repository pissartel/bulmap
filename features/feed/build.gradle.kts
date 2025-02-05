@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    alias(libs.plugins.pissartel.android.feature.compose)
}

android {
    namespace = "com.pissartel.repolist"
}
dependencies{
    implementation(libs.image.coil.compose)
}