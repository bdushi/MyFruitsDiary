package al.bruno.fruit.diary

import android.app.Application
import android.content.Context
import androidx.test.runner.AndroidJUnitRunner

/**
 * A custom [AndroidJUnitRunner] used to replace the application used in tests with a
 * [FruitsDairy].
 */

class CustomTestRunner : AndroidJUnitRunner() {
    override fun newApplication(cl: ClassLoader?, name: String?, context: Context?): Application {
        return super.newApplication(cl, TestMyFruitApplication::class.java.name, context)
    }
}