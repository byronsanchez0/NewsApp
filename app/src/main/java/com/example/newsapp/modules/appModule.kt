package com.example.newsapp.modules

import com.example.newsapp.FavRepo
import com.example.newsapp.components.search.viewmodel.SearchViewModel
import com.example.newsapp.model.network.GuardianApiService
import com.example.newsapp.model.network.GuardianApiServiceImpl
import com.example.newsapp.model.network.KtorClient
import com.example.newsapp.model.repository.NewsRepository
import com.example.newsapp.model.repository.NewsRepositoryImpl
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val appModule = module {
    single {
        KtorClient()
    }
    //repository injection
    single<GuardianApiService> { GuardianApiServiceImpl(get<KtorClient>().createClient()) }
    single<NewsRepository> { NewsRepositoryImpl(get()) }
    single<FavRepo> { FavRepo(androidContext()) }
//    viewModel { SearchViewModel(get(), get()) }

    viewModelOf(::SearchViewModel)
}