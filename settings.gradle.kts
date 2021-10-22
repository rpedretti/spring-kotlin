rootProject.name = "spring-kotlin"
enableFeaturePreview("VERSION_CATALOGS")
dependencyResolutionManagement {
    versionCatalogs {
        create("libs") {
            version("spring", "2.5.5")
            version("coroutines", "1.5.2")
            version("kotlin", "1.5.31")
            version("openApi", "1.5.11")
            version("jupiter", "5.7.2")

            // plugins
            alias("spring-boot").toPluginId("org.springframework.boot").versionRef("spring")
            alias("spring-dependency-management").toPluginId("io.spring.dependency-management")
                .version("1.0.11.RELEASE")
            alias("kotlin-jvm").toPluginId("org.jetbrains.kotlin.jvm").versionRef("kotlin")
            alias("kotlin-spring").toPluginId("org.jetbrains.kotlin.plugin.spring").versionRef("kotlin")

            // spring
            alias("spring-webflux").to("org.springframework.boot", "spring-boot-starter-webflux").versionRef("spring")
            alias("jackson-kotlin").to("com.fasterxml.jackson.module", "jackson-module-kotlin").version("2.13.0")
            alias("openapi-webflux-ui").to("org.springdoc", "springdoc-openapi-webflux-ui").versionRef("openApi")

            // kotlin
            alias("kotlin-reflect").to("org.jetbrains.kotlin", "kotlin-reflect").versionRef("kotlin")
            alias("kotlin-coroutines-core").to("org.jetbrains.kotlinx", "kotlinx-coroutines-core")
                .versionRef("coroutines")
            alias("kotlin-coroutines-reactor").to("org.jetbrains.kotlinx", "kotlinx-coroutines-reactor")
                .versionRef("coroutines")
            alias("kotlin-stdlib").to("org.jetbrains.kotlin", "kotlin-stdlib").versionRef("kotlin")
            alias("openapi-kotlin").to("org.springdoc", "springdoc-openapi-kotlin").versionRef("openApi")

            // dev
            alias("spring-actuator").to("org.springframework.boot", "spring-boot-starter-actuator").versionRef("spring")
            alias("spring-devtools").to("org.springframework.boot", "spring-boot-devtools").versionRef("spring")

            // test
            alias("spring-test").to("org.springframework.boot", "spring-boot-starter-test").versionRef("spring")
            alias("jupiter-api").to("org.junit.jupiter", "junit-jupiter-api").versionRef("jupiter")
            alias("jupiter-engine").to("org.junit.jupiter", "junit-jupiter-engine").versionRef("jupiter")
            alias("spring-mockk").to("com.ninja-squad", "springmockk").version("3.0.1")
            alias("kotlin-test").to("org.jetbrains.kotlinx", "kotlinx-coroutines-test").versionRef("coroutines")

            // bundles
            bundle("spring", listOf("spring-webflux", "openapi-kotlin", "openapi-webflux-ui", "jackson-kotlin"))
            bundle(
                "kotlin",
                listOf("kotlin-reflect", "kotlin-coroutines-core", "kotlin-coroutines-reactor", "kotlin-stdlib")
            )
            bundle("springDev", listOf("spring-actuator", "spring-devtools"))
        }
    }
}
