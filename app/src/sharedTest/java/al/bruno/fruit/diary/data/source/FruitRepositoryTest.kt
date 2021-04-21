package al.bruno.fruit.diary.data.source

/**
 * https://developer.android.com/studio/test
 */

import al.bruno.fruit.diary.DaggerTestApplicationRule
import al.bruno.fruit.diary.MainCoroutineRule
import al.bruno.fruit.diary.data.source.local.AppDatabase
import al.bruno.fruit.diary.model.Fruit
import androidx.arch.core.executor.testing.InstantTaskExecutorRule

import androidx.test.ext.junit.runners.AndroidJUnit4
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.runBlocking

import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import javax.inject.Inject

@RunWith(AndroidJUnit4::class)
@ExperimentalCoroutinesApi
class FruitRepositoryTest {
    @set:Inject lateinit var fruitRepository: FruitRepository
    // Set the main coroutines dispatcher for unit testing.
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    /**
     * Sets up Dagger components for testing.
     */
    @get:Rule
    val rule = DaggerTestApplicationRule()

    /**
     * Gets a reference to the [EntriesRepository] exposed by the [DaggerTestApplicationRule].
     */
    @Before
    fun setupDaggerComponent() {
        fruitRepository = rule.component.fruitRepository
    }

    // Executes each task synchronously using Architecture Components.
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Test
    fun fruitRemote() = runBlocking {
        assertTrue(fruitRepository.fruitRemote().isSuccessful)
    }

    @Test
    fun insert() = runBlocking {
        val fruit = Fruit(
            1,
            "Apple",
            250,
            "https://fruitdiary.test.themobilelife.com/api/fruit/images/apple.png",
            1
        )
        fruitRepository.insert(
            listOf(
                fruit
            )
        )
        fruitRepository.fruits().collect {
            assertTrue(it.indexOf(fruit) == 0)
        }
    }

    @Test
    fun fruits() = runBlocking {
        fruitRepository.fruits().collect {
            assertTrue(it.isNotEmpty())
        }
    }
}