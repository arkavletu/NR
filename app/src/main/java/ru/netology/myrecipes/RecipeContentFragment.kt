package ru.netology.myrecipes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.widget.PopupMenu
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
        val popupMenu by lazy {
            this.context?.let {
                PopupMenu(it, binding.category).apply {
                    inflate(R.menu.category_options)
                    setOnMenuItemClickListener {
                        when (it.itemId) {
                            R.id.eu -> {binding.enterCategory.text = "European"
                            true}
                            R.id.east ->{binding.enterCategory.text = "Eastern"
                                true}
                            R.id.asia -> {binding.enterCategory.text = "Asian"
                                true}
                            R.id.pan_asiatic -> {binding.enterCategory.text = "Pan-Asiatic"
                                true}
                            R.id.am -> {binding.enterCategory.text = "American"
                                true}
                            R.id.rus -> {binding.enterCategory.text = "Russian"
                                true}
                            R.id.med -> {binding.enterCategory.text = "Mediterranean"
                                true}
                            else -> false

                        }

                    }
                }
            }
        }
        binding.enterAuthor.setText(args.author)
        binding.enterName.setText(args.name)//arguments
        binding.enterCategory.setText(args.category)
        binding.enterAuthor.requestFocus()
        //binding.enterAuthor.showKeyboard()
        binding.category.setOnClickListener{
         popupMenu?.show() }
        binding.ok.setOnClickListener {
            val text = binding.enterAuthor.text
            val text2 = binding.enterName.text
            val text3 = binding.enterCategory.text
            if (!text.isNullOrBlank()&&!text2.isNullOrBlank()) {
                val resultBundle = Bundle(1)
                resultBundle.putStringArray(RESULT_KEY, arrayOf(text.toString(),text2.toString(),text3.toString()))
                setFragmentResult(REQUEST_KEY, resultBundle)
            }
//            if(!text2.isNullOrBlank()){
//                val resultBundle = Bundle(1)
//                resultBundle.putString(RESULT_KEY2, text.toString())
//                setFragmentResult(REQUEST_KEY2, resultBundle)
//            }
            binding.enterAuthor.text?.clear()
            binding.enterName.text?.clear()
            binding.enterCategory.text = ""

            //binding.edit.hideKeyboard()
            findNavController().popBackStack()
        }
    }.root



    companion object {
        const val REQUEST_KEY = "recipeAuthorRequestKey"
        const val RESULT_KEY = "recipeNewAuthor"
//        const val REQUEST_KEY2 = "recipeNameRequestKey"
//        const val RESULT_KEY2 = "recipeNewName"




    }
}