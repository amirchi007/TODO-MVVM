package com.amir.todo.di

import app.cash.sqldelight.async.coroutines.synchronous
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import com.amir.todo.TodoDatabase
import com.amir.todo.data.RepositoryImpl.TodoRepositoryImpl
import com.amir.todo.domain.Repository.TodoRepository
import org.koin.dsl.module

val appModule = module {
    factory { // like single but create instance after each request for view models

    }

    single { // like providing retrofit
        // Retrofit.Builder()
        // .baseurl(BaseUrl)
        // .addConverterFactory(MoshiConverterFactory.create())
        // .create(ApiService::class.java)
    }
    single {
        TodoDatabase(
            driver = AndroidSqliteDriver(
                schema = TodoDatabase.Schema.synchronous() ,
                context = get(),
                name = "todo.db"
            )
        )
    }
    single<TodoRepository> { TodoRepositoryImpl(get()) }
}