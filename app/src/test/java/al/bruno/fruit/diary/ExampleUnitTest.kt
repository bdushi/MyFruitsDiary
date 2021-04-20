package al.bruno.fruit.diary

import al.bruno.fruit.diary.data.source.EntriesRepository
import al.bruno.fruit.diary.DaggerLocalTestComponent
import al.bruno.fruit.diary.data.source.FruitRepository
import al.bruno.fruit.diary.model.Entries
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import javax.inject.Inject

@ExperimentalCoroutinesApi
@RunWith(JUnit4::class)
class ExampleUnitTest {

    @set:Inject
    lateinit var entriesRepository: EntriesRepository

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @Before
    fun setupDaggerComponent() {
        DaggerLocalTestComponent.factory().create(this).inject(this)
    }

    @Test
    fun todo() = runBlocking {
        assertTrue(entriesRepository.entries().body() is List<Entries>)
    }

    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }
}
