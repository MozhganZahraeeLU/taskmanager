package com.example.taskmanager

import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {

    private lateinit var editTextTask: EditText
    private lateinit var spinnerPriority: Spinner
    private lateinit var buttonAddTask: Button
    private lateinit var listViewTasks: ListView

    private lateinit var taskAdapter: ArrayAdapter<String>
    private val taskList = ArrayList<String>()

    private var selectedPriority: String = "Low" // Default priority

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        editTextTask = findViewById(R.id.editTextTask)
        spinnerPriority = findViewById(R.id.spinnerPriority)
        buttonAddTask = findViewById(R.id.buttonAddTask)
        listViewTasks = findViewById(R.id.listViewTasks)

        // Initialize the priority spinner
        ArrayAdapter.createFromResource(
            this,
            R.array.priority_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinnerPriority.adapter = adapter
        }

        // Set Spinner item selected listener
        spinnerPriority.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                selectedPriority = parent.getItemAtPosition(position).toString()
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                selectedPriority = "Low" // Default priority
            }
        }

        // Initialize task list and adapter
        taskAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, taskList)
        listViewTasks.adapter = taskAdapter

        // Set Button click listener
        buttonAddTask.setOnClickListener {
            addTask()
        }
    }

    private fun addTask() {
        val taskDescription = editTextTask.text.toString()
        if (taskDescription.isNotEmpty()) {
            val task = "$taskDescription - Priority: $selectedPriority"
            taskList.add(task)
            taskAdapter.notifyDataSetChanged()
            editTextTask.text.clear()
            Toast.makeText(this, "Task added", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "Please enter a task description", Toast.LENGTH_SHORT).show()
        }
    }
}
