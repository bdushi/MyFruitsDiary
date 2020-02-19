package al.bruno.fruit.diary.listener

import android.view.View
import android.widget.AdapterView

interface OnItemSelectedListener {
    fun onItemSelected(adapterView: AdapterView<*>?, view: View?, position: Int, id: Long)
}