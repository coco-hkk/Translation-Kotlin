package com.coco_hkk.translation

import android.content.Context
import android.media.AudioAttributes
import android.media.AudioAttributes.CONTENT_TYPE_MUSIC
import android.media.AudioFocusRequest
import android.media.AudioManager
import android.media.MediaPlayer
import android.os.Build
import android.os.Bundle
import android.widget.ListView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.coco_hkk.translation.databinding.WordListBinding

class FamilyActivity : AppCompatActivity() {
    // 视图绑定
    private lateinit var mBinding: WordListBinding

    private var mMedia: MediaPlayer? = null

    private lateinit var mFocusRequest: AudioFocusRequest

    // media 服务回调
    private val mCompletionListener: MediaPlayer.OnCompletionListener =
        MediaPlayer.OnCompletionListener {
            releaseMediaPlayer()
        }

    private lateinit var mAudioManager: AudioManager

    // 3. AudioFocus 回调
    private val afChangeListener =
        AudioManager.OnAudioFocusChangeListener { focusChange ->
            when (focusChange) {
                AudioManager.AUDIOFOCUS_LOSS -> {
                    releaseMediaPlayer()
                }
                AudioManager.AUDIOFOCUS_LOSS_TRANSIENT, AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK -> {
                    // 对于单词，重新播放
                    mMedia?.pause()
                    mMedia?.seekTo(0)
                }
                AudioManager.AUDIOFOCUS_GAIN -> {
                    mMedia?.start()
                }
                else -> {
                }
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 视图绑定
        mBinding = WordListBinding.inflate(layoutInflater)
        val view = mBinding.root
        setContentView(view)

        // 0. 获取音频服务
        mAudioManager = getSystemService(Context.AUDIO_SERVICE) as AudioManager

        // 1. 定义 AudioFocus 属性
        val mPlaybackAttributes = AudioAttributes.Builder()
            .setUsage(AudioAttributes.USAGE_MEDIA)
            .setContentType(CONTENT_TYPE_MUSIC)
            .build()

        // 2. 设置 AudioFocusRequest，指定音频焦点变化时的回调函数
        mFocusRequest = AudioFocusRequest.Builder(AudioManager.AUDIOFOCUS_GAIN_TRANSIENT)
            .setAudioAttributes(mPlaybackAttributes)
            .setOnAudioFocusChangeListener(afChangeListener)
            .build()

        val family: List<Word> =
            listOf(
                Word("father", "爹", R.drawable.family_father, R.raw.family_father),
                Word("mother", "娘", R.drawable.family_mother, R.raw.family_mother),
                Word("son", "儿子", R.drawable.family_son, R.raw.family_son),
                Word("daughter", "女儿", R.drawable.family_daughter, R.raw.family_daughter),
                Word(
                    "older brother",
                    "兄",
                    R.drawable.family_older_brother,
                    R.raw.family_older_brother
                ),
                Word(
                    "younger brother",
                    "弟",
                    R.drawable.family_younger_brother,
                    R.raw.family_younger_brother
                ),
                Word(
                    "older sister",
                    "姐",
                    R.drawable.family_older_sister,
                    R.raw.family_older_sister
                ),
                Word(
                    "younger sister",
                    "妹",
                    R.drawable.family_younger_sister,
                    R.raw.family_younger_sister
                ),
                Word("grandmother", "祖父", R.drawable.family_grandfather, R.raw.family_grandfather),
                Word("grandfather", "祖母", R.drawable.family_grandmother, R.raw.family_grandmother),
            )

        val adapter = WordAdapter(this, family, R.color.category_family)

        val listView: ListView = mBinding.list

        listView.adapter = adapter

        listView.setOnItemClickListener { _, _, position, _ ->
            val word: Word = family[position]

            releaseMediaPlayer()

            // 4. AudioManager 处理音频焦点
            val result = mAudioManager.requestAudioFocus(mFocusRequest)

            // 5. 若请求成功则播放音频
            if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
                mMedia = MediaPlayer.create(this, word.soundId)
                mMedia?.setAudioAttributes(mPlaybackAttributes)
                mMedia?.start()

                // media 一结束就调用 mCompletionListener, 它是全局变量，不需要每次都重新创建
                mMedia?.setOnCompletionListener(mCompletionListener)
            }
        }
    }

    // 当 activity 在 onStop 状态时释放 media 资源
    override fun onStop() {
        super.onStop()

        releaseMediaPlayer()
    }

    // 释放 media 资源
    private fun releaseMediaPlayer() {
        mMedia?.release()
        mMedia = null

        // 6. 当失去音频焦点时调用
        mAudioManager.abandonAudioFocusRequest(mFocusRequest)
    }
}
