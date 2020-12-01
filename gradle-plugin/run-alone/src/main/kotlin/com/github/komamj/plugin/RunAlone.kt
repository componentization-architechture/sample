package com.github.komamj.plugin

import org.gradle.api.Plugin
import org.gradle.api.Project

class RunAlone : Plugin<Project> {
    override fun apply(target: Project) {
        println("RunAlone plugin apply...")
    }
}