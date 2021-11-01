import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

@Suppress("DSL_SCOPE_VIOLATION")
plugins {
	alias(libs.plugins.spring.boot)
	alias(libs.plugins.spring.dependency.management)
	alias(libs.plugins.kotlin.jvm)
	alias(libs.plugins.kotlin.spring)
	alias(libs.plugins.kotlin.jpa)
	alias(libs.plugins.graphql)
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
	implementation(libs.bundles.db)
	implementation(platform(libs.graphql.dependencies))
	implementation(libs.graphql.starter)
	developmentOnly(libs.bundles.springDev)
	testImplementation(libs.spring.test) {
		exclude(module = "junit")
		exclude(module = "mockito-core")
	}
	testImplementation(libs.jupiter.api)
	testImplementation(libs.kotlin.test)
	testImplementation(libs.hibernate.test)
	testRuntimeOnly(libs.jupiter.engine)
	testImplementation(libs.spring.mockk)
}

tasks.withType<KotlinCompile> {
	kotlinOptions {
		freeCompilerArgs = listOf("-Xjsr305=strict", "-Xuse-experimental=kotlin.Experimental")
		jvmTarget = "16"
	}
}

tasks.withType<com.netflix.graphql.dgs.codegen.gradle.GenerateJavaTask> {
	packageName = "com.testspring.rpedretti.springkotlin.graphql.generated" // The package name to use to generate sources
	generateClient = true // Enable generating the type safe query API
}

tasks.withType<Test> {
	useJUnitPlatform()
}
