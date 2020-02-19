package al.bruno.fruit.diary.binding.utils

import android.text.Spannable
import android.text.SpannableString
import android.text.style.BackgroundColorSpan
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.google.android.material.textview.MaterialTextView
import com.squareup.picasso.Picasso


object Adapter {
    @JvmStatic
    @BindingAdapter("bind:res", requireAll = true)
    fun image(imageView: AppCompatImageView, res: Int) {
        val finalHeight = (imageView.resources.displayMetrics.widthPixels * 2) / (550 / 367)
        imageView.minimumHeight = finalHeight
        imageView.setImageResource(res)
    }

    @JvmStatic
    @BindingAdapter("bind:img", requireAll = true)
    fun image(imageView: AppCompatImageView, url: String) {
        Picasso.get().load("https://fruitdiary.test.themobilelife.com/$url").into(imageView);
    }

    @JvmStatic
    @BindingAdapter("bind:text", requireAll = true)
    fun span(materialTextView: MaterialTextView, text: String) {
        val spannable = SpannableString(text)
        spannable.setSpan(
            BackgroundColorSpan(ContextCompat.getColor(materialTextView.context, android.R.color.darker_gray)),
            spannable.length - 10, // start
            spannable.length, // end
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        materialTextView.text = spannable
    }

    @JvmStatic
    @BindingAdapter(value = ["bind:cover"], requireAll = false)
    fun cover(imageView: AppCompatImageView, res: Int) {
        imageView.setImageResource(res)
    }
}
