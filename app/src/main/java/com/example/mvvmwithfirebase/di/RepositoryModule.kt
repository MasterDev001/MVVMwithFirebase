package com.example.mvvmwithfirebase.di

import com.example.mvvmwithfirebase.data.repository.NoteRepository
import com.example.mvvmwithfirebase.data.repository.NoteRepositoryImpl
import com.google.firebase.firestore.FirebaseFirestore
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
    fun provideNoteRepository(database:FirebaseFirestore):NoteRepository{
        return NoteRepositoryImpl(database)
    }
}