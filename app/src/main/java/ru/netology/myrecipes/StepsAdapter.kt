package ru.netology.myrecipes

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.netology.myrecipes.databinding.StepBinding

class StepsAdapter(
    private val actionListener: RecipeActionListener

): ListAdapter<Step, StepsAdapter.ViewHolder>(DiffSearcher) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StepsAdapter.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = StepBinding.inflate(inflater, parent, false)

        return ViewHolder(binding, actionListener)
    }

    override fun onBindViewHolder(holder: StepsAdapter.ViewHolder, position: Int) {

        holder.bind(getItem(position))


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

                if(step.imageUrl.isNullOrBlank()) {
                    imageStep.setImageResource(R.drawable.no_image)
                }

            }
        }
    }

    private object DiffSearcher : DiffUtil.ItemCallback<Step>() {
        override fun areItemsTheSame(oldItem: Step, newItem: Step) = oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Step, newItem: Step) = oldItem == newItem

    }


}