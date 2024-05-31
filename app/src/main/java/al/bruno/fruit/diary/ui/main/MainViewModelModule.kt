package al.bruno.fruit.diary.ui.main

import al.bruno.fruit.diary.factory.ViewModelKey
import al.bruno.fruit.diary.factory.ViewModelProviderFactory
import al.bruno.fruit.diary.ui.about.AboutViewModel
import al.bruno.fruit.diary.ui.add.AddFruitViewModel
import al.bruno.fruit.diary.ui.detail.DetailViewModel
import al.bruno.fruit.diary.ui.my.diary.MyDiaryViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class MainViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(value =  MyDiaryViewModel::class)
    abstract fun bindMyDiaryViewModel(myDiaryViewModel: MyDiaryViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(value =  MainViewModel::class)
    abstract fun bindMainViewModel(myDiaryViewModel: MainViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(value =  DetailViewModel::class)
    abstract fun bindDetailViewModel(detailViewModel: DetailViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(value =  AboutViewModel::class)
    abstract fun bindAboutViewModel(aboutViewModel: AboutViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(value =  AddFruitViewModel::class)
    abstract fun bindAddFruitViewModel(aboutViewModel: AddFruitViewModel): ViewModel

    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelProviderFactory): ViewModelProvider.Factory
}