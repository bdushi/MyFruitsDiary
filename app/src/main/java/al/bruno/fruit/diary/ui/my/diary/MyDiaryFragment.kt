package al.bruno.fruit.diary.ui.my.diary

import al.bruno.fruit.diary.R
import al.bruno.fruit.diary.adapter.BindingData
import al.bruno.fruit.diary.adapter.CustomListAdapter
import al.bruno.fruit.diary.data.source.Result
import al.bruno.fruit.diary.databinding.EntriesSingleItemBinding
import al.bruno.fruit.diary.databinding.FragmentMyDairyBinding
import al.bruno.fruit.diary.listener.OnItemClickListener
import al.bruno.fruit.diary.model.Entries
import al.bruno.fruit.diary.ui.main.MainFragment
import al.bruno.fruit.diary.util.ENTRIES
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DiffUtil
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

class MyDiaryFragment : MainFragment() {
    private var _binding: FragmentMyDairyBinding? = null
    private var _adapter:CustomListAdapter<Entries, EntriesSingleItemBinding>? = null

    // This property is only valid between onCreateView and onDestroyView.
    private val binding get() = _binding
    private val adapter get() = _adapter
    private val myDiaryViewModel by lazy {
        ViewModelProvider(this, viewModelProvider)[MyDiaryViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMyDairyBinding.inflate(inflater)
        _adapter = CustomListAdapter(
            R.layout.entries_single_item,
            object : BindingData<Entries, EntriesSingleItemBinding> {
                override fun bindData(t: Entries?, vm: EntriesSingleItemBinding) {
                    vm.entries = t
                    vm.onItemClick = object : OnItemClickListener<Entries> {
                        override fun onItemClick(t: Entries) {
                            findNavController()
                                .navigate(
                                    R.id.action_navigation_my_dairy_to_detailFragment,
                                    bundleOf(ENTRIES to t)
                                )
                        }

                        override fun onLongItemClick(t: Entries): Boolean {
                            TODO("Not yet implemented")
                        }
                    }
                }
            },
            object : DiffUtil.ItemCallback<Entries>() {
                override fun areItemsTheSame(oldItem: Entries, newItem: Entries): Boolean =
                    oldItem.id == newItem.id

                override fun areContentsTheSame(oldItem: Entries, newItem: Entries): Boolean =
                    oldItem == newItem
            })
        binding?.viewModel = myDiaryViewModel
        binding?.rvEntries?.adapter = adapter
        /**
         * load entries
         */
        myDiaryViewModel.entries()

        /**
         * refresh entries
         */
        binding?.setRefreshListener {
            myDiaryViewModel.entries()
        }
        binding?.setOnClick {
            val builder: MaterialDatePicker.Builder<*> =
                MaterialDatePicker.Builder.datePicker() // 1
            val picker: MaterialDatePicker<*> = builder.build()  // 2
            picker.show(parentFragmentManager, picker.toString())
            picker.addOnPositiveButtonClickListener {
                myDiaryViewModel.entries(Date(it as Long))
            }
        }
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                myDiaryViewModel.entries.collect {
                    when(it) {
                        is Result.Success -> adapter?.submitList(it.data)
                        is Result.Error -> Snackbar.make(view, it.toString(), Snackbar.LENGTH_SHORT).show()
                        is Result.Loading -> {}
                    }
                }
            }
        }
        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                myDiaryViewModel.entriess.collect {
                    when(it) {
                        is Result.Success -> myDiaryViewModel.entries()
                        is Result.Error -> Snackbar.make(view, it.toString(), Snackbar.LENGTH_SHORT).show()
                        is Result.Loading -> {}
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding?.rvEntries?.adapter = null
        _binding = null
    }
}