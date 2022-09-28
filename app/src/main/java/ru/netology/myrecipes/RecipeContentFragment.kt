package ru.netology.myrecipes

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.ItemTouchHelper
import ru.netology.myrecipes.ItemTouchHelper.Callback
import ru.netology.myrecipes.databinding.RecipeContentFragmentBinding

class RecipeContentFragment : Fragment() {

    private val args by navArgs<RecipeContentFragmentArgs>()
    val viewModel by viewModels<RecipeViewModel>(
        ownerProducer = ::requireParentFragment
    )
    var uri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.addStepEvent.observe(this) {
            val direction =
                RecipeContentFragmentDirections.actionRecipeContentFragmentToAddStepFragment()
            findNavController().navigate(direction)
        }

    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = RecipeContentFragmentBinding.inflate(layoutInflater, container, false).also { binding ->
        val items = Categories.values()
        val adapter = ArrayAdapter(viewModel.getApplication(), R.layout.list_category, items)
        (binding.enterCategory.editText as? AutoCompleteTextView)?.setAdapter(adapter)

        val stepAdapter = StepsAdapter(viewModel)
        binding.listSteps.adapter = stepAdapter

       viewModel.getStepsForRecipe(viewModel.currentRecipe.value?.id?:0L).observe(viewLifecycleOwner){steps ->
           stepAdapter.submitList(steps)
       }



        binding.enterAuthor.editText?.setText(args.author)
        binding.enterName.editText?.setText(args.name)
        binding.enterCategory.editText?.setText(args.category)
        binding.enterAuthor.requestFocus()

        val addingMainImageLauncher = registerForActivityResult(ActivityResultContracts.OpenDocument()) { uri ->
            uri ?: return@registerForActivityResult
            requireActivity().contentResolver.takePersistableUriPermission(uri, Intent.FLAG_GRANT_READ_URI_PERMISSION)

            binding.imagePreview.setImageURI(uri)
            this.uri = uri
        }
        viewModel.ImageEvent.observe(viewLifecycleOwner) {
            addingMainImageLauncher.launch(arrayOf("image/*"))
        }
        binding.pick.setOnClickListener {
            viewModel.onImageClicked()
        }
        binding.ok.setOnClickListener {
            val text = binding.enterAuthor.editText?.text.toString()
            val text2 = binding.enterName.editText?.text.toString()
            val text3 = binding.enterCategory.editText?.text.toString()
            val text4 = uri.toString()

            if (!text.isBlank() && !text2.isBlank() && !text3.isBlank()&& !stepAdapter.currentList.isEmpty()) {
                val resultBundle = Bundle(1)
                resultBundle.putStringArray(
                    RESULT_KEY,
                    arrayOf(text, text2, text3, text4)
                )
                setFragmentResult(REQUEST_KEY, resultBundle)
            }



            binding.enterAuthor.editText?.text?.clear()
            binding.enterName.editText?.text?.clear()
            binding.enterCategory.editText?.text?.clear()

            findNavController().popBackStack()
        }

        binding.addStep.setOnClickListener {
            viewModel.addStep()
        }

        val callback: ItemTouchHelper.Callback = Callback(stepAdapter)
        val touchHelper = ItemTouchHelper(callback)
        touchHelper.attachToRecyclerView(binding.listSteps)

    }.root


    companion object {
        const val REQUEST_KEY = "recipeAuthorRequestKey"
        const val RESULT_KEY = "recipeNewAuthor"
    }
}