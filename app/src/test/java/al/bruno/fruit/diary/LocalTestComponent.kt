package al.bruno.fruit.diary

import al.bruno.fruit.diary.data.source.local.EntriesRemoteDataSourceTest
import al.bruno.fruit.diary.di.FakeDataSourceModule
import al.bruno.fruit.diary.di.FakeModule
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [
    FakeModule::class,
    FakeDataSourceModule::class,
])
interface LocalTestComponent {
    @Component.Factory
    interface Factory {
        fun create(@BindsInstance appTest: EntriesRemoteDataSourceTest): LocalTestComponent
    }
    fun inject(appTest: EntriesRemoteDataSourceTest)
}