package com.example.learnagain.leftzoom

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.learnagain.databinding.FragmentZoomLeftBinding

class FragmentZoomLeft : AppCompatActivity() {

    private lateinit var binding: FragmentZoomLeftBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.binding = FragmentZoomLeftBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val dataset = DatasourceLeft().loadAffirmations()
        val recyclerView = binding.recyclerviewLeft
        recyclerView.adapter = LeftAdapter(this, dataset)
        recyclerView.setHasFixedSize(true)
    }
}
