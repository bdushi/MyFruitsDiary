package al.bruno.fruit.diary.data.source.local

import al.bruno.fruit.diary.data.source.local.dao.FruitDao
import al.bruno.fruit.diary.model.Fruit
import al.bruno.fruit.diary.util.Converters
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [Fruit::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun eventDao(): FruitDao
}