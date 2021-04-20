package al.bruno.fruit.diary

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
        fun create(@BindsInstance appTest: ExampleUnitTest): LocalTestComponent
    }
    fun inject(appTest: ExampleUnitTest)
}