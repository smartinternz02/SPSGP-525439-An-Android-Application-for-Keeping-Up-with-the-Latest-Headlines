package com.example.newswiz

import android.annotation.SuppressLint
import android.content.ClipDescription
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.newswiz.api.Article
import kotlinx.coroutines.delay
import androidx.activity.viewModels
import androidx.compose.animation.core.*
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.List
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Canvas
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import coil.compose.AsyncImage
import com.example.newswiz.ui.theme.mon_bold
import com.example.newswiz.ui.theme.mon_medium
import com.example.newswiz.ui.theme.mon_regular
import com.example.newswiz.ui.theme.ws_bold
import com.example.newswiz.ui.theme.ws_medium



class MainPage : ComponentActivity() {
    val mainview by viewModels<MainViewModel>()
    var Articles = mutableListOf<Article>()
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        var cat = intent.getStringExtra("Category")
        var category = "general"
        if(cat!=null)
        {
            category = cat
        }
        Log.d("MainActivity","$category")
        //makeAPIRequest()
        super.onCreate(savedInstanceState)
        //makeAPIRequest()
        setContent {
            var isLoading by remember { mutableStateOf(true) }
            LaunchedEffect(category)
            {
                Articles = mainview.makeAPIRequest(category).await()
                isLoading = false
            }
            if(isLoading)
            {
                LoadingScreen()
            }
            else
            {
                Main_Screen(Articles = Articles,category)
            }
            }
    }

    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun Main_Screen(Articles:List<Article>, category:String){
        val items = listOf<MinFabItem>(
            MinFabItem(
                label = "business"
            ),
            MinFabItem(
                label = "entertainment"
            ),
            MinFabItem(
                label = "general"
            ),
            MinFabItem(
                label = "health"
            ),
            MinFabItem(
                label = "science"
            ),
            MinFabItem(
                label = "sports"
            ),
            MinFabItem(
                label = "technology"
            )
        )
        val start = LocalContext.current
        var multiFloatingState by remember {
            mutableStateOf(MultiFloatingState.Collapsed)
        }
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            containerColor = Color(0xFF212121),
            topBar = @Composable(){
                CenterAlignedTopAppBar(
                    modifier = Modifier.height(80.dp),
                    title = {
                        Box(
                            Modifier.fillMaxSize(), contentAlignment = Alignment.Center
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Center,
                                modifier = Modifier.fillMaxSize()
                            ) {
                                Image(painter = painterResource(id = R.drawable.nw_logo), contentDescription = "", modifier = Modifier.height(50.dp))
                                Spacer(modifier = Modifier.width(5.dp))
                                Text(
                                    "NewsWiz", style = TextStyle(
                                        fontFamily = mon_bold,
                                        fontSize = 25.sp,
                                        color = Color(0xFF212121)
                                    )
                                )
                            }
                        }
                    },
                    colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                        containerColor = Color(0xFFFFC116)
                    ),
                )
            },
            content = @Composable(){
                if(Articles.isEmpty())
                {
                    Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center, modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 30.dp, vertical = 50.dp)) {
                        Text("Internet Connectivity problem, check your own internet connection and restart the app",
                            modifier = Modifier.fillMaxWidth(), style = TextStyle(
                                fontFamily = mon_medium,
                                fontSize = 20.sp,
                                color = Color.White))
                    }
                }
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Top,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 30.dp, vertical = 50.dp)) {
                    Spacer(modifier = Modifier.height(50.dp))
                    Text("${category.uppercase()}", textAlign = TextAlign.Start, modifier = Modifier.fillMaxWidth(), style = TextStyle(
                        fontFamily = mon_medium,
                        fontSize = 20.sp,
                        color = Color.White
                    )
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    LazyColumn(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Top) {
                        items(Articles){
                            Article->Listile(url = Article.urlToImage, title = Article.title, Article.description)
                        }
                    }
                }
            },
            floatingActionButton = {
                MultiFloatingButton(start = start, multiFloatingState = multiFloatingState, onMultiFabStateChange = {
                    multiFloatingState = it
                },
                items = items)
            }
        )
    }
    @Composable
    fun MultiFloatingButton(
        start:Context,
        multiFloatingState: MultiFloatingState,
        onMultiFabStateChange: (MultiFloatingState) -> Unit,
        items:List<MinFabItem>
    ){
        val transition = updateTransition(targetState = multiFloatingState,label = "transition")

        val rotate by transition.animateFloat(label="rotate"){
            if(it==MultiFloatingState.Expanded)90f else 0f
        }
        
        val fabScale by transition.animateFloat(label = "FabScale") {
            if(it==MultiFloatingState.Expanded)36f else 0f
        }
        
        val alpha by transition.animateFloat(label = "alpha", transitionSpec = { tween(durationMillis = 50)
        }) {
            if(it==MultiFloatingState.Expanded)1f else 0f
        }
        val textShadow by transition.animateDp(label = "textShadow", transitionSpec = { tween(durationMillis = 50)
        }) {
            if(it==MultiFloatingState.Expanded)2.dp else 0.dp
        }
        Column(
            horizontalAlignment = Alignment.End
        ) {
            if(transition.currentState==MultiFloatingState.Expanded){
                items.forEach{
                    MinFab(item = it,
                        onMinFabItemClick = {
                            val intent = Intent(start, MainPage::class.java)
                            intent.putExtra("Category","${it.label}")
                            start.startActivity(intent)
                        },
                    alpha = alpha,
                    textShadow = textShadow,
                    fabScale = fabScale)
                    Spacer(modifier = Modifier.size(16.dp))
                }
            }
            FloatingActionButton(onClick = {
                onMultiFabStateChange(
                    if(transition.currentState==MultiFloatingState.Expanded){
                        MultiFloatingState.Collapsed
                    }
                    else
                    {
                        MultiFloatingState.Expanded
                    }
                )
            },
                shape = CircleShape,
                containerColor = Color(0xFFFFC116)) {
                Icon(painter = painterResource(R.drawable.baseline_filter_list_24),"", modifier = Modifier.rotate(rotate))
            }
        }
    }


    @Composable
    fun Listile(url:String , title:String, description: String){
        Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
            val showDesc = remember { mutableStateOf(false) }
            Card( colors = CardDefaults.cardColors(
                containerColor = Color.Black
            ), modifier = Modifier
                .padding(top = 18.dp, bottom = 10.dp)
                .fillMaxWidth()
            ) {
                AsyncImage(
                    model = "$url",
                    contentDescription = "",
                    modifier = Modifier.fillMaxWidth()
                )
            }
                    Text(
                        text = "$title",
                        style = TextStyle(
                            fontFamily = ws_medium,
                            fontSize = 20.sp,
                            color = Color.White
                        ),
                        modifier = Modifier
                            .padding(vertical = 10.dp)
                            .fillMaxWidth(),
                        textAlign = TextAlign.Start
                    )
            if(!showDesc.value) {
                Text(
                    text = "Know More",
                    style = TextStyle(
                        fontFamily = ws_bold,
                        fontSize = 18.sp,
                        color = Color(0xFFFFC116),
                        textDecoration = TextDecoration.Underline
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 5.dp, vertical = 5.dp)
                        .clickable {
                            showDesc.value = true
                        },
                    textAlign = TextAlign.End
                )
            }
            if(showDesc.value)
            {
                Text(text = "$description",
                    style = TextStyle(
                        fontFamily = ws_medium,
                        fontSize = 15.sp,
                        color = Color.White,
                    ),
                    textAlign = TextAlign.Start
                )
                Spacer(modifier = Modifier.height(5.dp))
                Text(text = "Close",
                    style = TextStyle(
                        fontFamily = ws_medium,
                        fontSize = 18.sp,
                        color = Color(0xFFFFC116),
                        textDecoration = TextDecoration.Underline
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 5.dp, vertical = 5.dp)
                        .clickable {
                            showDesc.value = false
                        },
                    textAlign = TextAlign.Center)
            }
            Spacer(Modifier.height(5.dp))
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(color = Color.Gray)
                    .clip(RoundedCornerShape(4.dp))
            )
            }
        }
    }

    enum class MultiFloatingState{
        Expanded,
        Collapsed
    }

    class MinFabItem(
        val label:String,
    )

    @Composable
    fun MinFab(
        item:MinFabItem,
        alpha: Float,
        textShadow: Dp,
        fabScale:Float,
        showLabel:Boolean = true,
        onMinFabItemClick:(MinFabItem)->Unit
    ){
            Card(
                shape = RoundedCornerShape(5.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color(0xFFFFC116)
                ),
                modifier = Modifier
                    .alpha(
                        animateFloatAsState(
                            targetValue = alpha,
                            animationSpec = tween(50)
                        ).value
                    )
                    .shadow(textShadow)
                    .clickable(
                        interactionSource = MutableInteractionSource(),
                        onClick = {
                            onMinFabItemClick.invoke(item)
                        },
                        indication = rememberRipple(
                            bounded = false,
                            radius = 20.dp,
                            color = Color(0xFFFFC116)
                        )
                    )
                    .width(150.dp),


            ) {
                Text(
                    text = item.label,
                    textAlign = TextAlign.Center,
                    fontSize = 15.sp,
                    fontFamily = mon_bold,
                    modifier = Modifier
                        .padding(all = 10.dp)
                        .fillMaxWidth(),
                    )
            }
    }

    @Composable
    fun LoadingScreen(){
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            LoadingAnimation2()
        }
    }

    @Composable
    fun LoadingAnimation2(
        circleColor: Color = Color.Magenta,
        animationDelay: Int = 1500
    ) {

        // 3 circles
        val circles = listOf(
            remember {
                Animatable(initialValue = 0f)
            },
            remember {
                Animatable(initialValue = 0f)
            },
            remember {
                Animatable(initialValue = 0f)
            }
        )

        circles.forEachIndexed { index, animatable ->
            LaunchedEffect(Unit) {
                // Use coroutine delay to sync animations
                // divide the animation delay by number of circles
                delay(timeMillis = (animationDelay / 3L) * (index + 1))

                animatable.animateTo(
                    targetValue = 1f,
                    animationSpec = infiniteRepeatable(
                        animation = tween(
                            durationMillis = animationDelay,
                            easing = LinearEasing
                        ),
                        repeatMode = RepeatMode.Restart
                    )
                )
            }
        }

        // outer circle
        Box(
            modifier = Modifier
                .size(size = 200.dp)
                .background(color = Color.Transparent)
        ) {
            // animating circles
            circles.forEachIndexed { index, animatable ->
                Box(
                    modifier = Modifier
                        .scale(scale = animatable.value)
                        .size(size = 200.dp)
                        .clip(shape = CircleShape)
                        .background(
                            color = Color.Black
                                .copy(alpha = (1 - animatable.value))
                        )
                ) {
                }
            }
        }
    }

