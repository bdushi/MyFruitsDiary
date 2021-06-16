package al.bruno.fruit.diary.data.source.local

import al.bruno.fruit.diary.DaggerLocalTestComponent
import al.bruno.fruit.diary.MainCoroutineRule
import al.bruno.fruit.diary.data.source.EntriesDataSource
import al.bruno.fruit.diary.data.source.remote.EntriesRemoteDataSource
import al.bruno.fruit.diary.model.Entries
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import java.util.*
import javax.inject.Inject

@ExperimentalCoroutinesApi
@RunWith(JUnit4::class)
class EntriesRemoteDataSourceTest {

    @set:Inject
    lateinit var entriesDataSource: EntriesRemoteDataSource

    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    @Before
    fun setupDaggerComponent() {
        DaggerLocalTestComponent.factory().create(this).inject(this)
    }

    @Test
    fun entries() = runBlocking {
        assertTrue(entriesDataSource.entries().body() is List<Entries>)
    }

    @Test
    fun delete() = runBlocking {
        assertTrue(entriesDataSource.delete().isSuccessful)
    }

    @Test
    fun delete(entryId: Long) = runBlocking {
        assertTrue(entriesDataSource.delete(1).isSuccessful)
    }

    @Test
    fun entries(date: Map<String, Date>) = runBlocking {
        assertTrue(entriesDataSource.entries(mapOf("apple" to Date())).isSuccessful)
    }

    @Test
    fun entries(entryId: Long?,fruitId: Long?, nrOfFruit: Int?) = runBlocking {
        assertTrue(entriesDataSource.entries(1, 1,2).isSuccessful)
    }

    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }
}
