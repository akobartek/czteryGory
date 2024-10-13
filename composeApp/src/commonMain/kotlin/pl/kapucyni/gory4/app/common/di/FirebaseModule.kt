package pl.kapucyni.gory4.app.common.di

import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.auth.auth
import dev.gitlive.firebase.firestore.firestore
import org.koin.dsl.module
import pl.kapucyni.gory4.app.common.data.FirebaseUserRepository
import pl.kapucyni.gory4.app.common.domain.UserRepository

val firebaseModule = module {
    single { Firebase.auth }
    single { Firebase.firestore }

    single<UserRepository> { FirebaseUserRepository(get(), get()) }
}