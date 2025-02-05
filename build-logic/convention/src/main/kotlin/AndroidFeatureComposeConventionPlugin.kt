
import com.android.build.gradle.LibraryExtension
import com.pissartel.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.kotlin

class AndroidFeatureComposeConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            pluginManager.apply {
                apply("pissartel.android.library")
                apply("pissartel.android.library.compose")
                apply("pissartel.android.hilt")
            }
            extensions.configure<LibraryExtension> {
                defaultConfig {
                    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
                }
            }

            dependencies {
                add("implementation", project(":core:di"))
                add("implementation", project(":core:domain"))
                add("implementation", project(":common"))
                add("implementation", project(":core:designsystem"))
                add("implementation", project(":core:ui"))



                add("implementation", libs.findLibrary("androidx.compose.hilt.navigation").get())
                add("implementation", libs.findLibrary("androidx.lifecycle.runtimeCompose").get())
                add("implementation", libs.findLibrary("androidx.lifecycle.viewModelCompose").get())
                add("implementation", libs.findLibrary("log.timber").get())
                add("implementation", libs.findLibrary("kamrul3288.dateced").get())
                add("implementation", libs.findLibrary("kotlinx.coroutines.android").get())


                add("testImplementation", libs.findLibrary("test-junit").get())
                add("androidTestImplementation", libs.findLibrary("test.extjunit").get())
                add("androidTestImplementation", libs.findLibrary("test.espresso").get())

            }
        }
    }
}
