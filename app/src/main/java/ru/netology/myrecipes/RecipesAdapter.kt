package ru.netology.myrecipes


import android.net.Uri.parse
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.PopupMenu
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.netology.myrecipes.ItemTouchHelper.TouchHelperAdapter
import ru.netology.myrecipes.databinding.RecipeBinding
import java.util.*
import kotlin.collections.ArrayList


class RecipesAdapter(

    private val actionListener: RecipeActionListener
) : ListAdapter<Recipe, RecipesAdapter.ViewHolder>(DiffSearcher), TouchHelperAdapter {
    var data = ArrayList<Recipe>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = RecipeBinding.inflate(inflater, parent, false)

        return ViewHolder(binding, actionListener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.bind(getItem(position))
        data.add(getItem(position))
        holder.itemView.setOnClickListener {
            actionListener.onPostClicked(getItem(position).id)
        }
    }


    inner class ViewHolder(
        private val binding: RecipeBinding,
        listener: RecipeActionListener
    ) :
        RecyclerView.ViewHolder(binding.root) {
        private lateinit var recipe: Recipe

        private val popupMenu by lazy {
            PopupMenu(itemView.context, binding.options).apply {
                inflate(R.menu.options)
                setOnMenuItemClickListener {
                    when (it.itemId) {
                        R.id.remove -> {
                            listener.onDeleteClicked(recipe)
                            true
                        }
                        R.id.edit -> {

                            listener.onEditClicked(recipe)
                            true
                        }
//
                        else -> false
                    }

                }
            }
        }

        init {
            binding.favor.setOnClickListener {
                listener.onLikeClicked(recipe)
            }

        }

        fun bind(recipe: Recipe) {
            this.recipe = recipe
            with(binding)
            {
                author.text = recipe.author
                name.text = recipe.name
                category.text = recipe.category
                options.setOnClickListener { popupMenu.show() }
                favor.isChecked = recipe.isFavorite
                if(recipe.imageUrl.isBlank()) {
                    image.setImageResource(R.drawable.no_image)
                } else image.setImageURI(parse(recipe.imageUrl))


            }
        }
    }

    private object DiffSearcher : DiffUtil.ItemCallback<Recipe>() {
        override fun areItemsTheSame(oldItem: Recipe, newItem: Recipe) = oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Recipe, newItem: Recipe) = oldItem == newItem

    }

    override fun onMove(fromPosition: Int, toPosition: Int) {
        if (fromPosition < toPosition) {
            for (i in fromPosition until toPosition) {
                Collections.swap(data, i, i + 1)
            }
        } else {
            for (i in fromPosition downTo toPosition + 1) {
                Collections.swap(data, i, i - 1)
            }
        }
        notifyItemMoved(fromPosition, toPosition)

    }

    override fun onItemDismiss(position: Int) {
        data.removeAt(position)
        notifyItemRemoved(position)
    }




}