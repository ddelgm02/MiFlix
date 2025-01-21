package com.fnd.miflix.views

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun HomeScreen() {
    Scaffold(bottomBar = {
        //BottomNavigationBar()
    }

    ){ padding ->
        Column(
            modifier = Modifier.fillMaxSize().padding(padding)
        ) {
            //FavoriteSection()
            //TrendingSection()
            Spacer(modifier = Modifier.height(16.dp))
            //NewReleaseSection()
        }

    }
}