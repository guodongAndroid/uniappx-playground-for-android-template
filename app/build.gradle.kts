import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
}

android {
    namespace = "com.guodong.uniappx.playground"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.guodong.uniappx.playground"
        minSdk = 21

        // https://doc.dcloud.net.cn/uni-app-x/native/debug/android.html
        // 当build.gradle中的targetSdk为34时，在安卓14设备上资源同步会失败。建议将targetSdk调整到30至33之间。
        //noinspection ExpiredTargetSdkVersion
        targetSdk = 32

        // region 版本号应该与uni-app x应用一致
        versionCode = 100
        versionName = "1.0.0"
        // endregion

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        ndk {
            abiFilters += listOf("x86_64", "armeabi-v7a", "arm64-v8a")
        }

        buildConfigField("String", "UTSRegisterComponents", "\"[{\\\"name\\\":\\\"video\\\",\\\"class\\\":\\\"uts.sdk.modules.DCloudUniVideo.VideoComponent\\\"}]\"")
    }

    buildFeatures {
        buildConfig = true
    }

    signingConfigs {
        create("release") {
            storeFile = File(project.projectDir, "keystore")
            storePassword = "123456"
            keyAlias = "uniappx"
            keyPassword = "123456"
        }
    }

    buildTypes {
        debug {
            signingConfig = signingConfigs["release"]
        }

        release {
            isMinifyEnabled = false
            signingConfig = signingConfigs["release"]
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }

    kotlin {
        compilerOptions {
            jvmTarget = JvmTarget.JVM_1_8
        }
    }

    androidResources {
        additionalParameters += "--auto-add-overlay"
        ignoreAssetsPattern = "!.svn:!.git:.*:!CVS:!thumbs.db:!picasa.ini:!*.scc:*~"
    }
}

dependencies {
    /*implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)*/

    // for custom playground
    implementation(fileTree(mapOf("include" to listOf("*.aar"), "dir" to "../libs")))
    implementation("androidx.core:core-ktx:1.10.1")
    implementation("androidx.recyclerview:recyclerview:1.3.2")
    implementation("androidx.appcompat:appcompat:1.0.0")
    implementation("androidx.exifinterface:exifinterface:1.3.6")
    implementation("androidx.localbroadcastmanager:localbroadcastmanager:1.0.0@aar")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("androidx.webkit:webkit:1.6.0")
    implementation("com.google.android.material:material:1.4.0")
    implementation("androidx.viewpager2:viewpager2:1.1.0-beta02")
    implementation("com.alibaba:fastjson:1.2.83")
    implementation("com.facebook.fresco:fresco:3.4.0")
    implementation("com.facebook.fresco:middleware:3.4.0")
    implementation("com.facebook.fresco:animated-gif:3.4.0")
    implementation("com.facebook.fresco:webpsupport:3.4.0")
    implementation("com.facebook.fresco:animated-webp:3.4.0")
    implementation("com.caverock:androidsvg:1.4")
    implementation("com.github.bumptech.glide:glide:4.9.0")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.4")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.6.4")
    implementation("org.jetbrains.kotlin:kotlin-stdlib:2.2.0")
    implementation("org.jetbrains.kotlin:kotlin-reflect:2.2.0")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.4.1")
    implementation("com.squareup.okhttp3:okhttp:3.12.12")
    implementation("com.github.getActivity:XXPermissions:18.63")

    // adapt 4.75
    implementation("net.lingala.zip4j:zip4j:2.11.5")

    implementation("com.squareup.leakcanary:leakcanary-android:2.14")

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}