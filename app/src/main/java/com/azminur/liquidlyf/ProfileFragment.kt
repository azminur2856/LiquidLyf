package com.azminur.liquidlyf

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment

class ProfileFragment : Fragment() {
    private lateinit var updateProfileItem: ConstraintLayout
    private lateinit var changePasswordItem: ConstraintLayout

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        updateProfileItem = view.findViewById(R.id.update_profile_item)
        changePasswordItem = view.findViewById(R.id.change_password_item)

        updateProfileItem.setOnClickListener {
            val intent = Intent(requireActivity(), UpdateProfileActivity::class.java)
            startActivity(intent)
        }

        changePasswordItem.setOnClickListener {
            val intent = Intent(requireActivity(), ChangePasswordActivity::class.java)
            startActivity(intent)
        }
    }
}