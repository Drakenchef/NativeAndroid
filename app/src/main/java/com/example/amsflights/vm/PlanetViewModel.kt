package com.example.amsflights.vm

import android.annotation.SuppressLint
import android.content.Context
import android.health.connect.datatypes.units.Length
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.amsflights.data.PlanetRepositoryImpl
import com.example.amsflights.model.Planet
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class PlanetViewModel(private val planetRepositoryImpl: PlanetRepositoryImpl = PlanetRepositoryImpl()) :
    ViewModel() {
    private val _planetsUiState = MutableStateFlow(emptyList<Planet>())
    val planetsUiState = _planetsUiState.asStateFlow()
    private val _planetUiState = MutableStateFlow(emptyList<Planet>().randomOrNull())
    val planetUiState = _planetUiState.asStateFlow()
    @SuppressLint("ShowToast")
    fun getAllPlanets(search: String? = null, context: Context) {
        viewModelScope.launch(CoroutineExceptionHandler { _, exception ->
            exception.printStackTrace()
        }) {
            planetRepositoryImpl.getAllPlanets(search)
                .collect { response ->
                    _planetsUiState.value = response.planets
                    Toast.makeText(context, response.flightId.toString() , Toast.LENGTH_LONG)
                    Toast.makeText(context, response.planets.toString() , Toast.LENGTH_LONG)
                }
        }
    }

    fun getPlanet(id: String) {
        viewModelScope.launch(CoroutineExceptionHandler { _, exception ->
            exception.printStackTrace()
            _planetUiState.value = null
        }) {
            planetRepositoryImpl.getPlanet(id)
                .collect { response ->
                    _planetUiState.value = response.planets
                }
        }
    }

//    companion object {
//        private val planets = listOf<Planet>(
//            Planet(
//                id = 1,
//                name = "Сатурн",
//                description = "Сатурн - шестая планета Солнечной системы, наиболее известная благодаря своим кольцам из льда и камней, которые делают ее уникальной среди других планет. Сатурн также является газовым гигантом с многочисленными спутниками, включая крупнейший - Титан. Несмотря на то, что Сатурн находится на значительном расстоянии от Земли, его потрясающая красота и тайны привлекают учёных и астрономов.",
//                radius = 3389.5,
//                distance = 1200000000,
//                gravity = 107,
//                image = "http://172.18.0.6:9000/amsflights/saturn.jpg?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Credential=66V5TUU991OFSUBCI48Y%2F20231014%2Fus-east-1%2Fs3%2Faws4_request&X-Amz-Date=20231014T214233Z&X-Amz-Expires=604800&X-Amz-Security-Token=eyJhbGciOiJIUzUxMiIsInR5cCI6IkpXVCJ9.eyJhY2Nlc3NLZXkiOiI2NlY1VFVVOTkxT0ZTVUJDSTQ4WSIsImV4cCI6MTY5NzMyMzEwMCwicGFyZW50IjoibWluaW8ifQ.FtOVDZLuOj5NQllP9lei-HIwEYi409lWj3di4arS1_bC9j6snsftvdbXveqoY_XB_mCrvdqpHPutIxxqvCSsWw&X-Amz-SignedHeaders=host&versionId=12ab384c-8992-4535-b6fd-a19e8030838c&X-Amz-Signature=2ccdc4647f9c2cff661c062aa757d070f65225f0a0009928eac436c6ae2a5ca7",
//                type = "Планета",
//                isDeleted = false,
//            ),
//            Planet(
//                id = 2,
//                name = "Луна",
//                description = "Сатурн - шестая планета Солнечной системы, наиболее известная благодаря своим кольцам из льда и камней, которые делают ее уникальной среди других планет. Сатурн также является газовым гигантом с многочисленными спутниками, включая крупнейший - Титан. Несмотря на то, что Сатурн находится на значительном расстоянии от Земли, его потрясающая красота и тайны привлекают учёных и астрономов.",
//                radius = 3389.5,
//                distance = 1200000000,
//                gravity = 107,
//                image = "http://172.18.0.6:9000/amsflights/saturn.jpg?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Credential=66V5TUU991OFSUBCI48Y%2F20231014%2Fus-east-1%2Fs3%2Faws4_request&X-Amz-Date=20231014T214233Z&X-Amz-Expires=604800&X-Amz-Security-Token=eyJhbGciOiJIUzUxMiIsInR5cCI6IkpXVCJ9.eyJhY2Nlc3NLZXkiOiI2NlY1VFVVOTkxT0ZTVUJDSTQ4WSIsImV4cCI6MTY5NzMyMzEwMCwicGFyZW50IjoibWluaW8ifQ.FtOVDZLuOj5NQllP9lei-HIwEYi409lWj3di4arS1_bC9j6snsftvdbXveqoY_XB_mCrvdqpHPutIxxqvCSsWw&X-Amz-SignedHeaders=host&versionId=12ab384c-8992-4535-b6fd-a19e8030838c&X-Amz-Signature=2ccdc4647f9c2cff661c062aa757d070f65225f0a0009928eac436c6ae2a5ca7",
//                type = "Планета",
//                isDeleted = false,
//            ),
//            Planet(
//                id = 3,
//                name = "Марс",
//                description = "Сатурн - шестая планета Солнечной системы, наиболее известная благодаря своим кольцам из льда и камней, которые делают ее уникальной среди других планет. Сатурн также является газовым гигантом с многочисленными спутниками, включая крупнейший - Титан. Несмотря на то, что Сатурн находится на значительном расстоянии от Земли, его потрясающая красота и тайны привлекают учёных и астрономов.",
//                radius = 3389.5,
//                distance = 1200000000,
//                gravity = 107,
//                image = "http://172.18.0.6:9000/amsflights/saturn.jpg?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Credential=66V5TUU991OFSUBCI48Y%2F20231014%2Fus-east-1%2Fs3%2Faws4_request&X-Amz-Date=20231014T214233Z&X-Amz-Expires=604800&X-Amz-Security-Token=eyJhbGciOiJIUzUxMiIsInR5cCI6IkpXVCJ9.eyJhY2Nlc3NLZXkiOiI2NlY1VFVVOTkxT0ZTVUJDSTQ4WSIsImV4cCI6MTY5NzMyMzEwMCwicGFyZW50IjoibWluaW8ifQ.FtOVDZLuOj5NQllP9lei-HIwEYi409lWj3di4arS1_bC9j6snsftvdbXveqoY_XB_mCrvdqpHPutIxxqvCSsWw&X-Amz-SignedHeaders=host&versionId=12ab384c-8992-4535-b6fd-a19e8030838c&X-Amz-Signature=2ccdc4647f9c2cff661c062aa757d070f65225f0a0009928eac436c6ae2a5ca7",
//                type = "Планета",
//                isDeleted = false,
//            )
//        )
//    }
}