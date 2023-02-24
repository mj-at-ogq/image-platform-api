plugins {
    kotlin("jvm")
}


dependencies {
    implementation(project(":controller"))
    implementation(project(":gateway"))
    implementation(project(":config"))
}
