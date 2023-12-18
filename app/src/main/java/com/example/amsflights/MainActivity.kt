package com.example.amsflights

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActionScope
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.amsflights.model.Planet
import com.example.amsflights.ui.theme.AMSFlightsTheme
import com.example.amsflights.vm.PlanetViewModel


class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalComposeUiApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AMSFlightsTheme {

                Column(
                    modifier = Modifier.fillMaxSize(),
                ) {
                    val context = LocalContext.current.applicationContext
                    val viewModel: PlanetViewModel = viewModel()
                    var filterText by remember {
                        mutableStateOf<String?>(null)
                    }
                    val planets by viewModel.planetsUiState.collectAsState()
                    val planet by viewModel.planetUiState.collectAsState()
                    val keyboard = LocalSoftwareKeyboardController.current
                    var tempValue by remember {
                        mutableStateOf("")
                    }
                    var isNeedToShow by remember {
                        mutableStateOf(false)
                    }
                    fun onSearch() {
                        filterText = tempValue
                        viewModel.getAllPlanets(filterText, context)
                        keyboard?.hide()
                    }
                    fun onClick(id: String){
                        viewModel.getPlanet(id)
                        isNeedToShow = true
                    }

                    LaunchedEffect(filterText) {
                        viewModel.getAllPlanets(filterText, context)
                    }

                    if (isNeedToShow && planet!=null) {
                        PlanetCard(planet = planet!!, isFull = true)
                        BackHandler {
                            viewModel.getAllPlanets(context = context)
                            isNeedToShow = false
                            viewModel.getPlanet((-1).toString())
                        }
                    } else {
                        InputField(
                            text = tempValue,
                            onValueChange = { tempValue = it },
                            label = "Поиск",
                            onSearch = {
                                onSearch()
                                keyboard?.hide()
                            }
                        )
                        PlanetsScreen(planets = planets, onClick = ::onClick)
                    }
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    AMSFlightsTheme {
        Greeting("Android")
    }
}

@Composable
fun PlanetCard(
    planet: Planet,
    modifier: Modifier = Modifier,
    backHandle: () -> Unit = {},
    isFull: Boolean = false
) {
    BackHandler {
        backHandle()
    }
    Column(
        verticalArrangement = Arrangement.spacedBy(0.dp, Alignment.Top),
        horizontalAlignment = Alignment.Start,
        modifier = modifier
            .padding(top = 12.dp)
            .fillMaxWidth()
            .fillMaxHeight(0.98F)
            .shadow(
                elevation = 200.dp,
                ambientColor = Color(0x14000000)
            )
            .padding(start = 16.dp, end = 16.dp, bottom = 16.dp)
            .background(color = Color(0xFFFFFFFF), shape = RoundedCornerShape(size = 16.dp))
    ) {
        AsyncImage(
            model = planet.image,
            contentDescription = "image description",
            contentScale = ContentScale.FillBounds,
            modifier = Modifier
                .padding(top = 12.dp)
                .width(328.dp)
                .height(301.dp)
                .clip(RoundedCornerShape(30.dp))
        )
        Text(
            text = planet.name,
            style = TextStyle(
                fontSize = 15.sp,
                lineHeight = 20.sp,
                fontWeight = FontWeight(400),
                color = Color(0xFF000000),
                letterSpacing = 0.2.sp,
            ),
            modifier = Modifier.padding(bottom = 16.dp).align(Alignment.CenterHorizontally)
        )
        if (isFull) {
            Text(
                text = "Радиус = ${planet.radius}",
                style = TextStyle(
                    fontSize = 15.sp,
                    lineHeight = 20.sp,
                    fontWeight = FontWeight(600),
                    color = Color(0xFF818C99),
                    letterSpacing = 0.3.sp,
                ),
                modifier = Modifier.padding(start = 16.dp)
            )
            Text(
                text = "Гравитация = ${planet.gravity}",
                style = TextStyle(
                    fontSize = 15.sp,
                    lineHeight = 20.sp,
                    fontWeight = FontWeight(600),
                    color = Color(0xFF818C99),
                    letterSpacing = 0.3.sp,
                ),
                modifier = Modifier.padding(start = 16.dp)
            )
            Text(
                text = "Тип = ${planet.type}",
                style = TextStyle(
                    fontSize = 15.sp,
                    lineHeight = 20.sp,
                    fontWeight = FontWeight(600),
                    color = Color(0xFF818C99),
                    letterSpacing = 0.3.sp,
                ),
                modifier = Modifier.padding(start = 16.dp)
            )
            Text(
                text = "Описание: ${planet.description}",
                style = TextStyle(
                    fontSize = 15.sp,
                    lineHeight = 20.sp,
                    fontWeight = FontWeight(600),
                    color = Color(0xFF818C99),
                    letterSpacing = 0.3.sp,
                ),
                modifier = Modifier.padding(start = 16.dp)
            )
        }
    }
}
@Composable
fun PlanetsScreen(
    planets: List<Planet>,
    onClick: (String) -> Unit
) {
    LazyColumn {
        items(planets, key = {it.hashCode()}) {
            PlanetCard(
                planet = it, modifier = Modifier.clickable(
                    interactionSource = MutableInteractionSource(),
                    indication = null,
                    onClick = { onClick(it.id.toString()) },
                )
            )
        }
    }
}

@Composable
fun InputField(
    modifier: Modifier = Modifier,
    label: String? = null,
    text: String = "",
    onValueChange: (String) -> Unit,
    onSearch: (KeyboardActionScope.() -> Unit)
) {
    Column {
        if (label != null) {
            Text(
                text = label,
                style = TextStyle(
                    fontSize = 14.sp,
                    lineHeight = 18.sp,
                    fontFamily = FontFamily(Font(R.font.roboto_regular)),
                    fontWeight = FontWeight(400),
                    color = Color(0xFF6D7885),
                    letterSpacing = 0.2.sp,
                ),
                modifier = modifier.padding(start = 16.dp)
            )
        }
    }
    Row(
        horizontalArrangement = Arrangement.spacedBy(0.dp, Alignment.Start),
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .padding(start = 16.dp, bottom = 26.dp, top = 8.dp, end = 16.dp)
            .border(
                width = 0.5.dp,
                color = Color(0x1F000000),
                shape = RoundedCornerShape(size = 8.dp)
            )
            .height(44.dp)
            .background(color = Color(0xFFF2F3F5), shape = RoundedCornerShape(size = 8.dp))
            .padding(start = 12.dp, top = 12.dp, end = 12.dp, bottom = 12.dp)
    ) {
        BasicTextField(
            value = text,
            onValueChange = onValueChange,
            modifier = Modifier.fillMaxWidth(),
            textStyle = TextStyle(
                fontSize = 16.sp,
                lineHeight = 20.sp,
                fontFamily = FontFamily(Font(R.font.roboto_regular)),
                fontWeight = FontWeight(400),
                color = Color(0xFF303030),
                letterSpacing = 0.1.sp,
            ),
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
            keyboardActions = KeyboardActions(onSearch = onSearch)
        )
    }
}