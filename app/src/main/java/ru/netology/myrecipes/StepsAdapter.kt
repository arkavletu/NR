package ru.netology.myrecipes

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.netology.myrecipes.ItemTouchHelper.TouchHelperAdapter
import ru.netology.myrecipes.databinding.StepBinding
import java.util.*
import kotlin.collections.ArrayList

class StepsAdapter(
    private val actionListener: RecipeActionListener

): ListAdapter<Step, StepsAdapter.ViewHolder>(DiffSearcher), TouchHelperAdapter {
    var data = ArrayList<Step>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StepsAdapter.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = StepBinding.inflate(inflater, parent, false)

        return ViewHolder(binding, actionListener)
    }

    override fun onBindViewHolder(holder: StepsAdapter.ViewHolder, position: Int) {

        holder.bind(getItem(position))
        data.add(getItem(position))

    }

    inner class ViewHolder(
        private val binding: StepBinding,
        listener: RecipeActionListener
    ) :
        RecyclerView.ViewHolder(binding.root) {
        private lateinit var step: Step


        fun bind(step: Step) {
            this.step = step
            with(binding)
            {
                textStep.text = step.text
                if(step.imageUrl.isBlank()) {
                    imageStep.setImageResource(R.drawable.no_image)
                } else imageStep.setImageURI(Uri.parse(step.imageUrl))

            }
        }
    }

    private object DiffSearcher : DiffUtil.ItemCallback<Step>() {
        override fun areItemsTheSame(oldItem: Step, newItem: Step) = oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Step, newItem: Step) = oldItem == newItem

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