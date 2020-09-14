package com.example.madlevel2task2

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.madlevel2task2.adapter.QuestionAdapter
import com.example.madlevel2task2.databinding.ActivityMainBinding
import com.example.madlevel2task2.model.QuestionModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val questions = arrayListOf<QuestionModel>()
    private val questionAdapter = QuestionAdapter(questions)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        initView()
    }

    private fun initView() {
        rvQuestions.layoutManager =
            LinearLayoutManager(this@MainActivity, RecyclerView.VERTICAL, false)
        rvQuestions.adapter = questionAdapter
        rvQuestions.addItemDecoration(
            DividerItemDecoration(
                this@MainActivity,
                DividerItemDecoration.VERTICAL
            )
        )

        for (i in QuestionModel.QUESTIONS.indices) {
            questions.add(QuestionModel(QuestionModel.QUESTIONS[i], QuestionModel.ANSWERS[i]))
        }
        questionAdapter.notifyDataSetChanged()

        createItemTouchHelper().attachToRecyclerView(rvQuestions)
    }

    private fun createItemTouchHelper(): ItemTouchHelper {
        val callback = object :
            ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {


            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }


            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition

                if ((direction == ItemTouchHelper.LEFT && !questions[position].answer) ||
                    (direction == ItemTouchHelper.RIGHT && questions[position].answer)
                ) {

                    Snackbar.make(
                        rvQuestions,
                        getString(R.string.correct, questions[position].answer),
                        Snackbar.LENGTH_SHORT
                    ).show()
                    questions.removeAt(position)
                    questionAdapter.notifyDataSetChanged()
                } else {
                    Snackbar.make(
                        rvQuestions,
                        getString(R.string.incorrect, questions[position].answer),
                        Snackbar.LENGTH_SHORT
                    ).show()
                }

                questionAdapter.notifyItemChanged(position)
            }

        }

        return ItemTouchHelper(callback)
    }
}