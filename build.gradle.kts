plugins {
    id("java")
    id("application")
}

group = "com.distribuida"
version = "unspecified"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.apache.openwebbeans:openwebbeans-impl:4.0.2")
    implementation("org.eclipse.persistence:eclipselink:4.0.3")
    implementation("io.helidon.webserver:helidon-webserver:4.0.9")
    implementation("org.xerial:sqlite-jdbc:3.46.0.0")
    implementation("com.google.code.gson:gson:2.11.0")
}

sourceSets {
    main {
        output.setResourcesDir( file("${buildDir}/classes/java/main"))
    }

}

tasks.jar {
    manifest {
        attributes(
            mapOf("Main-Class" to "com.distribuida.Main",
                "Class-Path" to configurations.runtimeClasspath
                    .get()
                    .joinToString(separator = " ") { file ->
                        "${file.name}"
                    })
        )
    }
}

tasks.test {
    useJUnitPlatform()
}