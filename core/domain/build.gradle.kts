@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.pissartel.android.library)
    alias(libs.plugins.pissartel.android.hilt)
}

android {
    namespace = "com.pissartel.domain"
}

dependencies {
    api(projects.model.entity)
    implementation(libs.androidx.corektx)
    implementation(libs.kotlinx.coroutines.android)
}