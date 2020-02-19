package al.bruno.fruit.diary.ui.main

import al.bruno.fruit.diary.ui.about.AboutFragment
import al.bruno.fruit.diary.ui.add.AddFruitDialog
import al.bruno.fruit.diary.ui.add.AddFruitFragment
import al.bruno.fruit.diary.ui.add.AddFruitViewModel
import al.bruno.fruit.diary.ui.detail.DetailFragment
import al.bruno.fruit.diary.ui.my.diary.MyDiaryFragment
import al.bruno.fruit.diary.ui.my.diary.MyDiaryViewModel
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class MainFragmentBuildersModule {
    @ContributesAndroidInjector
    internal abstract fun contributeMyDiaryViewModel(): MyDiaryFragment
    @ContributesAndroidInjector
    internal abstract fun contributeDetailFragment(): DetailFragment
    @ContributesAndroidInjector
    internal abstract fun contributeAboutFragment(): AboutFragment
    @ContributesAndroidInjector
    internal abstract fun contributeAddFruitFragment(): AddFruitFragment
    @ContributesAndroidInjector
    internal abstract fun contributeAddFruitDialog(): AddFruitDialog
}