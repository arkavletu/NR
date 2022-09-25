package ru.netology.myrecipes

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import ru.netology.myrecipes.databinding.IntentImageActivityBinding

class IntentImageActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)

        val binding = IntentImageActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val intent = intent ?: return
        if (intent.action != Intent.ACTION_GET_CONTENT) return
        if(intent.data == null) return
    }
}