package al.bruno.fruit.diary.ui.detail

import al.bruno.fruit.diary.R
import al.bruno.fruit.diary.databinding.FragmentAddFruitBinding
import al.bruno.fruit.diary.databinding.FragmentDetailsBinding
import al.bruno.fruit.diary.listener.ViewOnClickListener
import al.bruno.fruit.diary.model.Entries
import al.bruno.fruit.diary.ui.add.AddFruitFragment
import al.bruno.fruit.diary.ui.main.MainFragment
import al.bruno.fruit.diary.util.ENTRIES
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment.Companion.findNavController
import com.google.android.material.snackbar.Snackbar
import javax.inject.Inject

class DetailFragment : MainFragment() {
    @Inject
    lateinit var viewModelProvider: ViewModelProvider.Factory

    private var _binding: FragmentDetailsBinding? = null
    // This property is only valid between onCreateView and onDestroyView.
    private val binding get() = _binding
    //    private val backStackEntry = findNavController().getBackStackEntry(R.id.nav_host_fragment)
//    private val vm:DetailViewModel by navGraphViewModels(R.id.nav_host_fragment)
    private val detailViewModel by lazy {
        ViewModelProvider(this, viewModelProvider)[DetailViewModel::class.java]
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        detailViewModel.success.observe(viewLifecycleOwner) {
            findNavController(this).popBackStack()
        }
        detailViewModel.error.observe(viewLifecycleOwner) {
            Snackbar.make(view, it, Snackbar.LENGTH_SHORT).show()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View?{
        _binding = FragmentDetailsBinding.inflate(inflater)
        arguments?.let {
            val entries: Entries? = it.getParcelable(ENTRIES)
            binding?.entries = entries
            detailViewModel.fruit(entries?.fruit)
        }
        binding?.viewModel = detailViewModel
        binding?.lifecycleOwner = this
        binding?.onClick = object : ViewOnClickListener<Entries> {
            override fun onClick(v: View, t: Entries) {
                when (v.id) {
                    R.id.details_entries_remove -> {
                        detailViewModel.delete(entryId = t.id)
                    }
                    R.id.details_add_fruit -> {
                        findNavController(this@DetailFragment).navigate(
                            R.id.action_detailFragment_to_addFruitFragment,
                            bundleOf(ENTRIES to t)
                        )
                    }
                    R.id.details_fab_add_fruit -> {
                        findNavController(this@DetailFragment).navigate(
                            R.id.action_detailFragment_to_addFruitFragment,
                            bundleOf(ENTRIES to t)
                        )
                    }
                }
            }
        }
        return binding?.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        Log.d(AddFruitFragment::class.java.name, "onDestroyView")
    }
}