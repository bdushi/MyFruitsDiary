package al.bruno.fruit.diary.di

import al.bruno.fruit.diary.FruitsDairy
import al.bruno.fruit.diary.TestMyFruitApplication
import al.bruno.fruit.diary.data.source.EntriesRepository
import al.bruno.fruit.diary.data.source.FruitRepository
import android.app.Application
import dagger.BindsInstance
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

/**
 * https://dagger.dev/dev-guide/testing.html
 *
 * https://developer.android.com/training/testing/junit-runner
 */

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 *
 * https://www.raywenderlich.com/books/kotlin-coroutines-by-tutorials/v2.0/chapters/15-testing-coroutines
 *
 * https://github.com/Kotlin/kotlinx.coroutines/issues/2023
 *
 * https://proandroiddev.com/sharing-code-between-local-and-instrumentation-tests-c0b57ebd3200
 */

/**
 *
 * https://developer.android.com/codelabs/advanced-android-kotlin-training-testing-basics#12
 *
 * https://developer.android.com/codelabs/advanced-android-kotlin-training-testing-test-doubles/#11
 */

@Singleton
@Component(modules = [
    AndroidSupportInjectionModule::class,
    FakeModule::class,
    FakeDataSourceModule::class,
])
interface TestComponent : AppComponent {
    @Component.Factory
    interface Factory {
        fun create(@BindsInstance application: Application): TestComponent
    }
    val entriesRepository: EntriesRepository
    val fruitRepository: FruitRepository
}