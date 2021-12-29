package com.example.viviraapp_jonathancheli.ui.details

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.transition.TransitionInflater
import com.bumptech.glide.Glide
import com.example.viviraapp_jonathancheli.R
import com.example.viviraapp_jonathancheli.data.model.Repo
import com.example.viviraapp_jonathancheli.databinding.FragmentDetailsBinding
import com.example.viviraapp_jonathancheli.internal.DateUtils
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class DetailsFragment : Fragment(R.layout.fragment_details) {


    private var _binding: FragmentDetailsBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedElementEnterTransition =
            TransitionInflater.from(context).inflateTransition(android.R.transition.move)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)

        _binding = FragmentDetailsBinding.bind(view)

        val repo = arguments?.getParcelable<Repo>("KEY")


        binding.apply {
            name.text = repo!!.name
            username.text = repo.owner.login
            language.text = repo.language
            description.text = repo.description

            avatar.apply {
                transitionName = repo.owner.avatar_url
                Glide.with(view)
                    .load(repo.owner.avatar_url)
                    .error(android.R.drawable.stat_notify_error)
                    .into(this)
            }

            stars.text = repo.stars.toString()
            forks.text = repo.forks.toString()
            watchers.text = repo.watchers.toString()
            issuesOpened.text = repo.openIssues.toString()
            createDate.text = DateUtils.formatDate(repo.createDate)
            updateDate.text = DateUtils.formatDate(repo.updateDate)
            btnBrowse.setOnClickListener {
                val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(repo.url))
                startActivity(browserIntent)
            }

        }

      //  ViewCompat.setTransitionName(binding.avatar, "avatar_${repo!!.id}")

        setHasOptionsMenu(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                view?.let { Navigation.findNavController(it).navigateUp() }
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}