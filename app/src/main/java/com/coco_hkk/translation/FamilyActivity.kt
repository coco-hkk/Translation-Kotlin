package com.coco_hkk.translation

import android.content.Context
import android.media.AudioManager
import android.media.MediaPlayer
import android.os.Bundle
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity

class FamilyActivity : AppCompatActivity() {
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

        val family: Array<Word> =
            arrayOf(
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

        val listView: ListView = findViewById(R.id.list)

        listView.adapter = adapter

        listView.setOnItemClickListener { parent, view, position, id ->
            val word: Word = family[position]

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
