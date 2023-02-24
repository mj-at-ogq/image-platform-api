import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.6.21"
    kotlin("plugin.spring") version "1.6.21" apply false
    kotlin("plugin.jpa") version "1.6.21" apply false
    id("org.springframework.boot") version "2.7.2" apply false
    id("io.spring.dependency-management") version "1.0.12.RELEASE" apply false
}

val jar: Jar by tasks

jar.enabled = false

repositories {
    maven {
        url = uri("https://nexus.ocp.ogq.me/repository/ogq/")
    }
}


java.sourceCompatibility = JavaVersion.VERSION_11
java.targetCompatibility = JavaVersion.VERSION_11
val koTestVer = "5.5.4"

subprojects {
    repositories {
        maven {
            url = uri("https://nexus.ocp.ogq.me/repository/ogq/")
        }
    }
    group = "me.ogq.ocp"
    version = "0.0.1-SNAPSHOT"

    apply {
        plugin("kotlin")
        plugin("org.springframework.boot")
        plugin("io.spring.dependency-management")
        plugin("org.jetbrains.kotlin.plugin.spring")
        plugin("org.jetbrains.kotlin.plugin.jpa")
    }
    java.sourceCompatibility = JavaVersion.VERSION_11
    java.targetCompatibility = JavaVersion.VERSION_11

    tasks.withType<KotlinCompile> {
        kotlinOptions {
            freeCompilerArgs = listOf("-Xjsr305=strict")
            jvmTarget = "11"
        }
    }

    dependencies {
        implementation("org.springframework.boot:spring-boot-starter-data-jpa")
        implementation("org.springframework.boot:spring-boot-starter-web")
        implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
        implementation("org.jetbrains.kotlin:kotlin-reflect")
        implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
        runtimeOnly("org.mariadb.jdbc:mariadb-java-client")
        testImplementation("org.springframework.boot:spring-boot-starter-test")

        testImplementation("io.kotest:kotest-runner-junit5:$koTestVer")
        testImplementation("io.kotest:kotest-assertions-core:$koTestVer")
        testImplementation("io.kotest:kotest-property:$koTestVer")
    }


    tasks.withType<Test> {
        useJUnitPlatform()
    }

}

