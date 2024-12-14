plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    id("kotlin-kapt")
}

android {
    namespace = "com.example.journalingapp"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.journalingapp"
        minSdk = 29
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        // Pass the API key as a BuildConfig field
        buildConfigField(
            "String",
            "THEY_SAID_SO_API_KEY",
            "\"WBnkO4UV7uM6bf0CxA1AAiYVozD8d9BLZWL7Z31P97b9c43a\""
        )

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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

    buildFeatures {
        buildConfig = true
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }

    viewBinding {
        enable = true
    }
}

dependencies {
    // AndroidX Core and UI
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation("com.jakewharton.threetenabp:threetenabp:1.4.0")

    // Ensure MaterialCalendarView doesn't conflict with androidx.core
    implementation("com.github.prolificinteractive:material-calendarview:2.0.1") {
        exclude(group = "com.android.support") // Exclude conflicting support libraries
    }


    // Navigation Components
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)

    // Retrofit for API calls
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")

    // Room Dependencies
    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.room.ktx)
    implementation(libs.androidx.ui.text.android)
    kapt(libs.androidx.room.compiler)

    // Test Dependencies
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}

configurations.all {
    resolutionStrategy {
        // Force using AndroidX libraries
        force("androidx.core:core:1.15.0")
        force("androidx.appcompat:appcompat:1.6.1")
    }
}
