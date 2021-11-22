package al.bruno.fruit.diary.data.source

import al.bruno.fruit.diary.DaggerTestApplicationRule
import al.bruno.fruit.diary.MainCoroutineRule
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test

import org.junit.Rule
import org.junit.runner.RunWith
import javax.inject.Inject

@RunWith(AndroidJUnit4::class)
@ExperimentalCoroutinesApi
class EntriesRepositoryTest {

    @set:Inject
    lateinit var entriesRepository: EntriesRepository
    // Set the main coroutines dispatcher for unit testing.
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()
    @get:Rule
    val rule = DaggerTestApplicationRule()
    // Executes each task synchronously using Architecture Components.
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUpDaggerComponent() {
        entriesRepository = rule.component.entriesRepository
    }

    @After
    fun tearDown() {
    }

    @Test
    fun entries(): Unit = runBlocking {
        entriesRepository.entries()
    }

    @Test
    fun testEntries() {
    }

    @Test
    fun delete() {
    }

    @Test
    fun testEntries1() {
    }
}