package com.example.affirmations.data

import com.example.affirmations.R
import com.example.affirmations.model.Affirmation

class Datasource {

    fun loadAffirmations(): List<Affirmation> {
        return listOf<Affirmation>(
            Affirmation(R.string.affirmation1, R.drawable.bd02b0d17e73029884c69280239f971a),
            Affirmation(R.string.affirmation2, R.drawable.s6i7ahf59s431),
            Affirmation(R.string.affirmation3, R.drawable.bd02b0d17e73029884c69280239f971a),
            Affirmation(R.string.affirmation4, R.drawable.shutterstock_1218765286),
            Affirmation(R.string.affirmation5, R.drawable.shutterstock_1218765286),
            Affirmation(R.string.affirmation6, R.drawable.s6i7ahf59s431),
            Affirmation(R.string.affirmation7, R.drawable.bd02b0d17e73029884c69280239f971a),
            Affirmation(R.string.affirmation8, R.drawable.bd02b0d17e73029884c69280239f971a),
            Affirmation(R.string.affirmation9, R.drawable.shutterstock_1218765286),
            Affirmation(R.string.affirmation10, R.drawable.shutterstock_1218765286)
        )
    }
}