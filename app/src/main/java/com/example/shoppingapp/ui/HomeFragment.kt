package com.example.shoppingapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.models.SlideModel
import com.example.shoppingapp.R
import com.example.shoppingapp.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.fragment_home) {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentHomeBinding.bind(view)


        val imageSlider = binding.imageSlider
        val imageList = ArrayList<SlideModel>()

        imageList.add(SlideModel("https://source.unsplash.com/xXJ6utyoSw0/1600x900"))
        imageList.add(SlideModel("https://source.unsplash.com/quddu_dZKkQ/1600x900"))
        imageList.add(SlideModel("https://source.unsplash.com/zZLhoEwGCeM/1600x900"))
        imageList.add(SlideModel("https://source.unsplash.com/Ui3zMjpMrmM/1600x900"))
        imageSlider.setImageList(imageList, ScaleTypes.FIT)



        binding.electronicsImage.setOnClickListener {
            val action = HomeFragmentDirections.actionHomeFragmentToElectronicsFragment()
            findNavController().navigate(action)
        }

        binding.jewelryImage.setOnClickListener {
            val action = HomeFragmentDirections.actionHomeFragmentToJewelryFragment()
            findNavController().navigate(action)

        }

        binding.menClothingImage.setOnClickListener {
            val action = HomeFragmentDirections.actionHomeFragmentToMensClothingFragment()
            findNavController().navigate(action)
        }

        binding.womenClothingImage.setOnClickListener {
            val action = HomeFragmentDirections.actionHomeFragmentToWomensClothingFragment()
            findNavController().navigate(action)

        }



    }






}