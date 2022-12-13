plugins {
    kotlin("jvm") version "1.7.22"
}

dependencies {
    implementation("org.json:json:20220924")
}

repositories {
    mavenCentral()
}

tasks {
    sourceSets {
        main {
            java.srcDirs("src")
        }
    }

    wrapper {
        gradleVersion = "7.6"
    }
}
