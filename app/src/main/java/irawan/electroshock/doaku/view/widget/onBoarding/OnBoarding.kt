package irawan.electroshock.doaku.view.widget.onBoarding

import irawan.electroshock.doaku.R
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.HorizontalPagerIndicator
import com.google.accompanist.pager.rememberPagerState
import irawan.electroshock.doaku.view.widget.Animation

@ExperimentalAnimationApi
@ExperimentalPagerApi
@Composable
fun OnboardingUi(
) {
    val pagerState = rememberPagerState(pageCount = 3)
    Column {
        Text(text = "Skip" , modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp)
            .clickable { })

        HorizontalPager(state = pagerState , modifier = Modifier
            .fillMaxSize()
            .weight(1f)) { page ->
            PageUi(page = onboardPages[page])
        }

        HorizontalPagerIndicator(pagerState = pagerState, modifier = Modifier
            .align(Alignment.CenterHorizontally)
            .padding(16.dp),
            activeColor = Color.Blue)

        AnimatedVisibility(visible = pagerState.currentPage == 2) {
            OutlinedButton(shape = RoundedCornerShape(20.dp), modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
                onClick = {},
                colors = ButtonDefaults.outlinedButtonColors(
                    backgroundColor =
                    colorResource(id = R.color.purple_500),
                    colorResource(id = R.color.white))) {
                Text(text = "Let's Start")
            }

        }
    }
}

@Composable
fun PageUi(page: Page){
    Animation(source = page.image, size = 350)
    Column(horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(top = 270.dp)) {
        Spacer(modifier = Modifier.height(20.dp))
        Text(text = page.title,
            fontSize = 16.sp , fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = page.description,
            fontSize = 14.sp , textAlign = TextAlign.Center , modifier = Modifier.padding(start = 10.dp , end = 10.dp))
        Spacer(modifier = Modifier.height(12.dp))

    }
}
