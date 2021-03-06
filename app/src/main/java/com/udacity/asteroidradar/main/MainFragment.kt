package com.udacity.asteroidradar.main

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.udacity.asteroidradar.R
import com.udacity.asteroidradar.adapter.AsteroidAdapter
import com.udacity.asteroidradar.database.AsteroidsDatabase
import com.udacity.asteroidradar.databinding.FragmentMainBinding
import com.udacity.asteroidradar.repository.Repository

class MainFragment : Fragment() {

    private val viewModel: MainViewModel by lazy {
        ViewModelProvider(this).get(MainViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentMainBinding.inflate(inflater)

        val dao = AsteroidsDatabase.getInstance(requireContext()).asteroidDao
        val repo = Repository(dao)
        val factory = MainViewModelFactory(repo)

        val viewModel = ViewModelProvider(this, factory).get(MainViewModel::class.java)

        val asteroidAdapter = AsteroidAdapter(AsteroidAdapter.OnClickListener {
            viewModel.showAstroidDetail(it)
        })

        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        binding.asteroidRecycler.adapter = asteroidAdapter

        viewModel.moveToSelectedAsteroid.observe(viewLifecycleOwner, Observer {
            if (null != it) {
                findNavController().navigate(MainFragmentDirections.actionShowDetail(it))
                viewModel.showAstroidDetailDone()
            }
            // viewModel.showAstroidDetailDone()
        })

        setHasOptionsMenu(true)

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_overflow_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return true
    }
}
