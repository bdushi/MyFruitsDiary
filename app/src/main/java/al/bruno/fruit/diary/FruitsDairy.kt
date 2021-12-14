package al.bruno.fruit.diary

import al.bruno.fruit.diary.di.DaggerAppComponent
import android.app.Application
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import javax.inject.Inject

open class FruitsDairy : Application(), HasAndroidInjector {
    @Inject
    lateinit var activityInjector: DispatchingAndroidInjector<Any>

    override fun onCreate() {
        super.onCreate()
        return DaggerAppComponent
            .factory()
            .application(this)
            .inject(this)

    }
    override fun androidInjector(): AndroidInjector<Any> {
        return activityInjector
    }
}