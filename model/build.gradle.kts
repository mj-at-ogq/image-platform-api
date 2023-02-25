import org.springframework.boot.gradle.tasks.bundling.BootJar

plugins {
    kotlin("jvm")
}

val jar: Jar by tasks
val bootJar: BootJar by tasks
bootJar.enabled = false
jar.enabled = true
jar.archiveClassifier.convention("") // spring boot 2.5 부터 일반 jar 은 -plain.jar 로 변경됨. 이로 인하여 소스가 인포트되지 않음.

val ocpUtilsVer = "0.2.6"

dependencies {
    implementation("commons-io:commons-io:2.11.0")
    api("me.ogq.ocp.common:ocp-common-utils:$ocpUtilsVer")
}
