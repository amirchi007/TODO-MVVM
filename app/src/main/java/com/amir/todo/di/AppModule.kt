package com.amir.todo.di

import com.amir.todo.TodoDatabase
import com.amir.todo.data.RepositoryImpl.TodoRepositoryImpl
import com.amir.todo.domain.Repository.TodoRepository
import org.koin.dsl.module

val appModule = module {
    factory { // like single but create instance after each request

    }

    single { // like providing retrofit
        // Retrofit.Builder()
        // .baseurl(BaseUrl)
        // .addConverterFactory(MoshiConverterFactory.create())
        // .create(ApiService::class.java)
    }
    single { TodoDatabase }
    single<TodoRepository> { TodoRepositoryImpl(get()) }
}