import dev.slne.surf.surfapi.gradle.util.registerRequired

plugins {
    id("dev.slne.surf.surfapi.gradle.paper-plugin")
}

dependencies {
    compileOnly(project(":surf-hologram-api"))
}

surfPaperPluginApi {
    mainClass("dev.slne.surf.hologram.example.dsl.BukkitMain")
    authors.add("red")
    generateLibraryLoader(false)

    serverDependencies {
        registerRequired("surf-hologram-paper")
    }
}