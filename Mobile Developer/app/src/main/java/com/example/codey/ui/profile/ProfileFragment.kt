package com.example.codey.ui.profile

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.codey.databinding.FragmentProfileBinding
import com.google.firebase.database.*

class ProfileFragment : Fragment() {

    private lateinit var databaseReference: DatabaseReference
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
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

                            binding.tvUsername.text = username ?: "Unknown"
                            binding.tvEmail.text = email ?: "Unknown"
                        }
                    }

                    override fun onCancelled(error: DatabaseError) {
                    }
                })
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
