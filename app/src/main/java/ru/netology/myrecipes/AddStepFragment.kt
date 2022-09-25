package ru.netology.myrecipes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import ru.netology.myrecipes.databinding.AddStepFragmentBinding
import ru.netology.myrecipes.databinding.RecipeContentFragmentBinding

class AddStepFragment: Fragment() {
    val viewModel by viewModels<RecipeViewModel>(
        ownerProducer = ::requireParentFragment
    )
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }
//пока нет загрузки картинки
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    )= AddStepFragmentBinding.inflate(layoutInflater, container, false).also { binding ->
      binding.ok.setOnClickListener {
          viewModel.saveStep(binding.descriptionStep.editText?.text.toString(),null)
          findNavController().popBackStack()
      }
    }.root



}