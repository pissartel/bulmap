@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.pissartel.android.library)
    alias(libs.plugins.pissartel.android.hilt)
}
android {
    namespace = "com.pissartel.data"

}

dependencies {
    implementation(projects.core.domain)
    implementation(projects.core.di)
    api(projects.model.apiresponse)
}