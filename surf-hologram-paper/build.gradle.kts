import dev.slne.surf.surfapi.gradle.util.registerSoft

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
        registerSoft("MiniPlaceholders")
    }

    authors.add("red")
}