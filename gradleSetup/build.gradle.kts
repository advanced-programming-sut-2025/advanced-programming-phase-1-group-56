plugins {
    id("java")
    id("application")
}

group = "com.fiftySix"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    implementation("com.google.code.gson:gson:2.10.1")
    implementation ("com.squareup.okhttp3:okhttp:4.11.0")
    implementation ("com.fasterxml.jackson.core:jackson-databind:2.15.2")
    implementation("com.badlogicgames.gdx:gdx-backend-lwjgl3:1.12.0")
    implementation("com.badlogicgames.gdx:gdx-platform:1.12.0:natives-desktop")
}

tasks.test {
    useJUnitPlatform()
}

application {
    mainClass.set("Main")
}




