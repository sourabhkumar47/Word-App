package com.example.wordsapp

import android.os.Bundle
import android.view.*
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.wordsapp.databinding.FragmentLetterListBinding

private var _binding: FragmentLetterListBinding? = null

//If you're certain a value won't be null when you access it, you can append !! to its type name
private val binding get() = _binding!!

//Property of recycle view
private lateinit var recyclerView: RecyclerView

private var isLinearLayoutManager = true



class LetterListFragment : Fragment() {

    //To display the options menu
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    //the layout is inflated in onCreateView()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLetterListBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    //Set value of recycle view property and call chooseLayout() same as MainActivity
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        recyclerView = binding.recyclerView
        chooseLayout()
    }



    private fun chooseLayout() {
        when (isLinearLayoutManager) {
            true -> {
                //here we can not pass this as the layout manager's context , have to pass context property
                recyclerView.layoutManager = LinearLayoutManager(context)
                recyclerView.adapter = LetterAdapter()
            }
            false -> {
                recyclerView.layoutManager = GridLayoutManager(context, 4)
                recyclerView.adapter = LetterAdapter()
            }
        }
    }

    //reset the binding property as view no longer exist
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    //Activity has menuInflater , Fragment doesn't have
    //it has onCreateOptionMenu
    //it doesn't require return statement

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.layout_menu, menu)

        val layoutbutton = menu.findItem(R.id.action_switch_layout)
        setIcon(layoutbutton)
    }

    private fun setIcon(menuItem: MenuItem?) {
        if (menuItem == null)
            return

        menuItem.icon =
            if (isLinearLayoutManager)
                ContextCompat.getDrawable(this.requireContext(), R.drawable.ic_grid_layout)
            else
                ContextCompat.getDrawable(this.requireContext(), R.drawable.ic_linear_layout)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.action_switch_layout -> {
                isLinearLayoutManager = !isLinearLayoutManager
                chooseLayout()
                setIcon(item)

                return true
            }

        else    -> super.onOptionsItemSelected(item)
        }
    }

}