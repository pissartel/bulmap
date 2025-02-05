@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    alias(libs.plugins.pissartel.android.library)
    alias(libs.plugins.pissartel.android.library.compose)
}

android {
    namespace = "com.pissartel.ui"
}

dependencies {
    implementation(projects.core.designsystem)


    implementation(project(":common"))

    api(libs.androidx.corektx)
    api(libs.androidx.compose.foundation)
    api(libs.androidx.compose.material.iconsExtended)
    api(libs.androidx.compose.material3)
    api(libs.androidx.compose.ui.tooling.preview)
    api(libs.androidx.compose.ui)
    api(libs.androidx.compose.ui.graphics)
    implementation(libs.map.compose)
    implementation(libs.maps.location)
    implementation(libs.image.coil.compose)
    debugApi(libs.androidx.compose.ui.tooling)

    testImplementation(libs.test.junit)
    androidTestImplementation(libs.test.extjunit)
    androidTestImplementation(libs.test.espresso)
}