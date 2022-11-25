package com.example.mvvmwithfirebase.di

import android.content.SharedPreferences
import com.example.mvvmwithfirebase.data.repository.AuthRepository
import com.example.mvvmwithfirebase.data.repository.AuthRepositoryImpl
import com.example.mvvmwithfirebase.data.repository.NoteRepository
import com.example.mvvmwithfirebase.data.repository.NoteRepositoryImpl
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.StorageReference
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object RepositoryModule {

    @Provides
    @Singleton
    fun provideNoteRepository(
        database: FirebaseFirestore,
        storageReference: StorageReference
    ): NoteRepository {
        return NoteRepositoryImpl(database,storageReference)
    }

    @Provides
    @Singleton
    fun provideAuthRepository(
        database: FirebaseFirestore,
        auth: FirebaseAuth,
        appPreferences: SharedPreferences,
        gson: Gson
    ): AuthRepository {
        return AuthRepositoryImpl(auth, database, appPreferences, gson)
    }
}