package com.github.komamj.util

import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

fun Project.addCoroutines(configurationName: String = "implementation") {
    dependencies {
        add(configurationName, "org.jetbrains.kotlinx:kotlinx-coroutines-core:$COROUTINES")
        add(configurationName, "org.jetbrains.kotlinx:kotlinx-coroutines-android:$COROUTINES")
    }
}