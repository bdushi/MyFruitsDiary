package al.bruno.fruit.diary.data.source

/**
 * https://developer.android.com/studio/test
 */

import al.bruno.fruit.diary.DaggerTestApplicationRule
import al.bruno.fruit.diary.MainCoroutineRule
import al.bruno.fruit.diary.data.source.local.AppDatabase
import junit.framework.Assert.assertTrue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import javax.inject.Inject

@RunWith(JUnit4::class)
@ExperimentalCoroutinesApi
class FruitRepositoryTest {

    @set:Inject lateinit var entriesRepository: EntriesRepository
    @set:Inject lateinit var appDatabase: AppDatabase

    // Set the main coroutines dispatcher for unit testing.
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    /**
     * Sets up Dagger components for testing.
     */
//    @get:Rule
//    val rule = DaggerTestApplicationRule()

    /**
     * Gets a reference to the [EntriesRepository] exposed by the [DaggerTestApplicationRule].
     */
    @Before
    fun setupDaggerComponent() {
        DaggerTestApplicationRule()
//        entriesRepository = rule.component.entriesRepository
    }

    // Executes each task synchronously using Architecture Components.
//    @get:Rule
//    var instantExecutorRule = InstantTaskExecutorRule()

    @After
    fun closedb() {
//        appDatabase.close()
    }

    @Test
    fun todo() = runBlockingTest {
        val response = entriesRepository.entries()
        assertTrue(response.isSuccessful)
    }

    //    @Test
//    public void testInsert() {
//        assertNotEquals(dictionaryRepository.put(new Dictionary("test", "test")), -1);
//    }

    @Test
    fun fruitRemote() {
    }

    @Test
    fun fruitLocal() {
    }

    @Test
    fun insert() {
    }

    @Test
    fun fruits() {
    }
}