package irawan.electroshock.doaku.view.widget.onBoarding

import com.airbnb.lottie.compose.LottieCompositionSpec
import irawan.electroshock.doaku.R

data class Page(val title: String , val description : String,
                val image: LottieCompositionSpec)

val onboardPages = listOf(
    Page(
        "Fitur Offline",
        "Aplikasi ini dapat dijalankan ketika internet mati",
        LottieCompositionSpec.RawRes(R.raw.database)
    ),
    Page(
        "Fleksibilitas",
        "Aplikasi dapat diakses dengan mudah, dimana dan kapan saja",
        LottieCompositionSpec.RawRes(R.raw.mosque_green)
    ),
    Page(
        "Kelengkapan",
        "Aplikasi menyediakan doa-doa untuk kehidupan sehari-hari",
        LottieCompositionSpec.RawRes(R.raw.moon_lantern)
    )

)