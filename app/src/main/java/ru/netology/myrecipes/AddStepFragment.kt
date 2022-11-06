package ru.netology.myrecipes

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
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
    var uri: Uri? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    )= AddStepFragmentBinding.inflate(layoutInflater, container, false).also { binding ->
      binding.ok.setOnClickListener {
          viewModel.saveStep(binding.descriptionStep.editText?.text.toString(),uri.toString())
          val direction = AddStepFragmentDirections.actionAddStepFragmentToRecipeContentFragment()
          findNavController().navigate(direction)
      }
        binding.pick.setOnClickListener {
            viewModel.onImageClicked()
        }
        val addingMainImageLauncher = registerForActivityResult(ActivityResultContracts.OpenDocument()) { uri ->
            uri ?: return@registerForActivityResult
            requireActivity().contentResolver.takePersistableUriPermission(uri, Intent.FLAG_GRANT_READ_URI_PERMISSION)
            this.uri = uri
        }
        viewModel.ImageEvent.observe(viewLifecycleOwner) {
            addingMainImageLauncher.launch(arrayOf("image/*"))
        }
    }.root



}