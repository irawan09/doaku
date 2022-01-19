package irawan.electroshock.doaku.view.widget.onBoarding

import com.airbnb.lottie.compose.LottieCompositionSpec
import irawan.electroshock.doaku.R

data class Page(val title: String , val description : String,
                val image: LottieCompositionSpec)

val onboardPages = listOf(
    Page(
        "Ready To Travel",
        "Choose the destination, plan your trip , enjoy your holidays",
        LottieCompositionSpec.RawRes(R.raw.indonesia)
    ),
    Page(
        "Select The Date",
        "Choose the destination, plan your trip , enjoy your holidays",
        LottieCompositionSpec.RawRes(R.raw.mosque_green)
    ),
    Page(
        "Feels Like Home",
        "Choose the destination, plan your trip , enjoy your holidays",
        LottieCompositionSpec.RawRes(R.raw.moon_lantern)
    )

)