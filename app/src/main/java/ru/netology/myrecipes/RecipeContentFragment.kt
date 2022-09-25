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
import ru.netology.myrecipes.databinding.RecipeContentFragmentBinding

class RecipeContentFragment : Fragment() {

    private val args by navArgs<RecipeContentFragmentArgs>()
    val viewModel by viewModels<RecipeViewModel>(
        ownerProducer = ::requireParentFragment
    )
    var uri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.addStepEvent.observe(this){
            val direction = RecipeContentFragmentDirections.actionRecipeContentFragmentToAddStepFragment()
            findNavController().navigate(direction)
        }

        viewModel.ImageEvent.observe(this)
        {
            val pickFromGallery =
                Intent().apply{
                    action = Intent.ACTION_GET_CONTENT
                    type = "image/*"
                    putExtra("uri",uri)
                }
            startActivity(pickFromGallery)
        }
        setFragmentResultListener(REQUEST_KEY_URI) { requestKey, bundle ->
            registerForActivityResult(ActivityResultContracts.GetContent()) {
                requireActivity().contentResolver.takePersistableUriPermission(
                    requireNotNull(it),
                    Intent.FLAG_GRANT_READ_URI_PERMISSION

                )
                if (requestKey != REQUEST_KEY_URI) return@registerForActivityResult
                 uri = Uri.parse(bundle.getString(
                    RESULT_KEY_URI
                )) ?: return@registerForActivityResult
            }
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
    viewModel.currentSteps.observe(viewLifecycleOwner){steps->
        stepAdapter.submitList(steps)
    }

    binding.enterAuthor.editText?.setText(args.author)
    binding.enterName.editText?.setText(args.name)
    binding.enterAuthor.requestFocus()

    binding.pick.setOnClickListener {
        viewModel.onImageClicked()
        val resultBundle = Bundle(1)
        resultBundle.putString(RESULT_KEY_URI, uri.toString())
        setFragmentResult(REQUEST_KEY_URI, resultBundle)
        binding.imagePreview.setImageURI(uri)
        binding.enterUri.text  = uri.toString()
    }
    binding.ok.setOnClickListener {
        val text = binding.enterAuthor.editText?.text.toString()
        val text2 = binding.enterName.editText?.text.toString()
        val text3 = binding.enterCategory.editText?.text.toString()
        val text4 = uri.toString()

        if (!text.isNullOrBlank() && !text2.isNullOrBlank() && !text3.isNullOrBlank()) {
            val resultBundle = Bundle(1)
            resultBundle.putStringArray(
                RESULT_KEY,
                arrayOf(text.toString(), text2.toString(), text3.toString(), text4)
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
}.root


companion object {
    const val REQUEST_KEY = "recipeAuthorRequestKey"
    const val RESULT_KEY = "recipeNewAuthor"
    const val REQUEST_KEY_URI = "imageReq"
    const val RESULT_KEY_URI = "imageRes"
}
}