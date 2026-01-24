plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.serialization) apply false
    alias(libs.plugins.compose.compiler) apply false
    alias(libs.plugins.ksp) apply false
    alias(libs.plugins.hilt) apply false
    alias(libs.plugins.detekt)
    alias(libs.plugins.google.services) apply false
    alias(libs.plugins.fb.crashlytics) apply false
    alias(libs.plugins.firebase.distribution) apply false
}

detekt {
    buildUponDefaultConfig = true
    allRules = false
    config.setFrom("$rootDir/config/detekt/detekt.yml")
    baseline = file("$rootDir/config/detekt/baseline.xml")
    parallel = true
    autoCorrect = true
    source.setFrom("$rootDir/app/src/main/java")
}

dependencies {
    detektPlugins(libs.detekt.compose)
}
