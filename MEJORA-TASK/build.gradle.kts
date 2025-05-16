plugins {
    kotlin("jvm") version "2.0.21"
}

group = "es.prog2425.calcprueba"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
    implementation(kotlin("stdlib"))
    testImplementation("io.kotest:kotest-runner-junit5:5.6.2")
    testImplementation("io.kotest:kotest-runner-junit5:5.6.2")
    testImplementation("io.kotest:kotest-assertions-core:5.5.4")
    testImplementation("io.kotest:kotest-property:5.5.4")
    testImplementation("io.mockk:mockk:1.13.5")
    implementation("ch.qos.logback:logback-classic:1.4.11")
    testImplementation("net.bytebuddy:byte-buddy:1.14.5")
    testImplementation("net.bytebuddy:byte-buddy-agent:1.14.5")
}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<Test> {
    jvmArgs("-Dnet.bytebuddy.experimental=true")
}

sourceSets {
    test {
        java {
            setSrcDirs(listOf("src/test/kotlin"))
        }
    }
}

kotlin {
    jvmToolchain(21)
}