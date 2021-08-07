package com.coco_hkk.translation

import android.content.Context
import android.media.AudioManager
import android.media.MediaPlayer
import android.os.Bundle
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity

class PhrasesActivity : AppCompatActivity() {
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

        val phrase: Array<Word> =
            arrayOf(
                Word("Good Morning!", "早上好！", R.raw.phrase_morning),
                Word("Did you have breakfast?", "吃了吗？", R.raw.phrase_eat),
                Word("Where are you going?", "去哪？", R.raw.phrase_go),
            )

        val adapter = WordAdapter(this, phrase, R.color.category_phrases)

        val listView: ListView = findViewById(R.id.list)

        listView.adapter = adapter

        listView.setOnItemClickListener { parent, view, position, id ->
            val word: Word = phrase[position]

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

        setSupportActionBar()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
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