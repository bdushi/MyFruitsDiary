package al.bruno.fruit.diary

import android.app.Application
import android.content.Context
import androidx.test.runner.AndroidJUnitRunner

/**
 * A custom [AndroidJUnitRunner] used to replace the application used in tests with a
 * [TestMyFruitApplication].
 */

class CustomTestRunner : AndroidJUnitRunner() {
    override fun newApplication(classLoader: ClassLoader?, className: String?, context: Context?): Application {
        return super.newApplication(classLoader, TestMyFruitApplication::class.java.name, context)
    }
}