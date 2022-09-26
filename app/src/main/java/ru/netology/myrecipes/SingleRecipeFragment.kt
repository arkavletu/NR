package ru.netology.myrecipes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.ItemTouchHelper
import ru.netology.myrecipes.ItemTouchHelper.Callback
import ru.netology.myrecipes.databinding.FragmentSingleRecipeBinding


class SingleRecipeFragment : Fragment() {
    private val args by navArgs<SingleRecipeFragmentArgs>()

    val viewModel by viewModels<RecipeViewModel>(
        ownerProducer = ::requireParentFragment
    )


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        setFragmentResultListener(RecipeContentFragment.REQUEST_KEY)
        { requestKey, bundle ->
            if (requestKey != RecipeContentFragment.REQUEST_KEY) return@setFragmentResultListener// edit here!!!
            val newContent = bundle.getStringArray(
                RecipeContentFragment.RESULT_KEY
            ) ?: return@setFragmentResultListener
            viewModel.contentArray = newContent
            viewModel.onSaveClicked(newContent)
        }
            viewModel.navigateToEditScreenEvent.observe(this)
            { initialContent ->
                val direction =
                    SingleRecipeFragmentDirections.actionSingleRecipeFragmentToRecipeContentFragment(
                        initialContent?.get(0), initialContent?.get(1), initialContent?.get(2)
                    )
                findNavController().navigate(direction)
            }



    }

        override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
        ) = FragmentSingleRecipeBinding.inflate(layoutInflater, container, false).also {
            val id = args.recipeId
            val adapter = RecipesAdapter(viewModel)
            it.oneRecipe.list.adapter = adapter
            viewModel.data.observe(viewLifecycleOwner) { recipes ->
                adapter.submitList(recipes.filter { it.id == id })
            }

            val stepsAdapter = StepsAdapter(viewModel)
            it.listSteps.adapter = stepsAdapter
            viewModel.getStepsForRecipe(id).observe(viewLifecycleOwner){steps->
                stepsAdapter.submitList(steps)
            }







        }.root


}