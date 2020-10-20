plugins {
    java
}

plugins.apply("org.gradle.java-library")

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.xerial:sqlite-jdbc:3.8.11.2")
    implementation("org.eclipse.jetty:jetty-server:9.4.21.v20190926")
    implementation("org.eclipse.jetty:jetty-servlet:9.4.21.v20190926")

    implementation(group = "org.jboss.resteasy", name = "resteasy-client", version = "4.5.8.Final")

    val junitVersion = "5.6.0"
    "testImplementation"("org.junit.jupiter:junit-jupiter-api:$junitVersion")
    "testRuntimeOnly"("org.junit.jupiter:junit-jupiter-engine:$junitVersion")
    "testImplementation"("org.junit.jupiter:junit-jupiter-params:$junitVersion")
}

tasks.getting(Test::class) {
    useJUnitPlatform()
}
