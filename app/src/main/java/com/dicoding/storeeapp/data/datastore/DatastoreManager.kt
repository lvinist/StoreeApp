package com.dicoding.storeeapp.data.datastore

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.dicoding.storeeapp.utils.Constants.LOGIN_DATASTORE
import com.dicoding.storeeapp.utils.Constants.LOGIN_TOKEN
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject

val Context.loginDataStore by preferencesDataStore(LOGIN_DATASTORE)
class DatastoreManager @Inject constructor(context: Context) {

    private object PreferenceKeys {
        val loginToken = stringPreferencesKey(LOGIN_TOKEN)
    }

    private val loginDataStore = context.loginDataStore

    val preferenceLoginToken = loginDataStore.data.catch { exception ->
        if (exception is IOException)
            emit(emptyPreferences())
        else
            throw exception
    }.map {
        it[PreferenceKeys.loginToken] ?: ""
    }

    suspend fun updateLoginToken(loginToken: String) {
        loginDataStore.edit {
            it[PreferenceKeys.loginToken] = loginToken
        }
    }

    suspend fun clearUserToken(){
        loginDataStore.edit {
            it.clear()
        }
    }

}