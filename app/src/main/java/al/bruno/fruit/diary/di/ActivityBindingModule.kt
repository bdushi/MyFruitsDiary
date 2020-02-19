package al.bruno.fruit.diary.di

import al.bruno.fruit.diary.ui.main.MainActivity
import al.bruno.fruit.diary.ui.main.MainFragmentBuildersModule
import al.bruno.fruit.diary.ui.main.MainViewModelModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBindingModule {
    @ContributesAndroidInjector(modules = [MainFragmentBuildersModule::class, MainViewModelModule::class])
    abstract fun mainActivity(): MainActivity
}