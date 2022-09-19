package ru.netology.myrecipes

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import ru.netology.myrecipes.databinding.ListFragmentBinding
import java.util.Locale.filter

class ListFragment : Fragment() {
    val viewModel by viewModels<RecipeViewModel>(
        ownerProducer = ::requireParentFragment
    )

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


//        val searchView = it.includedList.searchView
//        searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener,
//            android.widget.SearchView.OnQueryTextListener {
//            override fun onQueryTextSubmit(query: String): Boolean{
//                if(query.isBlank()) return false
//                adapter.submitList(viewModel.data.value?.filter { it.name.contains(query,true) })
//                return true
//            }
//            override fun onQueryTextChange(newQuery: String): Boolean{
//                if(newQuery.isBlank()) {
//                    adapter.submitList(viewModel.data.value)
//                    return false
//                }
//                adapter.submitList(viewModel.data.value?.filter { it.name.contains(newQuery,true) })
//                return false
//            }
//        })
//
        val menuHost: MenuHost = requireActivity()
        // val adapter = RecipesAdapter(viewModel)


        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.top_toolbar_menu, menu)

                val search = menu.findItem(R.id.search)
                val searchView: SearchView = search.actionView as SearchView
                searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
                    android.widget.SearchView.OnQueryTextListener {
                    override fun onQueryTextSubmit(query: String): Boolean {
                        if (query.isBlank()) return false
                        adapter.submitList(viewModel.data.value?.filter {
                            it.name.contains(
                                query,
                                true
                            )
                        })
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
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                // Handle the menu selection
                return when (menuItem.itemId) {
                    R.id.search -> {
                        true
                    }
                    R.id.filter -> {
                        // loadTasks(true)
                        true
                    }
                    else -> false
                }
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)

    }.root


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

    }

    companion object {
        const val TAG = "feedFragment"
    }
}


