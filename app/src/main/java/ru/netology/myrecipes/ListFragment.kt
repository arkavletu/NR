package ru.netology.myrecipes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.core.view.allViews
import androidx.core.view.children
import androidx.customview.widget.ViewDragHelper
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.ItemTouchHelper.DOWN
import androidx.recyclerview.widget.ItemTouchHelper.UP
import com.google.android.material.card.MaterialCardView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import ru.netology.myrecipes.ItemTouchHelper.Callback
import ru.netology.myrecipes.databinding.ListFragmentBinding

class ListFragment : Fragment() {
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
            val direction = ListFragmentDirections.actionListFragmentToRecipeContentFragment(
                initialContent?.get(0), initialContent?.get(1), initialContent?.get(2)
            )
            findNavController().navigate(direction)

        }

        viewModel.navigateToPostFragment.observe(this)
        { id ->
            val direction = ListFragmentDirections.actionListFragmentToSingleRecipeFragment(id)
            findNavController().navigate(direction)

        }
        viewModel.navigateToFavoritesFragment.observe(this) {
            val direction = ListFragmentDirections.actionListFragmentToFavoriteRecipesFragment()
            findNavController().navigate(direction)
        }


    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = ListFragmentBinding.inflate(layoutInflater, container, false).also {
        val adapter = RecipesAdapter(viewModel)
        it.includedList.list.adapter = adapter
        viewModel.data.observe(viewLifecycleOwner) { recipes ->
            adapter.submitList(recipes)
        }


        val searchView = it.includedList.search
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
            android.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                if (query.isBlank()) return false
                adapter.submitList(viewModel.data.value?.filter { it.name.contains(query, true) })
                return true
            }

            override fun onQueryTextChange(newQuery: String): Boolean {
                if (newQuery.isBlank()) {
                    adapter.submitList(viewModel.data.value)
                    return false
                }
                adapter.submitList(viewModel.data.value?.filter {
                    it.name.contains(
                        newQuery,
                        true
                    )
                })
                return false
            }
        })
        it.includedList.filterIcon.setOnClickListener {
            val singleItems = arrayOf("All") + Categories.array
            var checkedItem = 0
            MaterialAlertDialogBuilder(requireActivity())
                .setTitle(resources.getString(R.string.category))
                .setSingleChoiceItems(singleItems, checkedItem) { dialog, which ->
                    checkedItem = which
                }.setPositiveButton(resources.getString(R.string.save_me)) { dialog, which ->
                    if (checkedItem == 0) adapter.submitList(viewModel.data.value)
                    else adapter.submitList(viewModel.data.value?.filter { it.category == singleItems[checkedItem] })
                }.show()
        }
        val callback: ItemTouchHelper.Callback = Callback(adapter)
        val touchHelper = ItemTouchHelper(callback)
        touchHelper.attachToRecyclerView(it.includedList.list)
    }.root

}


