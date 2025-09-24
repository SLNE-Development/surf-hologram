plugins {
    id("dev.slne.surf.surfapi.gradle.paper-plugin")
}

dependencies {
    api(project(":surf-hologram-core"))
    compileOnly(libs.miniplaceholder.api)
}

surfPaperPluginApi {
    mainClass("dev.slne.surf.hologram.paper.PaperMain")
    foliaSupported(true)
    generateLibraryLoader(false)

    serverDependencies {
        register("MiniPlaceholders")
    }

    authors.add("red")
}