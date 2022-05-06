package com.example.atmajayarental

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.MutableLiveData
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.atmajayarental.data.api.model.AuthResponse
import com.example.atmajayarental.data.userpreferences.UserPreferencesImpl
import com.example.atmajayarental.ui.auth.AuthScreen
import com.example.atmajayarental.ui.home.HomeScreen
import com.example.atmajayarental.ui.home.customer.CustomerHomeScreen
import com.example.atmajayarental.ui.home.driver.DriverHomeScreen
import com.example.atmajayarental.ui.home.manager.ManagerHomeScreen
import com.example.atmajayarental.ui.promo.PromoScreen
import com.example.atmajayarental.ui.theme.AtmaJayaRentalTheme
import com.example.atmajayarental.util.Routes
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity: ComponentActivity() {

    @Inject
    lateinit var userPreferences: UserPreferencesImpl


    var authResponse: MutableLiveData<AuthResponse> = MutableLiveData()
    var startRoute by mutableStateOf(Routes.AUTH)
        private set

//    suspend fun getUserLogin(){
//        userPreferences.getUserLogin().collect {
//            authResponse.postValue(it)
//        }
//    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        userPreferences = UserPreferencesImpl(this)
        CoroutineScope(Dispatchers.Default).launch{
            userPreferences.getUserLogin().collect {
                authResponse.postValue(it)
                if(it.user!=null){
                    when(it.user.level){
                        "CUSTOMER" -> startRoute = Routes.HOME_CUSTOMER
                        "DRIVER" -> startRoute = Routes.HOME_DRIVER
                        else->startRoute = Routes.HOME_MANAGER
                    }
                }
//                    startRoute = Routes.HOME
            }
        }

        setContent {
            AtmaJayaRentalTheme {
                val navController = rememberNavController()

                // A surface container using the 'background' color from the theme
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.background) {

//                    if(authResponse.value?.user != null)
//                        startRoute = Routes.HOME
//                    else
//                        Log.i("USFPERF-------",authResponse.value.toString())


                    NavHost(navController = navController, startDestination = startRoute){

                        composable(Routes.AUTH) {
                            AuthScreen(onNavigate = {
                                navController.navigate(it.route){
                                    launchSingleTop = true
                                }
                            })

                        }
                        composable(Routes.HOME) {
                            HomeScreen()
                        }
                        composable(Routes.HOME_CUSTOMER) {
                            CustomerHomeScreen(onNavigate = {
                                navController.navigate(it.route)
                            })
                        }
                        composable(Routes.HOME_DRIVER) {
                            DriverHomeScreen()
                        }
                        composable(Routes.HOME_MANAGER) {
                            ManagerHomeScreen()
                        }
                        composable(Routes.PROMO) {
                            PromoScreen()
                        }
                    }

                }
            }
        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    AtmaJayaRentalTheme {
        Greeting("Android")
    }
}