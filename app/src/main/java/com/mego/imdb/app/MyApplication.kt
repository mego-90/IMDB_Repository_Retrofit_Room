package com.mego.imdb.app

import android.app.Application
import androidx.room.Room
import com.mego.imdb.data.MoviesRepository
import com.mego.imdb.framework.retrofit.RetrofitMoviesDataSource
import com.mego.imdb.framework.retrofit.AuthorizationInterceptor
import com.mego.imdb.framework.retrofit.MoviesRemoteService
import com.mego.imdb.framework.retrofit.MyLoggingInterceptor
import com.mego.imdb.framework.room.MoviesDatabase
import com.mego.imdb.framework.room.RoomMoviesDataSource
import com.mego.imdb.interactors.GetMostPopularMovies
import com.mego.imdb.interactors.MovieTitleAutocomplete
import com.mego.imdb.presentation.autocomplete.AutocompleteViewModel
import com.mego.imdb.presentation.popularmovies.PopularMoviesViewModel
import okhttp3.Cache
import okhttp3.OkHttpClient
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        val mainModule = module {
            single { provideRetrofit() }
            single { provideMoviesDAO() }
            factory { provideMoviesRemoteService( get() ) }
            factory { RetrofitMoviesDataSource(get()) }
            factory { RoomMoviesDataSource(get()) }
            factory { MoviesRepository(get(),get()) }

            factory { GetMostPopularMovies(get()) }
            factory { MovieTitleAutocomplete(get()) }

            viewModel { PopularMoviesViewModel(get()) }
            viewModel { AutocompleteViewModel(get()) }

        }

        startKoin {
            androidLogger()
            androidContext(this@MyApplication)
            modules(mainModule)
        }

    }

    private fun provideMoviesDAO() =
        Room
            .databaseBuilder(this@MyApplication, MoviesDatabase::class.java, MoviesDatabase.name)
            .build()
            .moviesDAO


    private fun provideRetrofit() : Retrofit {

        val client = OkHttpClient().newBuilder()
            .addInterceptor(AuthorizationInterceptor())
            .addInterceptor(MyLoggingInterceptor())
            .cache( Cache(cacheDir, 20*1024L*1024L) )
            .build()

        return Retrofit.Builder()
            .baseUrl("https://imdb8.p.rapidapi.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
    }

    private fun provideMoviesRemoteService(retrofit: Retrofit) =
        retrofit.create(MoviesRemoteService::class.java)


}