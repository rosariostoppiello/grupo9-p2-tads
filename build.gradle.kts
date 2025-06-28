plugins {
    id("java")
    id("application")
}

group = "um.edu.uy"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

application {
    mainClass.set("um.edu.uy.Main")
}

tasks.test {
    useJUnitPlatform()
}

tasks.jar {
    archiveBaseName.set("grupo9-p2-tads")

    manifest {
        attributes("Main-Class" to "um.edu.uy.Main")
    }

    from(configurations.runtimeClasspath.get().map {
        if (it.isDirectory) it else zipTree(it)
    })

    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
}

tasks.jar {
    archiveBaseName.set("grupo9-p2-tads")
    archiveVersion.set("")
    manifest {
        attributes["Main-Class"] = "tu.paquete.principal.Main"
    }
}