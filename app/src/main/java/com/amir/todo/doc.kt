package com.amir.todo

// in the activity we have different type of impl koin dependency
// 1. private val viewModel by viewModel<MianViewModel>()
// 2. val viewModel = getViewModel<MianViewModel>() for compose

// 3. for something that you want to inject as soon as possible we use :
// private val api = get<MyApi>()

// 4. for something that you want to get data when for example data fetch like tokens we use
// private val api = inject<MyApi>()

// 5. we have something called scope its control life time for the real instance of dependency like UserSession, Feature-Specific Services ,CheckoutService

// 6. factory use case : ViewModel, Request-Scoped Dependencies
// 7. Single use case : DatabaseRepository, AuthService