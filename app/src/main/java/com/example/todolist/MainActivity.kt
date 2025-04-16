package com.example.todolist

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.todolist.adapter.TaskAdapter
import com.example.todolist.data.Task
import com.example.todolist.databinding.ActivityMainBinding
import com.example.todolist.viewmodel.TaskViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var taskAdapter: TaskAdapter
    private lateinit var taskViewModel: TaskViewModel
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        taskAdapter = TaskAdapter(
            onItemClick = { task -> showTaskDetails(task) },
            onDeleteClick = { task -> deleteTask(task) },
            onToggleComplete = { task -> toggleTaskCompletion(task) }
        )

        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = taskAdapter

        taskViewModel = ViewModelProvider(this).get(TaskViewModel::class.java)

        taskViewModel.getAllTasks().observe(this, Observer { tasks ->
            tasks?.let {
                taskAdapter.submitList(it)
            }
        })

        binding.btnAddTask.setOnClickListener {
            addNewTask()
        }
    }

    private fun showTaskDetails(task: Task) {

    }

    private fun deleteTask(task: Task) {
        taskViewModel.deleteTask(task)
    }

    private fun toggleTaskCompletion(task: Task) {
        task.completed = !task.completed
        taskViewModel.updateTask(task)
    }

    private fun addNewTask() {

    }
}
