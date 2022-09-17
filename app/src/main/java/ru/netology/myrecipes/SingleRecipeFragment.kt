package ru.netology.myrecipes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import ru.netology.myrecipes.databinding.FragmentSingleRecipeBinding


class SingleRecipeFragment : Fragment() {
    private val args by navArgs<SingleRecipeFragmentArgs>()

    val viewModel by viewModels<RecipeViewModel>(
        ownerProducer = ::requireParentFragment
    )
    //val singlePostVM by viewModels<SinglePostViewModel>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        viewModel.playVideoEvent.observe(this)
//        { video ->
//            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(video))
//            val playIntent = Intent.createChooser(intent, "Choose app")
//            startActivity(playIntent)
//        }
//        viewModel.sharePost.observe(this)
//        { postContent ->
//            val intent = Intent().apply {
//                action = Intent.ACTION_SEND
//                putExtra(Intent.EXTRA_TEXT, postContent)
//                type = "text/plain"
//            }
//            val shareIntent =
//                Intent.createChooser(intent, getString(R.string.chooser_share_post))
//            startActivity(shareIntent)
//        }

        setFragmentResultListener(RecipeContentFragment.REQUEST_KEY)
        { requestKey, bundle ->
            if (requestKey != RecipeContentFragment.REQUEST_KEY) return@setFragmentResultListener// edit here!!!
            val newContent = bundle.getStringArray(
                RecipeContentFragment.RESULT_KEY
            ) ?: return@setFragmentResultListener
            viewModel.contentArray = newContent
            viewModel.onSaveClicked(newContent)

            viewModel.navigateToEditScreenEvent.observe(this)
            { initialContent ->
                val direction =
                    SingleRecipeFragmentDirections.actionSingleRecipeFragmentToRecipeContentFragment(
                        initialContent?.get(0), initialContent?.get(1), initialContent?.get(2)
                    )
                findNavController().navigate(direction)
            }

//        viewModel.navigateToFirstFragment.observe(this){
//            findNavController().popBackStack()
//        }
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
        }.root


}