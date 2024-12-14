package com.example.journalingapp

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import com.example.journalingapp.databinding.FragmentProfileBinding
import java.io.IOException

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private val pickImageRequestCode = 100

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Set up the gender spinner
        val genderOptions = resources.getStringArray(R.array.gender_options)
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, genderOptions)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerGender.adapter = adapter

        // Load saved profile data
        loadProfileData()

        // Handle Add/Change Profile Picture Button
        binding.btnChangeProfilePicture.setOnClickListener {
            openImagePicker()
        }

        // Handle Save Personal Information Button
        binding.btnSaveProfile.setOnClickListener {
            val name = binding.etName.text.toString()
            val email = binding.etEmail.text.toString()
            val phoneNumber = binding.etPhoneNumber.text.toString()
            val address = binding.etAddress.text.toString()
            val dateOfBirth = binding.etDateOfBirth.text.toString()
            val gender = binding.spinnerGender.selectedItem.toString()
            val aboutMe = binding.etAboutMe.text.toString()
            val instagram = binding.etInstagram.text.toString()
            val twitter = binding.etTwitter.text.toString()
            val linkedIn = binding.etLinkedIn.text.toString()

            // Save the data
            saveProfileData(name, email, phoneNumber, address, dateOfBirth, gender, aboutMe, instagram, twitter, linkedIn)
        }
    }

    private fun openImagePicker() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(intent, pickImageRequestCode)
    }

    private fun saveProfileData(
        name: String, email: String, phoneNumber: String, address: String,
        dateOfBirth: String, gender: String, aboutMe: String,
        instagram: String, twitter: String, linkedIn: String
    ) {
        // Save to SharedPreferences
        val sharedPreferences = requireContext().getSharedPreferences("UserProfile", Activity.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString("Name", name)
        editor.putString("Email", email)
        editor.putString("PhoneNumber", phoneNumber)
        editor.putString("Address", address)
        editor.putString("DateOfBirth", dateOfBirth)
        editor.putString("Gender", gender)
        editor.putString("AboutMe", aboutMe)
        editor.putString("Instagram", instagram)
        editor.putString("Twitter", twitter)
        editor.putString("LinkedIn", linkedIn)
        editor.apply()
    }

    private fun loadProfileData() {
        // Load from SharedPreferences
        val sharedPreferences = requireContext().getSharedPreferences("UserProfile", Activity.MODE_PRIVATE)
        binding.etName.setText(sharedPreferences.getString("Name", ""))
        binding.etEmail.setText(sharedPreferences.getString("Email", ""))
        binding.etPhoneNumber.setText(sharedPreferences.getString("PhoneNumber", ""))
        binding.etAddress.setText(sharedPreferences.getString("Address", ""))
        binding.etDateOfBirth.setText(sharedPreferences.getString("DateOfBirth", ""))
        val gender = sharedPreferences.getString("Gender", "")
        binding.spinnerGender.setSelection(
            (binding.spinnerGender.adapter as ArrayAdapter<String>).getPosition(gender)
        )
        binding.etAboutMe.setText(sharedPreferences.getString("AboutMe", ""))
        binding.etInstagram.setText(sharedPreferences.getString("Instagram", ""))
        binding.etTwitter.setText(sharedPreferences.getString("Twitter", ""))
        binding.etLinkedIn.setText(sharedPreferences.getString("LinkedIn", ""))
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == pickImageRequestCode && resultCode == Activity.RESULT_OK) {
            val selectedImageUri: Uri? = data?.data
            try {
                val bitmap: Bitmap? = MediaStore.Images.Media.getBitmap(requireContext().contentResolver, selectedImageUri)
                binding.ivProfilePicture.setImageBitmap(bitmap)

                // Save the image URI to SharedPreferences
                val sharedPreferences = requireContext().getSharedPreferences("UserProfile", Activity.MODE_PRIVATE)
                sharedPreferences.edit().putString("ProfilePictureUri", selectedImageUri.toString()).apply()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }

    private fun loadProfilePicture() {
        val sharedPreferences = requireContext().getSharedPreferences("UserProfile", Activity.MODE_PRIVATE)
        val uriString = sharedPreferences.getString("ProfilePictureUri", null)
        uriString?.let {
            val uri = Uri.parse(it)
            try {
                val bitmap = MediaStore.Images.Media.getBitmap(requireContext().contentResolver, uri)
                binding.ivProfilePicture.setImageBitmap(bitmap)
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
