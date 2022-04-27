package com.example.atmajayarental

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.atmajayarental.data.api.model.Promo
import com.example.atmajayarental.ui.auth.AuthScreen
import com.example.atmajayarental.ui.home.HomeScreen
import com.example.atmajayarental.ui.promo.PromoScreen
import com.example.atmajayarental.ui.theme.AtmaJayaRentalTheme
import com.example.atmajayarental.util.Routes
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AtmaJayaRentalTheme {
                val navController = rememberNavController()
                // A surface container using the 'background' color from the theme
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.background) {

                    NavHost(navController = navController, startDestination = Routes.AUTH){
                        composable(Routes.AUTH) {
                            AuthScreen(onNavigate = {
                                navController.navigate(it.route)
                            })
                        }
                        composable(Routes.HOME) {
                            HomeScreen()
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