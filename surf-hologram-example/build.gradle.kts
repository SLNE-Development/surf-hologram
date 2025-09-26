import dev.slne.surf.surfapi.gradle.util.registerRequired

plugins {
    id("dev.slne.surf.surfapi.gradle.paper-plugin")
}

dependencies {
    compileOnly(project(":surf-hologram-api"))
}

surfPaperPluginApi {
    mainClass("dev.slne.surf.hologram.example.BukkitMain")
    authors.add("red")

    serverDependencies {
        registerRequired("surf-hologram-bukkit")
    }
}