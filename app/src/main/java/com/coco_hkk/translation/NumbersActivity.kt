package com.coco_hkk.translation

import android.content.Context
import android.media.AudioManager
import android.media.MediaPlayer
import android.os.Bundle
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity

class NumbersActivity : AppCompatActivity() {
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

        // 定义 numbers 列表，元素类型为 Word 类
        val words: Array<Word> =
            arrayOf(
                Word("one", "一", R.drawable.number_one, R.raw.number_one),
                Word("two", "二", R.drawable.number_two, R.raw.number_two),
                Word("three", "三", R.drawable.number_three, R.raw.number_three),
                Word("four", "四", R.drawable.number_four, R.raw.number_four),
                Word("five", "五", R.drawable.number_five, R.raw.number_five),
                Word("six", "六", R.drawable.number_six, R.raw.number_six),
                Word("seven", "七", R.drawable.number_seven, R.raw.number_seven),
                Word("eight", "八", R.drawable.number_eight, R.raw.number_eight),
                Word("nine", "九", R.drawable.number_nine, R.raw.number_nine),
                Word("ten", "十", R.drawable.number_ten, R.raw.number_ten),
            )

        /* for (x in words.indices) {
             Log.v("NumbersActivity", "Word at index $x: ${words[x]}")
         }*/

        // 创建 AdapterView
        val adapter = WordAdapter(this, words, R.color.category_numbers)

        val listView: ListView = findViewById(R.id.list)

        listView.adapter = adapter

        listView.setOnItemClickListener { parent, view, position, id ->
            val word: Word = words[position]
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
