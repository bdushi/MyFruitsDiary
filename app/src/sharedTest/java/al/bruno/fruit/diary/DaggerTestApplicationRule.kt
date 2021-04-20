package al.bruno.fruit.diary

import al.bruno.fruit.diary.di.TestComponent
import al.bruno.fruit.diary.di.DaggerTestComponent
import android.content.Context
import androidx.test.core.app.ApplicationProvider
import org.junit.rules.TestWatcher
import org.junit.runner.Description

/**
 * JUnit rule that creates a [TestComponent] and injects the [TestMyFruitApplication].
 *
 * Note that the `testInstrumentationRunner` property needs to point to [CustomTestRunner].
 */

class DaggerTestApplicationRule : TestWatcher() {

    lateinit var component: TestComponent
        private set

    override fun starting(description: Description?) {
        super.starting(description)

        val app = ApplicationProvider.getApplicationContext<Context>() as TestMyFruitApplication
        component = DaggerTestComponent.factory().create(app)
        component.inject(app)
    }
}