package com.example.newsapp.data.di.modules

import com.example.newsapp.data.datastore.ProviderDataStore
import com.example.newsapp.data.local.repository.FavRepo
import com.example.newsapp.components.search.SearchViewModel
import com.example.newsapp.components.favorites.FavoritesViewModel
import com.example.newsapp.data.remote.GuardianApiServiceImpl
import com.example.newsapp.data.remote.KtorClient
import com.example.newsapp.data.remote.apirepository.NewsRepository
import com.example.newsapp.data.remote.apirepository.NewsRepositoryImpl
import com.example.newsapp.data.remote.GuardianApiService
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val appModule = module {
    single {
        KtorClient()
    }

    single { ProviderDataStore(androidContext()) }
    single<GuardianApiService> { GuardianApiServiceImpl(get<KtorClient>().createClient()) }
    single<NewsRepository> { NewsRepositoryImpl(get()) }
    single<FavRepo> { FavRepo(androidContext()) }
    viewModel { SearchViewModel(get(), get(), get()) }
    viewModelOf(::FavoritesViewModel)
}
