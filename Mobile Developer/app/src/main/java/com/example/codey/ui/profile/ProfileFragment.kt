package com.example.codey.ui.profile

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.codey.R
import com.google.firebase.database.*

class ProfileFragment : Fragment() {

    private lateinit var databaseReference: DatabaseReference
    private lateinit var usernameTextView: TextView
    private lateinit var emailTextView: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_profile, container, false)
        usernameTextView = view.findViewById(R.id.tv_username)
        emailTextView = view.findViewById(R.id.tv_email)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        databaseReference = FirebaseDatabase.getInstance().getReference("users")

        val sharedPreferences = requireActivity().getSharedPreferences("UserPrefs", Context.MODE_PRIVATE)
        val loggedInEmail = sharedPreferences.getString("LOGGED_IN_EMAIL", null)

        if (!loggedInEmail.isNullOrEmpty()) {
            databaseReference.orderByChild("email").equalTo(loggedInEmail)
                .addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        for (childSnapshot in snapshot.children) {
                            val username = childSnapshot.child("username").getValue(String::class.java)
                            val email = childSnapshot.child("email").getValue(String::class.java)

                            usernameTextView.text = username ?: "Unknown"
                            emailTextView.text = email ?: "Unknown"
                        }
                    }

                    override fun onCancelled(error: DatabaseError) {
                    }
                })
        }
    }
}
