import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties

plugins {
    id(Plugin.Android_Application)
    id(Plugin.JETBRAINS_KOTLIN_ANDROID)
    id(Plugin.KOTLINX_SERIALIZATION)
    id(Plugin.DAGGER_HILT)
    kotlin(Plugin.KAPT)
}

android {
    namespace = Project.NAME_SPACE
    compileSdk = Project.COMPILE_SDK


    defaultConfig {
        applicationId = Project.NAME_SPACE
        minSdk = Project.MIN_SDK
        targetSdk = Project.TARGET_SDK
        versionCode = Project.VERSION_CODE
        versionName = Project.VERSION_NAME

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        manifestPlaceholders["admob_key"] = getLocalProperties("admob_key")
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro"
            )
            manifestPlaceholders["admob_key"] = getLocalProperties("admob_key")
        }

        getByName("debug") {
            isMinifyEnabled = false
            manifestPlaceholders["admob_key"] = getLocalProperties("admob_key")
        }
    }
    compileOptions {
        sourceCompatibility(JavaVersion.VERSION_17)
        targetCompatibility(JavaVersion.VERSION_17)
    }
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_17.toString()
    }
    dataBinding {
        enable = true
    }
    buildFeatures {
        buildConfig = true
    }
}

dependencies {
    implementation(project(":domain"))
    implementation(project(":data"))

    implementation(AndroidX.CORE)
    implementation(AndroidX.AppCompat.APP_COMPAT)
    implementation(AndroidX.ConstraintLayout.CONSTRAINT_LAYOUT)

    implementation(Google.METERIAL)

    testImplementation(Junit.JUNIT)
    androidTestImplementation(AndroidX.Test.Ext.JUNIT)
    androidTestImplementation(AndroidX.Test.Espresso.ESPRESSO_CORE)

    implementation(Google.Hilt.HILT_ANDROID)
    kapt(Google.Hilt.HILT_COMPILER)

    implementation(AndroidX.Lifecycle.LIFECYCLE_RUNTIME_KTX)
    implementation(AndroidX.Lifecycle.PROCESS)
    implementation(AndroidX.Lifecycle.RUNTIME)
    implementation(AndroidX.Lifecycle.VIEWMODEL)
    implementation(AndroidX.Lifecycle.VIEWMODEL_SAVEDSTATE)

    implementation(AndroidX.Activity.ACTIVITY_KTX)

    implementation(Google.Firebase.ADS)
}

fun getLocalProperties(propertyKey: String): String {
    return gradleLocalProperties(rootDir).getProperty(propertyKey)
}