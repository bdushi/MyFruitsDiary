package al.bruno.fruit.diary.ui.my.diary

import al.bruno.fruit.diary.R
import al.bruno.fruit.diary.ui.main.MainFragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import al.bruno.fruit.diary.databinding.FragmentMyDairyBinding
import al.bruno.fruit.diary.listener.OnItemClickListener
import al.bruno.fruit.diary.model.Entries
import al.bruno.fruit.diary.util.ENTRIES
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.snackbar.Snackbar
import java.sql.Date
import javax.inject.Inject

class MyDiaryFragment : MainFragment() {

    @Inject
    lateinit var viewModelProvider: ViewModelProvider.Factory

    private val myDiaryViewModel by lazy {
        ViewModelProvider(this, viewModelProvider)[MyDiaryViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val fragmentMyDairyBinding = FragmentMyDairyBinding.inflate(inflater)
        // load entries
        myDiaryViewModel.entries()
        fragmentMyDairyBinding.viewModel = myDiaryViewModel
        fragmentMyDairyBinding.lifecycleOwner = this
        fragmentMyDairyBinding.setRefreshListener {
            // refresh entries
            myDiaryViewModel.entries()
        }
        fragmentMyDairyBinding.setOnClick {
            val builder : MaterialDatePicker.Builder<*> = MaterialDatePicker.Builder.datePicker() // 1
            val picker : MaterialDatePicker<*> = builder.build()  // 2
            picker.show(parentFragmentManager, picker.toString())
            picker.addOnPositiveButtonClickListener {
                myDiaryViewModel.entries(Date(it as Long))
            }
        }
        return fragmentMyDairyBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        myDiaryViewModel.error.observe(this, Observer {
            Snackbar.make(view, it, Snackbar.LENGTH_SHORT).show()
        })
        myDiaryViewModel.onItemSelectedListener = object : OnItemClickListener<Entries> {
            override fun onItemClick(t: Entries) {
                findNavController()
                    .navigate(
                        R.id.action_navigation_my_dairy_to_detailFragment,
                        bundleOf(ENTRIES to t)
                    )
            }

            override fun onLongItemClick(t: Entries): Boolean {
                return false
            }

        }
    }
}