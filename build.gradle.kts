import org.apache.tools.ant.taskdefs.condition.Os

/*
 * This file was generated by the Gradle 'init' task.
 *
 * This generated file contains a sample Java project to get you started.
 * For more details take a look at the Java Quickstart chapter in the Gradle
 * User Manual available at https://docs.gradle.org/6.6.1/userguide/tutorial_java_projects.html
 */
buildscript {
    dependencies {
        classpath("org.openjfx:javafx-plugin:0.0.13")
        classpath("org.javamodularity:moduleplugin:1.8.12")
    }
    repositories {
        mavenLocal()
        mavenCentral()
        maven {
            setUrl("https://plugins.gradle.org/m2/")
        }
    }
}
apply(plugin = "java")
apply(plugin = "org.javamodularity.moduleplugin")
apply(plugin = "org.openjfx.javafxplugin")


plugins {
    // Apply the java plugin to add support for Java
    java
    // Apply the application plugin to add support for building a CLI application.
    application
    id("org.openjfx.javafxplugin") version "0.0.10"
    id("org.javamodularity.moduleplugin") version "1.8.10"
}

repositories {
    // Use jcenter for resolving dependencies.
    // You can declare any Maven/Ivy/file repository here.
//    jcenter()
    mavenCentral()
}

dependencies {
    // This dependency is used by the application.
    implementation("com.google.guava:guava:31.0.1-jre")
    implementation("com.drewnoakes:metadata-extractor:2.16.0")
    implementation("org.jfxtras:jmetro:11.6.14") {
        exclude("org.openjfx")
    }
    implementation("org.json:json:20220320")

    testImplementation("org.junit.jupiter:junit-jupiter:5.8.2")
    testImplementation("org.mockito:mockito-core:4.1.0")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.8.2")
}


tasks.compileTestJava {
    modularity.inferModulePath.set(false)
}
tasks.test {
    useJUnitPlatform()
    extensions.configure(org.javamodularity.moduleplugin.extensions.TestModuleOptions::class) {
        runOnClasspath = true
    }
}

application {
    // Define the main class for the application.
    mainClass.set("io.penblog.filewizard.Main")
    mainModule.set("FileWizard")
}

javafx {
    version = "17.0.2"
    modules("javafx.controls", "javafx.fxml", "javafx.graphics", "javafx.web")
}