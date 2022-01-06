package irawan.electroshock.doaku.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import irawan.electroshock.doaku.model.DatabaseModel

@Dao
interface DoaDao {

    @Query("SELECT * FROM doa_table")
    fun getAllDoa(): LiveData<List<DatabaseModel>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertDoa(databaseModel: DatabaseModel)

    @Query("DELETE FROM doa_table")
    suspend fun deleteAll()
}