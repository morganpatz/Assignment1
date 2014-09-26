package com.example.assignment1;

import java.util.ArrayList;

import android.os.Bundle;
import android.view.Menu;
import android.widget.TextView;

public class Stats extends ToDoMain {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_stats);

		todoCheck();

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.stats, menu);
		return true;
	}

	private void todoCheck() {

		TextView checked = (TextView) findViewById(R.id.todoCheck);
		TextView unchecked = (TextView) findViewById(R.id.todoUncheck);

		int checkCounter = 0;
		int uncheckCounter = 0;
		
		ArrayList<Task> taskList = dataAdapter.taskList1;
		for (int i = 0; i < taskList.size(); i++) {
			Task task = taskList.get(i);
			if (task.isSelected()) {
				responseText.append("\n" + task.getName());
			}
		}

		if (tasks != null) {
			for (int i = 0; i < tasks.size(); i++) {
				if (tasks.get(i).isSelected()) {
					checkCounter++;
				} else {
					uncheckCounter++;
				}
			}
		} else {
			System.out.println("uh oh");
		}
		int size = 0;

		if (tasks != null) {
			size = tasks.size();
		}

		checked.setText("Checked ToDo Items: " + size);
		unchecked.setText("Unchecked ToDo Items: " + uncheckCounter);

	}

}
