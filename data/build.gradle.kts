
plugins {
    id(Plugin.ANDROID_LIBERARY)
    id(Plugin.JETBRAINS_KOTLIN_ANDROID)
    id(Plugin.KOTLINX_SERIALIZATION)
    id(Plugin.DAGGER_HILT)
    kotlin(Plugin.KAPT)
}

android {
    namespace = Project.NAME_SPACE
    compileSdk = Project.COMPILE_SDK

    defaultConfig {
        minSdk = Project.MIN_SDK
        targetSdk = Project.TARGET_SDK

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_17.toString()
    }
}

dependencies {

    implementation(project(":domain"))
    implementation(AndroidX.CORE)
    implementation(AndroidX.AppCompat.APP_COMPAT)
    implementation(AndroidX.ConstraintLayout.CONSTRAINT_LAYOUT)

    implementation(Google.METERIAL)

    testImplementation(Junit.JUNIT)
    androidTestImplementation(AndroidX.Test.Ext.JUNIT)
    androidTestImplementation(AndroidX.Test.Espresso.ESPRESSO_CORE)

    implementation(Kotlin.KOTLIN_SERIALIZATION)
    implementation(KotlinX.KOTLINX_SERIALIZATION)
    implementation(KotlinX.KOTLINX_SERIALIZATION_JSON)

    implementation(SquareUp.OkHttp3.CORE)
    implementation(SquareUp.OkHttp3.LOGGING_INTERCEPTOR)

    implementation(Google.Hilt.HILT_ANDROID)
    kapt(Google.Hilt.HILT_COMPILER)
}