plugins {
    `java-gradle-plugin`
    `maven-publish`
    `kotlin-dsl`
}

group = "com.github.komamj"
version = "1.0.0-snapshot"

gradlePlugin {
    plugins {
        create("commonDependenciesPlugin") {
            id = "com.github.komamj.run-alone-plugin"
            displayName = "runAlonePlugin"
            description = "The run alone plugin for componentization architechture."
            implementationClass = "com.github.komamj.plugin.RunAlone"
        }
    }
}

dependencies {
    compileOnly(gradleApi())
    implementation("com.android.tools.build:gradle:4.1.1")
    implementation(kotlin("gradle-plugin", "1.4.20"))
}

repositories {
    google()
    jcenter()
}

publishing {
    repositories {
        maven {
            url = uri("../../repos")
        }
    }
}