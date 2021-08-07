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

class Word(
    enTranslation: String,
    cnTranslation: String,
    soundId: Int
) {
    private val NO_IMAGE_PROVIDED = -1
    private var mEnTranslation: String = enTranslation
    private var mCnTranslation: String = cnTranslation
    private var mImageId: Int = NO_IMAGE_PROVIDED
    private var mSoundId: Int = soundId

    // 次构造函数，接受图片
    constructor(
        enTranslation: String,
        cnTranslation: String,
        imageId: Int,
        soundId: Int
    ) : this(enTranslation, cnTranslation, soundId) {
        mImageId = imageId
    }

    fun getEnTranslation(): String {
        return mEnTranslation
    }

    fun getCnTranslation(): String {
        return mCnTranslation
    }

    fun getImageResourceId(): Int {
        return mImageId
    }

    fun getSoundResourceId(): Int {
        return mSoundId
    }

    // 如果有图片，返回 true
    fun hasImage(): Boolean {
        return mImageId != NO_IMAGE_PROVIDED
    }
}