package al.bruno.fruit.diary.ui.add

// import androidx.navigation.fragment.findNavController
import al.bruno.fruit.diary.R
import al.bruno.fruit.diary.adapter.BindingData
import al.bruno.fruit.diary.adapter.CustomListAdapter
import al.bruno.fruit.diary.databinding.AddFruitSingleItemBinding
import al.bruno.fruit.diary.databinding.FragmentAddFruitBinding
import al.bruno.fruit.diary.listener.OnItemClickListener
import al.bruno.fruit.diary.model.Entries
import al.bruno.fruit.diary.model.Fruit
import al.bruno.fruit.diary.ui.main.MainFragment
import al.bruno.fruit.diary.util.ENTRIES
import al.bruno.fruit.diary.util.FRUIT
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DiffUtil
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

/**
 * A simple [Fragment] subclass.
 * Use the [AddFruitFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class AddFruitFragment : MainFragment() {
    private var _binding: FragmentAddFruitBinding? = null

    // This property is only valid between onCreateView and onDestroyView.
    private val binding get() = _binding

    // findNavController().getBackStackEntry(R.id.mobile_navigation).viewModelStore
    // findNavController().getBackStackEntry(R.id.mobile_navigation)
    private val addFruitViewModel by lazy {
        ViewModelProvider(
            findNavController().getBackStackEntry(R.id.mobile_navigation).viewModelStore,
            viewModelProvider
        )[AddFruitViewModel::class.java]
    }

    // private val addFruitViewModel:AddFruitViewModel by navGraphViewModels(R.id.mobile_navigation)
    private var entries: Entries? = null

    private val adapter = CustomListAdapter(
        R.layout.add_fruit_single_item,
        object : BindingData<Fruit, AddFruitSingleItemBinding> {
            override fun bindData(t: Fruit?, vm: AddFruitSingleItemBinding) {
                vm.fruit = t
                vm.onItemClick = object : OnItemClickListener<Fruit> {
                    override fun onItemClick(t: Fruit) {
                        findNavController().navigate(
                            R.id.action_addFruitFragment_to_addFruitDialog,
                            bundleOf(FRUIT to t, ENTRIES to entries)
                        )
                    }

                    override fun onLongItemClick(t: Fruit): Boolean {
                        TODO("Not yet implemented")
                    }

                }
            }
        },
        object : DiffUtil.ItemCallback<Fruit>() {
            override fun areItemsTheSame(oldItem: Fruit, newItem: Fruit): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Fruit, newItem: Fruit): Boolean =
                oldItem == newItem
        })

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        addFruitViewModel.fruits()
        arguments?.let {
            entries = it.getParcelable(ENTRIES)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAddFruitBinding.inflate(inflater)
        binding?.viewModel = addFruitViewModel
        binding?.rvFruits?.adapter = adapter
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // https://developer.android.com/kotlin/flow/stateflow-and-sharedflow
//        lifecycleScope.launchWhenStarted {
//            addFruitViewModel.addFruitUiState.collect {
//                when(it) {
//                    is AddFruitUiState.Success -> findNavController(this@AddFruitFragment).navigate(R.id.action_addFruitFragment_to_navigationMyDairy)
//                    is AddFruitUiState.Error -> Snackbar.make(view, it.exception.toString(), Snackbar.LENGTH_SHORT).show()
//                }
//            }
//        }
        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                addFruitViewModel.fruits.collect {
                    adapter.submitList(it)
                }
            }
        }
        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                addFruitViewModel.addFruitUiState.collect {
                    when (it) {
                        is AddFruitUiState.Success -> findNavController().navigate(R.id.action_addFruitFragment_to_navigationMyDairy)
                        is AddFruitUiState.Error -> Snackbar.make(
                            view,
                            it.exception.toString(),
                            Snackbar.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding?.rvFruits?.adapter = null
        _binding = null
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(AddFruitFragment::class.java.name, "onDestroy")
    }
}
