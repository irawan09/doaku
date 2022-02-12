package irawan.electroshock.doaku.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class JSONObject(
    @SerializedName("data")
    @Expose
    val data : List<SerializedModel>
)

data class SerializedModel(

    @SerializedName("id_doa")
    @Expose
    val id : String?,

    @SerializedName("nama")
    @Expose
    val doa : String?,

    @SerializedName("lafal")
    @Expose
    val ayat : String?,

    @SerializedName("transliterasi")
    @Expose
    val latin : String?,

    @SerializedName("arti")
    @Expose
    val artinya : String?
)
