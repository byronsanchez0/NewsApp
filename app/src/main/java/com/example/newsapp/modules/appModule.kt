package com.example.newsapp.modules

import com.example.newsapp.data.local.repository.FavRepo
import com.example.newsapp.components.search.viewmodel.SearchViewModel
import com.example.newsapp.components.favorites.FavoritesViewModel
import com.example.newsapp.network.GuardianApiServiceImpl
import com.example.newsapp.network.KtorClient
import com.example.newsapp.model.data.remote.apirepository.NewsRepository
import com.example.newsapp.model.data.remote.apirepository.NewsRepositoryImpl
import com.example.newsapp.network.GuardianApiService
import org.koin.android.ext.koin.androidContext
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
    viewModelOf(::FavoritesViewModel)
//    viewModelOf(::Fav)

}