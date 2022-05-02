package com.example.atmajayarental.data.userpreferences

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.atmajayarental.data.api.model.AuthResponse
import com.example.atmajayarental.data.api.model.User
import com.squareup.moshi.Json
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.adapter
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

const val DataStore_NAME = "LOGIN_PREFERENCES"
val Context.datastore: DataStore<Preferences> by preferencesDataStore(name = DataStore_NAME)

class UserPreferencesImpl(private val context: Context) : UserPreferennces {


    companion object {
        val IS_LOGIN = booleanPreferencesKey("IS_LOGIN")
        val MESSAGE = stringPreferencesKey("MESSAGE")
        val USER = stringPreferencesKey("USER")
        val TOKEN_TYPE = stringPreferencesKey("TOKEN_TYPE")
        val ACCESS_TOKEN = stringPreferencesKey("ACCESS_TOKEN")
    }


    val moshi: Moshi = Moshi.Builder().build()

    @OptIn(ExperimentalStdlibApi::class)
    val jsonAdapter: JsonAdapter<User> = moshi.adapter<User>()

    override suspend fun saveUserLogin(authResponse: AuthResponse) {

        context.datastore.edit { loginData ->
//            loginData[IS_LOGIN] = authResponse.user != null
            loginData[MESSAGE] = authResponse.message.toString()
            if (authResponse.user != null)
                loginData[USER] = jsonAdapter.toJson(authResponse.user)
            loginData[TOKEN_TYPE] = authResponse.token_type.toString()
            loginData[ACCESS_TOKEN] = authResponse.access_token.toString()
        }
    }

    override suspend fun clearDataStore() {
        context.datastore.edit {
            it.clear()
        }
    }


    override suspend fun getUserLogin() = context.datastore.data.map { loginData ->
        AuthResponse(
            message = loginData[MESSAGE],
            user = loginData[USER]?.let { jsonAdapter.fromJson(it) },
            token_type = loginData[TOKEN_TYPE],
            access_token = loginData[ACCESS_TOKEN]
        )
    }

    override suspend fun getToken() = context.datastore.data.map { loginData ->
        "${ loginData[TOKEN_TYPE]!! } ${ loginData[ACCESS_TOKEN]!! } "
    }

//    override suspend fun getToken(): String {
//        var token: String = ""
//        context.datastore.data.map { loginData->
//            token = "${loginData[TOKEN_TYPE]!!} ${loginData[ACCESS_TOKEN]!!}"
//        }
//        return token
//    }

    //    override suspend fun getToken() = context.datastore.data.map { loginData ->
//       loginData[TOKEN_TYPE]
//    }
}