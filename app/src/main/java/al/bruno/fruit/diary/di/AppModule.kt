package al.bruno.fruit.diary.di

import al.bruno.fruit.diary.data.source.local.AppDatabase
import al.bruno.fruit.diary.data.source.local.dao.FruitDao
import al.bruno.fruit.diary.data.source.remote.service.EntriesService
import al.bruno.fruit.diary.data.source.remote.service.FruitService
import al.bruno.fruit.diary.util.ErrorHandler
import android.app.Application
import androidx.room.Room
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.Reusable
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
class AppModule {

    @Provides
    @Singleton
    fun providerRetrofit(
        okHttpClient: OkHttpClient,
        gsonConverterFactory: GsonConverterFactory
    ): Retrofit {
        return Retrofit
            .Builder()
            .baseUrl("https://fruitdiary.test.themobilelife.com/api/")
            .client(okHttpClient)
            .addConverterFactory(gsonConverterFactory)
            .build()
    }

    @Provides
    @Reusable
    fun okHttpClient(httpLoggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(httpLoggingInterceptor)
            .build()
    }

    @Provides
    @Reusable
    fun logging(): HttpLoggingInterceptor {
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY
        return logging
    }

    @Provides
    @Reusable
    fun converterFactory(): GsonConverterFactory {
        return GsonConverterFactory.create(
            GsonBuilder()
                .setDateFormat("yyyy-MM-dd")
                .create()
        )
    }

    @Provides
    @Singleton
    fun errorHandler(retrofit: Retrofit): ErrorHandler {
        return ErrorHandler(retrofit)
    }

    @Provides
    @Singleton
    fun fruitService(retrofit: Retrofit): FruitService {
        return retrofit.create(FruitService::class.java)
    }

    @Provides
    @Singleton
    fun entriesService(retrofit: Retrofit): EntriesService {
        return retrofit.create(EntriesService::class.java)
    }

    @Provides
    @Singleton
    fun providesDatabaseName(): String {
        return "my-dairy.db"
    }

    @Provides
    @Singleton
    fun providesDatabaseHelper(app: Application, name: String): AppDatabase {
        return Room
            .databaseBuilder(app, AppDatabase::class.java, name)
            .build()
    }

    @Singleton
    @Provides
    fun provideExpenseDao(appDatabase: AppDatabase): FruitDao {
        return appDatabase.eventDao()
    }
}