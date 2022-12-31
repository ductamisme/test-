package com.example.learnagain.leftzoom

import com.example.learnagain.R

class DatasourceLeft {

    fun loadAffirmations(): List<Affirmation> {
        return listOf<Affirmation>(
            Affirmation(R.string.affirmation1, R.drawable.image1),
            Affirmation(R.string.affirmation2, R.drawable.image2),
            Affirmation(R.string.affirmation3, R.drawable.image3),
            Affirmation(R.string.affirmation4, R.drawable.image4),
            Affirmation(R.string.affirmation5, R.drawable.image5),
            Affirmation(R.string.affirmation6, R.drawable.image6),
            Affirmation(R.string.affirmation7, R.drawable.image7),
            Affirmation(R.string.affirmation8, R.drawable.image8),
            Affirmation(R.string.affirmation9, R.drawable.image9),
            Affirmation(R.string.affirmation10, R.drawable.image10))
    }
}

/*<androidx.fragment.app.FragmentContainerView
android:id="@+id/nav_host_fragment_child"
android:layout_width="200dp"
android:layout_height="570dp"
app:defaultNavHost="true"
app:layout_constraintBottom_toBottomOf="parent"
app:layout_constraintEnd_toEndOf="parent"
app:layout_constraintHorizontal_bias="0.0"
app:layout_constraintTop_toTopOf="parent"
app:layout_constraintVertical_bias="0.0"
app:navGraph="@navigation/nav_graph"
tools:layout="@layout/fragment_zoom_left"
/>
*/