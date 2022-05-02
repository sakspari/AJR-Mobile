package com.example.atmajayarental.dependency_injection

import com.example.atmajayarental.data.api.AuthApi
import com.example.atmajayarental.data.api.PromoApi
import com.example.atmajayarental.data.api.UrlDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideAuthApi(builder: Retrofit.Builder): AuthApi {
        return builder
            .build()
            .create(AuthApi::class.java)
    }

    @Provides
    @Singleton
    fun providePromoApi(builder: Retrofit.Builder): PromoApi {
        return builder
            .build()
            .create(PromoApi::class.java)
    }

    @Provides
    @Singleton
    fun provideRetrofit() : Retrofit.Builder{
        return Retrofit.Builder()
            .baseUrl(UrlDataSource.API)
            .addConverterFactory(MoshiConverterFactory.create())
    }

}