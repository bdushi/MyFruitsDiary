package al.bruno.fruit.diary

import al.bruno.fruit.diary.data.source.EntriesDataSource
import al.bruno.fruit.diary.data.source.FruitDataSource
import al.bruno.fruit.diary.data.source.FruitRepository
import al.bruno.fruit.diary.data.source.local.FruitLocalDataSource
import al.bruno.fruit.diary.data.source.remote.EntriesRemoteDataSource
import al.bruno.fruit.diary.data.source.remote.FruitRemoteDataSource
import al.bruno.fruit.diary.di.FakeDataSourceModule
import al.bruno.fruit.diary.di.FakeModule
import al.bruno.fruit.diary.di.TestComponent
import androidx.test.ext.junit.runners.AndroidJUnit4
import it.cosenonjaviste.daggermock.DaggerMockRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
class FruitMoc {
    lateinit var fruitRepository: FruitRepository

    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    @Mock lateinit var fruitLocalDataSource: FruitLocalDataSource
    @Mock lateinit var fruitRemoteDataSource: FruitRemoteDataSource

    @get:Rule
    var rule: DaggerMockRule<TestComponent> =
        DaggerMockRule(TestComponent::class.java, FakeModule())
            .set {
                fruitRepository = it.fruitRepository
            }

    @Test
    fun fruit(): Unit = runBlocking {
//        when(restService.getSomething()).thenReturn("abc");
        val fruits = fruitRepository.fruitRemote().body()
        fruits?.let { fruitRepository.insert(it) }
    }

}