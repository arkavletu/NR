package ru.netology.myrecipes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import ru.netology.myrecipes.ItemTouchHelper.Callback
import ru.netology.myrecipes.databinding.FavoriteRecipesFragmentBinding


class FavoriteRecipesFragment : Fragment() {
    val viewModel by viewModels<RecipeViewModel>(
        ownerProducer = ::requireParentFragment
    )


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)



        setFragmentResultListener(RecipeContentFragment.REQUEST_KEY)
        { requestKey, bundle ->
            if (requestKey != RecipeContentFragment.REQUEST_KEY) return@setFragmentResultListener
            val newContent = bundle.getStringArray(
                RecipeContentFragment.RESULT_KEY
            ) ?: return@setFragmentResultListener
            viewModel.contentArray = newContent
            viewModel.onSaveClicked(newContent)
        }




        viewModel.navigateToEditScreenEvent.observe(this)
        { initialContent ->
            val direction =
                FavoriteRecipesFragmentDirections.actionFavoriteRecipesFragmentToRecipeContentFragment(
                    initialContent?.get(0), initialContent?.get(1), initialContent?.get(2)
                )
            findNavController().navigate(direction)

        }

        viewModel.navigateToPostFragment.observe(this)
        { id ->
            val direction =
                FavoriteRecipesFragmentDirections.actionFavoriteRecipesFragmentToSingleRecipeFragment(
                    id
                )
            findNavController().navigate(direction)

        }


    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = FavoriteRecipesFragmentBinding.inflate(layoutInflater, container, false).also {
        val adapter = RecipesAdapter(viewModel)
        it.includedListFavorites.list.adapter = adapter
        viewModel.currentFavorites.observe(viewLifecycleOwner) { recipes ->
            adapter.submitList(recipes)
        }
        val searchView = it.includedListFavorites.search
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
            android.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                if (query.isBlank()) return false
                adapter.submitList(viewModel.currentFavorites.value?.filter {
                    it.name.contains(
                        query,
                        true
                    )
                })
                return true
            }

            override fun onQueryTextChange(newQuery: String): Boolean {
                if (newQuery.isBlank()) {
                    adapter.submitList(viewModel.currentFavorites.value)
                    return false
                }
                adapter.submitList(viewModel.currentFavorites.value?.filter {
                    it.name.contains(
                        newQuery,
                        true
                    )
                })
                return false
            }
        })
        it.includedListFavorites.filterIcon.setOnClickListener {
            val singleItems = arrayOf("All") + Categories.array
            var checkedItem = 0
            MaterialAlertDialogBuilder(requireActivity())
                .setTitle(resources.getString(R.string.category))
                .setPositiveButton(resources.getString(R.string.save_me)) { dialog, which ->
                    if (checkedItem == 0) adapter.submitList(viewModel.currentFavorites.value)
                    adapter.submitList(viewModel.currentFavorites.value?.filter {
                        it.category == singleItems[checkedItem] && it.isFavorite == true
                    })
                    // Respond to positive button press
                }.setSingleChoiceItems(singleItems, checkedItem) { dialog, which ->
                    checkedItem = which
                }
                .show()
        }

        val callback: ItemTouchHelper.Callback = Callback(adapter)
        val touchHelper = ItemTouchHelper(callback)
        touchHelper.attachToRecyclerView(it.includedListFavorites.list)
    }.root

}