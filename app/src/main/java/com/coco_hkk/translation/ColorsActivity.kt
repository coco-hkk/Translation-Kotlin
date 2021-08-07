package com.coco_hkk.translation

import android.content.Context
import android.media.AudioManager
import android.media.MediaPlayer
import android.os.Bundle
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity

class ColorsActivity : AppCompatActivity() {
    private var mMedia: MediaPlayer? = null

    private val mCompletionListener: MediaPlayer.OnCompletionListener =
        MediaPlayer.OnCompletionListener {
            releaseMediaPlayer()
        }

    private lateinit var mAudioManager: AudioManager
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
        setContentView(R.layout.word_list)

        mAudioManager = getSystemService(Context.AUDIO_SERVICE) as AudioManager

        val colors: Array<Word> =
            arrayOf(
                Word("red", "红", R.drawable.color_red, R.raw.color_red),
                Word("green", "绿", R.drawable.color_green, R.raw.color_green),
                Word("brown", "棕", R.drawable.color_brown, R.raw.color_brown),
                Word("gray", "灰", R.drawable.color_gray, R.raw.color_gray),
                Word("black", "黑", R.drawable.color_black, R.raw.color_black),
                Word("white", "白", R.drawable.color_white, R.raw.color_white),
                Word("orange", "橙", R.drawable.color_dusty_yellow, R.raw.color_orange),
                Word("yellow", "黄", R.drawable.color_mustard_yellow, R.raw.color_yellow),
            )

        val adapter = WordAdapter(this, colors, R.color.category_colors)

        val listView: ListView = findViewById(R.id.list)

        listView.adapter = adapter

        listView.setOnItemClickListener { parent, view, position, id ->
            val word: Word = colors[position]
            releaseMediaPlayer()

            val result: Int = mAudioManager.requestAudioFocus(
                afChangeListener,
                AudioManager.STREAM_MUSIC,
                AudioManager.AUDIOFOCUS_GAIN_TRANSIENT
            )

            if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
                mMedia = MediaPlayer.create(this, word.getSoundResourceId())
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
        mAudioManager.abandonAudioFocus(afChangeListener)
    }
}
