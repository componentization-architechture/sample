plugins {
    `java-gradle-plugin`
    `maven-publish`
    `kotlin-dsl`
}

group = "com.koma.componentization"
version = "1.0.0-snapshot"

gradlePlugin {
    plugins {
        create("commonDependenciesPlugin") {
            id = "com.koma.componentization.common-dependencies-plugin"
            displayName = "commonDependenciesPlugin"
            description = "The componentization of commonDependenciesPlugin."
            implementationClass = "com.github.komamj.dependencies.plugin.CommonDependencies"
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
            url = uri("../repos")
        }
    }
}