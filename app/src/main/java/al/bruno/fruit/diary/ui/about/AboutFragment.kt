package al.bruno.fruit.diary.ui.about

import al.bruno.fruit.diary.ui.main.MainFragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import al.bruno.fruit.diary.R

class AboutFragment : MainFragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_about, container, false)
    }
}