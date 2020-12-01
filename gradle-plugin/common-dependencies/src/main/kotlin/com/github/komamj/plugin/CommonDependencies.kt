/*
 * Copyright 2020 komamj
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.github.komamj.plugin

import com.android.build.gradle.AppExtension
import com.android.build.gradle.BaseExtension
import com.android.build.gradle.LibraryExtension
import com.github.komamj.util.JUNIT
import com.github.komamj.util.KOTLIN
import com.koma.componentization.Configuration
import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

class CommonDependencies : Plugin<Project> {
    override fun apply(target: Project) {
        println("CommonDependencies plugin apply...")

        target.plugins.apply(KOTLIN_ANDROID_PLUGIN)

        target.extensions.create(CUSTOM_CONFIGURATION, Configuration::class.java)

        // Configure common android build parameters.
        configureAndroidExtension(target)

        configureDependencies(target)
    }

    private fun configureDependencies(target: Project) {
        target.dependencies {
            add("implementation", "fileTree(dir: ${"libs"}, include: [${"*.jar"}])")
            add(
                "implementation",
                "org.jetbrains.kotlin:kotlin-stdlib-jdk8:$KOTLIN"
            )
        }

        addTestDependencies(target)
    }

    private fun addTestDependencies(target: Project) {
        target.dependencies {
            add(
                "testImplementation",
                "junit:junit:$JUNIT"
            )
        }
    }

    private fun configureAndroidExtension(target: Project) {
        val androidExtension = target.extensions.getByName(ANDROID_EXTENSION)
        if (androidExtension is BaseExtension) {
            androidExtension.apply {
                compileSdkVersion(COMPILE_SDK_VERSION)
                buildToolsVersion(BUILD_TOOLS_VERSION)

                defaultConfig {
                    minSdkVersion(MIN_SDK_VERSION)
                    targetSdkVersion(TARGET_SDK_VERSION)
                    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
                }

                compileOptions {
                    sourceCompatibility = JavaVersion.VERSION_1_8
                    targetCompatibility = JavaVersion.VERSION_1_8
                }

                target.tasks.withType(KotlinCompile::class.java).configureEach {
                    kotlinOptions {
                        jvmTarget = JVM_TARGET
                    }
                }

                // Configure common proguard file settings.
                configureProguardSettings()
            }
        }
    }

    private fun BaseExtension.configureProguardSettings() {
        when (this) {
            is LibraryExtension -> defaultConfig {
                consumerProguardFiles(LIBRARY_PROGUARD_FILES)
            }
            is AppExtension -> buildTypes {
                getByName(BUILD_TYPE_RELEASE) {
                    isMinifyEnabled = true
                    isShrinkResources = true
                    isZipAlignEnabled = true
                    proguardFiles(
                        getDefaultProguardFile(DEFAULT_APPLICATION_PROGUARD_FILES),
                        APPLICATION_PROGUARD_FILES
                    )
                }
            }
        }
    }

    companion object {
        private const val KOTLIN_ANDROID_PLUGIN = "kotlin-android"
        private const val ANDROID_EXTENSION = "android"
        private const val CUSTOM_CONFIGURATION = "customConfiguration"
        private const val COMPILE_SDK_VERSION = 30
        private const val BUILD_TOOLS_VERSION = "30.0.2"
        private const val MIN_SDK_VERSION = 23
        private const val TARGET_SDK_VERSION = 30
        private const val JVM_TARGET = "1.8"
        private const val BUILD_TYPE_RELEASE = "release"
        private const val LIBRARY_PROGUARD_FILES = "consumer-rules.pro"
        private const val APPLICATION_PROGUARD_FILES = "proguard-rules.pro"
        private const val DEFAULT_APPLICATION_PROGUARD_FILES = "proguard-android-optimize.txt"
    }
}
