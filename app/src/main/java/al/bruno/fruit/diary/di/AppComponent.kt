package al.bruno.fruit.diary.di

import al.bruno.fruit.diary.FruitsDairy
import android.app.Application
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules = [
    AndroidSupportInjectionModule::class,
    AppModule::class,
    DataSourceModule::class,
    ActivityBindingModule::class])
interface AppComponent : AndroidInjector<FruitsDairy> {
    @Component.Factory
    interface Factory {
        fun application(@BindsInstance application: Application): AppComponent
    }
}