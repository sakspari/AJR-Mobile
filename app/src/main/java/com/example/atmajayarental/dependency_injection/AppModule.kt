package com.example.atmajayarental.dependency_injection

import com.example.atmajayarental.data.api.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
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
    fun provideMobilApi(builder: Retrofit.Builder): MobilApi {
        return builder
            .build()
            .create(MobilApi::class.java)
    }

    @Provides
    @Singleton
    fun provideCustomerApi(builder: Retrofit.Builder): CustomerApi {
        return builder
            .build()
            .create(CustomerApi::class.java)
    }

    @Provides
    @Singleton
    fun provideDriverApi(builder: Retrofit.Builder): DriverApi {
        return builder
            .build()
            .create(DriverApi::class.java)
    }

    @Provides
    @Singleton
    fun providePegawaiApi(builder: Retrofit.Builder): PegawaiApi {
        return builder
            .build()
            .create(PegawaiApi::class.java)
    }

    @Provides
    @Singleton
    fun provideTransaksiApi(builder: Retrofit.Builder): TransaksiApi {
        return builder
            .build()
            .create(TransaksiApi::class.java)
    }

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit.Builder {
//        set time out time
        val client = OkHttpClient
            .Builder()
            .readTimeout(15, TimeUnit.SECONDS)
            .connectTimeout(15, TimeUnit.SECONDS)
            .callTimeout(15, TimeUnit.SECONDS)
            .writeTimeout(15, TimeUnit.SECONDS)
            .build()

        return Retrofit.Builder()
            .baseUrl(UrlDataSource.API)
            .addConverterFactory(MoshiConverterFactory.create())
            .client(client) // apply time out
    }

}