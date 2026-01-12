plugins {
	alias(libs.plugins.android.application)
	alias(libs.plugins.kotlin.android)
	alias(libs.plugins.kotlin.compose)
	kotlin("plugin.serialization") version "2.3.0"
}

android {
	namespace = "com.nin0dev.vendroid"
	compileSdk {
		version = release(36)
	}

	defaultConfig {
		applicationId = "com.nin0dev.vendroid"
		minSdk = 29
		targetSdk = 36
		versionCode = 14
		versionName = "2.0"
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
		sourceCompatibility = JavaVersion.VERSION_11
		targetCompatibility = JavaVersion.VERSION_11
	}
	buildFeatures {
		compose = true
		buildConfig = true
	}
}

kotlin {
	compilerOptions {
		jvmTarget.set(org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_11)
	}
}

dependencies {
	implementation(libs.androidx.core.ktx)
	implementation(libs.androidx.lifecycle.runtime.ktx)
	implementation(libs.androidx.activity.compose)
	implementation(platform(libs.androidx.compose.bom))
	implementation(libs.androidx.compose.ui)
	implementation(libs.androidx.compose.ui.graphics)
	implementation(libs.androidx.compose.ui.tooling.preview)
	implementation(libs.androidx.compose.material3)
	implementation(libs.androidx.ui)
	implementation(libs.androidx.material3)
	implementation(libs.androidx.browser)
	implementation(libs.androidx.compose.material.icons.extended)
	implementation(libs.androidx.compose.foundation.layout)
	implementation(libs.androidx.navigation.compose)
	implementation(libs.androidx.compose.animation)
	implementation(libs.volley)
	implementation(libs.androidx.datastore.preferences)
	debugImplementation(libs.androidx.compose.ui.tooling)
	implementation(libs.kotlinx.serialization.json)
}
