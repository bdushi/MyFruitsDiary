package al.bruno.fruit.diary.di

import al.bruno.fruit.diary.data.source.EntriesDataSource
import al.bruno.fruit.diary.data.source.FruitDataSource
import al.bruno.fruit.diary.data.source.local.FruitLocalDataSource
import al.bruno.fruit.diary.data.source.remote.EntriesRemoteDataSource
import al.bruno.fruit.diary.data.source.remote.FruitRemoteDataSource
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
abstract class DataSourceModule {
    @Singleton
    @Binds
    abstract fun provideFruitLocalDataSource(dataSource: FruitLocalDataSource): FruitDataSource

    @Singleton
    @Binds
    abstract fun provideFruitRemoteDataSource(dataSource: FruitRemoteDataSource): FruitDataSource

    @Singleton
    @Binds
    abstract fun provideEntriesRemoteDataSource(dataSource: EntriesRemoteDataSource): EntriesDataSource
}