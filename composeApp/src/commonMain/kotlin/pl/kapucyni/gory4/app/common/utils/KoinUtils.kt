package pl.kapucyni.gory4.app.common.utils

import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration
import pl.kapucyni.gory4.app.auth.di.authModule
import pl.kapucyni.gory4.app.common.di.firebaseModule
import pl.kapucyni.gory4.app.common.di.platformModule

private fun getBaseModules() = listOf(
    platformModule,
    firebaseModule,
    authModule,
)

fun initKoin(config: KoinAppDeclaration? = null) {
    startKoin {
        config?.invoke(this)
        modules(getBaseModules())
    }
}