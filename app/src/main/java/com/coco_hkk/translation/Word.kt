package com.coco_hkk.translation

/**
 * 使用 ListView + ArrayAdapter 方法实现动态内容或未预先确定的内容布局
 * 1. 自定义每条内容布局方式，如 list_item.xml
 * 2. 自定义每条内容数据，如 Word.kt
 * 3. 自定义如何显示每条内容，如 WordAdapter.kt 中 getView() 实现
 */

/**
 * @param   enTranslation   英文
 * @param   cnTranslation   中文
 */

data class Word(
    val enTranslation: String,
    val cnTranslation: String,
    val imageId: Int? = null,
    val soundId: Int
)