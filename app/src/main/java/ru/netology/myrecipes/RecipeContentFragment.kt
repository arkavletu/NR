package ru.netology.myrecipes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import ru.netology.myrecipes.databinding.RecipeContentFragmentBinding

class RecipeContentFragment: Fragment() {

    private val args by navArgs<RecipeContentFragmentArgs>()
    val viewModel by viewModels<RecipeViewModel>(
        ownerProducer = ::requireParentFragment
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = RecipeContentFragmentBinding.inflate(layoutInflater, container, false).also { binding ->
        val items = Categories.values()
        val adapter = ArrayAdapter(viewModel.getApplication(), R.layout.list_category, items)
        (binding.enterCategory.editText as? AutoCompleteTextView)?.setAdapter(adapter)

        binding.enterAuthor.editText?.setText(args.author)
        binding.enterName.editText?.setText(args.name)
        binding.enterAuthor.requestFocus()
        //binding.enterAuthor.showKeyboard()

        binding.ok.setOnClickListener {
            val text = binding.enterAuthor.editText?.text.toString()
            val text2 = binding.enterName.editText?.text.toString()
            val text3 = binding.enterCategory.editText?.text.toString()
            if (!text.isNullOrBlank()&&!text2.isNullOrBlank()&&!text3.isNullOrBlank()) {
                val resultBundle = Bundle(1)
                resultBundle.putStringArray(RESULT_KEY, arrayOf(text.toString(),text2.toString(),text3.toString()))
                setFragmentResult(REQUEST_KEY, resultBundle)
            }

            binding.enterAuthor.editText?.text?.clear()
            binding.enterName.editText?.text?.clear()
            binding.enterCategory.editText?.text?.clear()

            //binding.edit.hideKeyboard()
            findNavController().popBackStack()
        }
    }.root



    companion object {
        const val REQUEST_KEY = "recipeAuthorRequestKey"
        const val RESULT_KEY = "recipeNewAuthor"
    }
}