package pl.kapucyni.gory4.app.common.di

import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.auth.auth
import dev.gitlive.firebase.firestore.firestore
import org.koin.dsl.module

val firebaseModule = module {
    single { Firebase.auth }
    single { Firebase.firestore }
}