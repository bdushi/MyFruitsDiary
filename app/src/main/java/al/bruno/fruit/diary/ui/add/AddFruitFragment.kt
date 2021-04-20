package al.bruno.fruit.diary.ui.add

import al.bruno.fruit.diary.R
import al.bruno.fruit.diary.databinding.FragmentAddFruitBinding
import al.bruno.fruit.diary.listener.OnItemClickListener
import al.bruno.fruit.diary.model.Entries
import al.bruno.fruit.diary.model.Fruit
import al.bruno.fruit.diary.ui.main.MainFragment
import al.bruno.fruit.diary.util.ENTRIES
import al.bruno.fruit.diary.util.FRUIT
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.NavHostFragment.findNavController
// import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

/**
 * A simple [Fragment] subclass.
 * Use the [AddFruitFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class AddFruitFragment : MainFragment() {
    @Inject
    lateinit var viewModelProvider: ViewModelProvider.Factory

    // findNavController().getBackStackEntry(R.id.mobile_navigation).viewModelStore
    // findNavController().getBackStackEntry(R.id.mobile_navigation)
    private val addFruitViewModel by lazy {
        ViewModelProvider(
            findNavController(this).getBackStackEntry(R.id.mobile_navigation).viewModelStore,
            viewModelProvider)[AddFruitViewModel::class.java]
    }
    // private val addFruitViewModel:AddFruitViewModel by navGraphViewModels(R.id.mobile_navigation)
    private var entries: Entries? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        addFruitViewModel.fruits()
        arguments?.let {
            entries = it.getParcelable(ENTRIES)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val fragmentAddFruitBinding = FragmentAddFruitBinding.inflate(inflater)
        fragmentAddFruitBinding.lifecycleOwner = this
        fragmentAddFruitBinding.viewModel = addFruitViewModel
        return fragmentAddFruitBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        addFruitViewModel.onItemSelectedListener = object : OnItemClickListener<Fruit> {
            override fun onItemClick(t: Fruit) {
                findNavController(this@AddFruitFragment).navigate(R.id.action_addFruitFragment_to_addFruitDialog,
                    bundleOf(FRUIT to t, ENTRIES to entries))
            }

            override fun onLongItemClick(t: Fruit): Boolean {
                return false
            }
        }
        // https://developer.android.com/kotlin/flow/stateflow-and-sharedflow
        lifecycleScope.launchWhenStarted {
            addFruitViewModel.addFruitUiState.collect {
                when(it) {
                    is AddFruitUiState.Success -> findNavController(this@AddFruitFragment).navigate(R.id.action_addFruitFragment_to_navigationMyDairy)
                    is AddFruitUiState.Error -> Snackbar.make(view, it.exception.toString(), Snackbar.LENGTH_SHORT).show()
                }
            }
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.d(AddFruitFragment::class.java.name, "onDestroyView")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(AddFruitFragment::class.java.name, "onDestroy")
    }
}
