import java.util.Properties

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("org.jetbrains.kotlin.kapt")
    id("com.google.gms.google-services")
}

// ✅ Load API key from local.properties safely
val localProps = Properties()
localProps.load(rootProject.file("local.properties").inputStream())
val MAPS_API_KEY: String = localProps.getProperty("MAPS_API_KEY") ?: ""

android {
    namespace = "com.example.leedstrinity.smarttravelappclean"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.leedstrinity.smarttravelappclean"
        minSdk = 23
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        // Expose key to Kotlin
        buildConfigField("String", "MAPS_API_KEY", "\"$MAPS_API_KEY\"")

        // Expose key to Manifest
        resValue("string", "google_maps_key", MAPS_API_KEY)
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
        jvmTarget = "17"
    }

    buildFeatures {
        compose = true
        buildConfig = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.8"
    }

    sourceSets {
        getByName("main") {
            assets.srcDirs("src/main/assets")
        }
    }
}



dependencies {

    // ---------------------------------------------------------
    // ANDROID CORE + COMPOSE
    // ---------------------------------------------------------
    implementation("androidx.core:core-ktx:1.13.1")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.7.0")
    implementation("androidx.activity:activity-compose:1.9.0")

    implementation(platform("androidx.compose:compose-bom:2024.04.01"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3")
    implementation("androidx.compose.material:material-icons-extended")
    implementation("androidx.navigation:navigation-compose:2.7.7")

    // ---------------------------------------------------------
    // GOOGLE MAPS + COMPOSE MAPS
    // ---------------------------------------------------------
    implementation("com.google.maps.android:maps-compose:4.3.0")
    implementation("com.google.android.gms:play-services-maps:18.2.0")

    // ---------------------------------------------------------
    // NETWORKING (Retrofit)
    // ---------------------------------------------------------
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")

    // ---------------------------------------------------------
    // FIREBASE AUTH
    // ---------------------------------------------------------
    implementation("com.google.firebase:firebase-auth-ktx:22.3.1")

    // ---------------------------------------------------------
    // ROOM DATABASE
    // ---------------------------------------------------------
    implementation("androidx.room:room-runtime:2.6.1")
    implementation("androidx.room:room-ktx:2.6.1")
    kapt("androidx.room:room-compiler:2.6.1")

    // ---------------------------------------------------------
    // TENSORFLOW LITE (CLEAN + CORRECT)
    // ---------------------------------------------------------
    implementation("org.tensorflow:tensorflow-lite:2.12.0")
    implementation("org.tensorflow:tensorflow-lite-support:0.4.3")


    // ---------------------------------------------------------
    // TESTING
    // ---------------------------------------------------------
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation(platform("androidx.compose:compose-bom:2024.04.01"))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")

    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")
}











