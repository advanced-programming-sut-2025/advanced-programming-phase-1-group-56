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
    implementation ("com.google.code.gson:gson:2.10.1")
}

tasks.test {
    useJUnitPlatform()
}

application {
    mainClass.set("Main")
}




