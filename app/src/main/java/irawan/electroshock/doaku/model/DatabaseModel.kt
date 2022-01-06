package irawan.electroshock.doaku.model

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "doa_table")
data class DatabaseModel(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "id")
    @NonNull
    var id : String = "N/A",

    @ColumnInfo(name = "doa")
    @NonNull
    var doa : String = "N/A",

    @ColumnInfo(name = "ayat")
    @NonNull
    var ayat : String = "N/A",

    @ColumnInfo(name = "latin")
    @NonNull
    var latin : String = "N/A",

    @ColumnInfo(name = "artinya")
    @NonNull
    var artinya : String = "N/A"
)
