package irawan.electroshock.doaku.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import irawan.electroshock.doaku.model.DatabaseModel

@Database(entities = [DatabaseModel::class], version = 1, exportSchema = false)
abstract class DoaDatabaseFactory : RoomDatabase() {
    abstract fun doaDao() : DoaDao

    companion object {
        private var INSTANCE: DoaDatabaseFactory? = null

        fun getDatabaseInstance(context: Context): DoaDatabaseFactory {
            if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(
                    context,
                    DoaDatabaseFactory::class.java,
                    "roomdb"
                ).build()
            }
            return INSTANCE as DoaDatabaseFactory
        }
    }
}