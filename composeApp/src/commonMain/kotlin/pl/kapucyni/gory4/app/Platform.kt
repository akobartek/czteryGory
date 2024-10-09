package pl.kapucyni.gory4.app

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform