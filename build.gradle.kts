plugins {
    java
}

group = "org.example"
version = "1.0-SNAPSHOT"

val javafxVersion = "24.0.1"
val platform = "win"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.openjfx:javafx-base:$javafxVersion:$platform")
    implementation("org.openjfx:javafx-controls:$javafxVersion:$platform")
    implementation("org.openjfx:javafx-graphics:$javafxVersion:$platform")
    implementation("org.openjfx:javafx-fxml:$javafxVersion:$platform")

    implementation("org.postgresql:postgresql:42.7.1")

    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

tasks.test {
    useJUnitPlatform()
}