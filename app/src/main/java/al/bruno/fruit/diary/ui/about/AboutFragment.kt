package al.bruno.fruit.diary.ui.about

import al.bruno.fruit.diary.ui.main.MainFragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import al.bruno.fruit.diary.R

class AboutFragment : MainFragment() {

    private lateinit var aboutViewModel: AboutViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_about, container, false)

        return root
    }
}