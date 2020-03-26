plugins {
    kotlin("multiplatform") version "1.4-M1"
    kotlin("plugin.serialization") version "1.3.70"
    id("com.android.library") version "3.6.0"
    id("maven-publish")
}

group = "tz.co.asoft"
version = rootProject.version

repositories {
    google()
    jcenter()
    maven(url = "https://jitpack.io")
    maven(url = "https://dl.bintray.com/kotlin/kotlin-eap")
}

android {
    compileSdkVersion(28)
    defaultConfig {
        minSdkVersion(1)
        testInstrumentationRunner = "android.support.test.runner.AndroidJUnitRunner"
    }

    sourceSets {
        val main by getting {
            manifest.srcFile("src/androidMain/AndroidManifest.xml")
            resources.srcDirs("src/androidMain/resources")
        }
    }

    lintOptions {

    }

    buildTypes {
        val release by getting {
            isMinifyEnabled = false
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
}

kotlin {
    android {
        compilations.all {
            kotlinOptions { jvmTarget = "1.8" }
        }
        publishLibraryVariants("release")
    }

    jvm {
        compilations.all {
            kotlinOptions { jvmTarget = "1.8" }
        }
    }

    js {
        compilations.all {
            kotlinOptions {
                metaInfo = true
                sourceMap = true
                moduleKind = "commonjs"
            }
        }
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(kotlin("stdlib-common"))
                api("org.jetbrains.kotlinx:kotlinx-coroutines-core-common:${versions.kotlinx.coroutines}")
            }
        }

        val commonTest by getting {
            dependencies {
                implementation(asoftTest("metadata"))
            }
        }

        val androidMain by getting {
            dependencies {
                implementation(kotlin("stdlib"))
                api("org.jetbrains.kotlinx:kotlinx-coroutines-android:${versions.kotlinx.coroutines}")
                api("androidx.appcompat:appcompat:${versions.androidx.appcompat}")
            }
        }

        val androidTest by getting {
            dependencies {
                implementation(asoftTest("android"))
            }
        }

        val jvmMain by getting {
            dependencies {
                implementation(kotlin("stdlib"))
                api("org.jetbrains.kotlinx:kotlinx-coroutines-core:${versions.kotlinx.coroutines}")
                api("org.jetbrains.kotlinx:kotlinx-serialization-runtime:${versions.kotlinx.serialization}")
            }
        }

        val jvmTest by getting {
            dependencies {
                implementation(asoftTest("jvm"))
            }
        }
        val jsMain by getting {
            dependencies {
                implementation(kotlin("stdlib-js"))
                api("org.jetbrains.kotlinx:kotlinx-coroutines-core-js:${versions.kotlinx.coroutines}")
            }
        }
        val jsTest by getting {
            dependencies {
                implementation(asoftTest("js"))
            }
        }
    }
}