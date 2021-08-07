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
class WordAdapter : ArrayAdapter<Word> {
    private var mColor: Int = -1

    // 将不同 activity 的背景颜色设置安排在这里
    constructor(context: Context, words: Array<Word>, bgColor: Int) : super(context, 0, words) {
        mColor = bgColor
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        // 对每个 activity 使用 list_item.xml 布局
        val listView: View = convertView ?: LayoutInflater.from(context).inflate(
            R.layout.list_item, parent, false
        )

        // 获取当前位置 Word
        val currentWord: Word = getItem(position) as Word

        // 根据不同 activity 设置相应背景颜色
        val textContainer: View = listView.findViewById(R.id.text_container)
        val color = ContextCompat.getColor(context, mColor)
        textContainer.setBackgroundColor(color)

        // 显示中文
        val cnTextView: TextView = listView.findViewById(R.id.zh_text_view)
        cnTextView.text = currentWord.getCnTranslation()

        // 显示英文
        val enTextView: TextView = listView.findViewById(R.id.en_text_view)
        enTextView.text = currentWord.getEnTranslation()

        // 根据是否有图片，选择是否使用 ImageView
        val imageView: ImageView = listView.findViewById(R.id.list_item_icon)
        if (currentWord.hasImage()) {
            imageView.setImageResource(currentWord.getImageResourceId())
            imageView.visibility = View.VISIBLE
        } else {
            // 不显示 ImageView 视图，相当于把 ImageView 在 xml 中删除
            imageView.visibility = View.GONE
        }

        return listView
    }
}