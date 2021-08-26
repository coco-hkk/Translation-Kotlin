package com.coco_hkk.translation

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat


/**
 * 重新定义 ArrayAdapter，处理 Word 内容显示
 */
class WordAdapter(
    context: Context,
    words: List<Word>,
    private val bgColor: Int
) : ArrayAdapter<Word>(context, 0, words) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        // 对每个 activity 使用 list_item.xml 布局
        val listView: View = convertView ?: LayoutInflater.from(context).inflate(
            R.layout.list_item, parent, false
        )

        // 获取当前位置 Word
        val currentWord: Word = getItem(position) as Word

        // 根据不同 activity 设置相应背景颜色
        val textContainer: View = listView.findViewById(R.id.text_container)
        val color = ContextCompat.getColor(context, bgColor)
        textContainer.setBackgroundColor(color)

        // 显示中文
        val cnTextView: TextView = listView.findViewById(R.id.zh_text_view)
        cnTextView.text = currentWord.cnTranslation

        // 显示英文
        val enTextView: TextView = listView.findViewById(R.id.en_text_view)
        enTextView.text = currentWord.enTranslation

        // 根据是否有图片，选择是否显示 ImageView
        val imageView: ImageView = listView.findViewById(R.id.list_item_icon)
        currentWord.imageId?.let {
            //imageView.setImageResource(it)
            imageView.setImageDrawable(ContextCompat.getDrawable(context, it))
            imageView.visibility = View.VISIBLE
        } ?:let { imageView.visibility = View.GONE }

        return listView
    }
}