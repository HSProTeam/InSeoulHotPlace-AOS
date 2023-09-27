plugins {
    id(Plugin.KOTLIN)
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

dependencies {
    implementation(Kotlin.KOTLIN_STDLIB)
    testImplementation(Junit.JUNIT)
    testImplementation(Google.TRUTH)
}