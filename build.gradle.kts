import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

@Suppress("DSL_SCOPE_VIOLATION")
plugins {
	alias(libs.plugins.spring.boot)
	alias(libs.plugins.spring.dependency.management)
	alias(libs.plugins.kotlin.jvm)
	alias(libs.plugins.kotlin.spring)
}

group = "com.testspring.rpedretti"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_16

repositories {
	mavenCentral()
}

dependencies {
	implementation(libs.bundles.spring)
	implementation(libs.bundles.kotlin)
	developmentOnly(libs.bundles.springDev)
	testImplementation(libs.spring.test) {
		exclude(module = "junit")
		exclude(module = "mockito-core")
	}
	testImplementation(libs.jupiter.api)
	testImplementation(libs.kotlin.test)
	testRuntimeOnly(libs.jupiter.engine)
	testImplementation(libs.spring.mockk)
}

tasks.withType<KotlinCompile> {
	kotlinOptions {
		freeCompilerArgs = listOf("-Xjsr305=strict", "-Xuse-experimental=kotlin.Experimental")
		jvmTarget = "16"
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}
