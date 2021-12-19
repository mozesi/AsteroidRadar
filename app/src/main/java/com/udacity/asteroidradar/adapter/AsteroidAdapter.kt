package com.udacity.asteroidradar.adapter
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.databinding.AsteroidItemBinding

class AsteroidAdapter(val onClickListener: OnClickListener) : ListAdapter<Asteroid, AsteroidAdapter.AsteroidViewHolder>(DiffCallback){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AsteroidViewHolder {
        val binding =   AsteroidItemBinding.inflate(LayoutInflater.from(parent.context))
        return AsteroidViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AsteroidViewHolder, position: Int) {

        holder.itemView.setOnClickListener {
            onClickListener.onClick(getItem(position))
        }
        holder.bind(getItem(position))

    }

object DiffCallback : DiffUtil.ItemCallback<Asteroid>() {
    override fun areItemsTheSame(oldItem: Asteroid, newItem: Asteroid): Boolean {
        return oldItem === newItem
    }

    override fun areContentsTheSame(oldItem: Asteroid, newItem: Asteroid): Boolean {
        return oldItem.id == newItem.id
    }
}

class AsteroidViewHolder(private val binding : AsteroidItemBinding):RecyclerView.ViewHolder(binding.root) {
    fun bind(mAsteroid: Asteroid) {
        binding.asteroid = mAsteroid
        binding.executePendingBindings()
    }
}

class OnClickListener(val clickListener:(asteroid: Asteroid) -> Unit){
    fun onClick(asteroid: Asteroid)= clickListener(asteroid)
}

}


