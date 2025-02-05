@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    alias(libs.plugins.pissartel.android.library)
}
android {
    namespace = "com.pissartel.common"
}
dependencies {
    implementation(libs.androidx.lifecycle.viewmodel.android)
    implementation(project(":model:entity"))
}