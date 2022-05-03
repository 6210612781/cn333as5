package com.example.mycontactlist

import android.app.Application
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mycontactlist.routing.PhoneNumberRouter
import com.example.mycontactlist.routing.Screen
import com.example.mycontactlist.screens.ContactScreen
import com.example.mycontactlist.screens.VerifyScreen
import com.example.mycontactlist.screens.SaveContactScreen
import com.example.mycontactlist.screens.TrashScreen
import com.example.mycontactlist.ui.theme.MycontactlistTheme
import com.example.mycontactlist.ui.theme.MycontactlistThemeSettings
import com.example.mycontactlist.viewmodel.MainViewModel
import com.example.mycontactlist.viewmodel.MainViewModelFactory

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterialApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MycontactlistTheme(darkTheme = MycontactlistThemeSettings.isDarkThemeEnabled) {
                val viewModel: MainViewModel = viewModel(
                    factory = MainViewModelFactory(LocalContext.current.applicationContext as Application)
                )
                MainActivityScreen(viewModel)
            }
        }
    }
}


@ExperimentalMaterialApi
@Composable
fun MainActivityScreen(viewModel: MainViewModel) {
    Surface {
        when (PhoneNumberRouter.currentScreen) {
            is Screen.Contacts -> ContactScreen(viewModel)
            is Screen.SaveContact -> SaveContactScreen(viewModel)
            is Screen.Trash -> TrashScreen(viewModel)
            is Screen.Verify -> VerifyScreen(viewModel)
        }
    }
}