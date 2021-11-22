package al.bruno.fruit.diary.ui.add

import al.bruno.fruit.diary.R
import al.bruno.fruit.diary.databinding.DialogAddFruitBinding
import al.bruno.fruit.diary.databinding.FragmentAddFruitBinding
import al.bruno.fruit.diary.listener.ViewOnClickListener
import al.bruno.fruit.diary.model.Entries
import al.bruno.fruit.diary.model.Fruit
import al.bruno.fruit.diary.util.ENTRIES
import al.bruno.fruit.diary.util.FRUIT
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject

class AddFruitDialog : DialogFragment() {

    private var fruit: Fruit? = null
    private var entries: Entries? = null

    @Inject
    lateinit var viewModelProvider: ViewModelProvider.Factory

    private var _binding: DialogAddFruitBinding? = null
    // This property is only valid between onCreateView and onDestroyView.
    private val binding get() = _binding
    /**
     * https://developer.android.com/guide/navigation/navigation-programmatic#kotlin
     */
    // findNavController().getBackStackEntry(R.id.mobile_navigation)
    private val addFruitViewModel by lazy {
        ViewModelProvider(
            findNavController().getBackStackEntry(R.id.mobile_navigation).viewModelStore,
            viewModelProvider
        )[AddFruitViewModel::class.java]
    }

    //private val addFruitViewModel:AddFruitViewModel by navGraphViewModels(R.id.mobile_navigation)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NO_TITLE, R.style.MyTheme_FloatingDialog);
        arguments?.let {
            fruit = it.getParcelable(FRUIT)
            entries = it.getParcelable(ENTRIES)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DialogAddFruitBinding.inflate(inflater)
        binding?.fruit = fruit
        binding?.onClick = object : ViewOnClickListener<Fruit> {
            override fun onClick(v: View, t: Fruit) {
                when (v.id) {
                    R.id.add_fruit_ok -> {
                        addFruitViewModel.entries(
                            entryId = entries?.id,
                            fruitId = fruit?.id,
                            nrOfFruit = fruit?.amount
                        )
                        dismiss()
                    }
                    R.id.add_fruit_cancel -> {
                        dismiss()
                    }
                }
            }
        }
        return binding?.root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        AndroidSupportInjection.inject(this)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}