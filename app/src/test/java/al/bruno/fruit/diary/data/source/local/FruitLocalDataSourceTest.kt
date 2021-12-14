package al.bruno.fruit.diary.data.source.local

import al.bruno.fruit.diary.MainCoroutineRule
import al.bruno.fruit.diary.data.source.FruitRepository
import al.bruno.fruit.diary.di.FakeModule
import al.bruno.fruit.diary.di.TestComponent
import it.cosenonjaviste.daggermock.DaggerMockRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@ExperimentalCoroutinesApi
@RunWith(JUnit4::class)
class FruitLocalDataSourceTest {

    lateinit var fruitRepository: FruitRepository

    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

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

    @Test
    fun fruits() {
    }

    @Test
    fun insert() {
    }
}