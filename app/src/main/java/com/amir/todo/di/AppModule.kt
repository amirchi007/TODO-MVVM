package com.amir.todo.di

import app.cash.sqldelight.async.coroutines.synchronous
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import com.amir.todo.TodoDatabase
import com.amir.todo.data.RepositoryImpl.TodoRepositoryImpl
import com.amir.todo.domain.repository.TodoRepository
import com.amir.todo.domain.useCases.DeleteTask
import com.amir.todo.domain.useCases.EditTask
import com.amir.todo.domain.useCases.GetAllTask
import com.amir.todo.domain.useCases.GetTaskById
import com.amir.todo.domain.useCases.UpdateTaskCompletion
import com.amir.todo.presentation.ui.main.add.AddEditTaskViewModel
import com.amir.todo.presentation.ui.main.details.TaskDetailsScreenViewModel
import com.amir.todo.presentation.ui.main.list.TaskListViewModel
import org.koin.dsl.module

val appModule = module {
    factory { /* like single but create instance after each request for view models */ }


    single { /* like providing retrofit
        Retrofit.Builder()
            .baseurl(BaseUrl)
            .addConverterFactory(MoshiConverterFactory.create())
            .create(ApiService::class.java) */
    }
    single {
        TodoDatabase(
            driver = AndroidSqliteDriver(
                schema = TodoDatabase.Schema.synchronous(),
                context = get(),
                name = "todo.db"
            )
        )
    }
    single<TodoRepository> { TodoRepositoryImpl(get()) }
    factory { GetAllTask(get()) }
    factory { UpdateTaskCompletion(get()) }
    factory { DeleteTask(get()) }
    factory { GetTaskById(get()) }
    factory { EditTask(get()) }

    factory { TaskListViewModel(get(), get(), get()) }
    factory { TaskDetailsScreenViewModel(get()) }
    factory { AddEditTaskViewModel(get(), get()) }

}