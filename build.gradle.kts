plugins {
    kotlin("jvm") version "2.0.0"
    application
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))

    // Dependência do Retrofit para requisições HTTP
    implementation("com.squareup.retrofit2:retrofit:2.9.0")

    // Para converter o HTML como String
    implementation("com.squareup.retrofit2:converter-scalars:2.9.0")

    // Jsoup para parsing do HTML
    implementation("org.jsoup:jsoup:1.14.3")

    // Kotlin padrão
    implementation(kotlin("stdlib"))

}

tasks.test {
    useJUnitPlatform()
}