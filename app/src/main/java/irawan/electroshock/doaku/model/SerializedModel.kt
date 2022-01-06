package irawan.electroshock.doaku.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class SerializedModel(

    @SerializedName("id")
    @Expose
    val id : String?,

    @SerializedName("doa")
    @Expose
    val doa : String?,

    @SerializedName("ayat")
    @Expose
    val ayat : String?,

    @SerializedName("latin")
    @Expose
    val latin : String?,

    @SerializedName("artinya")
    @Expose
    val artinya : String?
)
