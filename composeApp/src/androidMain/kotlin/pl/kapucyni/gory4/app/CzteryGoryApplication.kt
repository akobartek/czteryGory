package pl.kapucyni.gory4.app

import android.app.Application
import org.koin.android.ext.koin.androidContext
import pl.kapucyni.gory4.app.common.utils.initKoin

class CzteryGoryApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        initKoin {
            androidContext(this@CzteryGoryApplication)
        }
    }
}