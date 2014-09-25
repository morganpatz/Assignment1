package com.example.assignment1;

import java.util.ArrayList;


import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class CheckList extends Activity {
	
	MyCustomAdapter dataAdapter = null;
	

	
	protected void displayListView() {

		// Array list of countries
		ArrayList<Task> taskList = new ArrayList<Task>();

		Task tasks = new Task("First todo", false);
		taskList.add(tasks);
		
		
		
		

		// create an ArrayAdaptar from the String Array
		dataAdapter = new MyCustomAdapter(this, R.layout.task_info, taskList);
		ListView todoList = (ListView) findViewById(R.id.listView1);
		// Assign adapter to ListView
		todoList.setAdapter(dataAdapter);
		
	

		todoList.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// When clicked, show a toast with the TextView text
				Task task = (Task) parent.getItemAtPosition(position);
				Toast.makeText(getApplicationContext(),
						"Clicked on Row: " + task.getName(), Toast.LENGTH_LONG)
						.show();
			}
		});
	}

	public class MyCustomAdapter extends ArrayAdapter<Task> {

		private ArrayList<Task> taskList;

		public MyCustomAdapter(Context context, int textViewResourceId,
				ArrayList<Task> taskList) {
			super(context, textViewResourceId, taskList);
			this.taskList = new ArrayList<Task>();
			this.taskList.addAll(taskList);
		}

		private class ViewHolder {
			CheckBox name;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {

			ViewHolder holder = null;

			Log.v("ConvertView", String.valueOf(position));

			if (convertView == null) {

				LayoutInflater vi = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

				convertView = vi.inflate(R.layout.task_info, null);

				holder = new ViewHolder();
				holder.name = (CheckBox) convertView
						.findViewById(R.id.checkBox1);

				convertView.setTag(holder);

				holder.name.setOnClickListener(new View.OnClickListener() {
					public void onClick(View v) {
						CheckBox cb = (CheckBox) v;
						Task tasks = (Task) cb.getTag();

						Toast.makeText(
								getApplicationContext(),
								"Clicked on Checkbox: " + cb.getText() + " is "
										+ cb.isChecked(), Toast.LENGTH_LONG)
								.show();

						tasks.setSelected(cb.isChecked());
					}
				});

			} else {
				holder = (ViewHolder) convertView.getTag();
			}

			Task task = taskList.get(position);

			holder.name.setText(task.getName());
			holder.name.setChecked(task.isSelected());

			holder.name.setTag(task);

			return convertView;
		}

	}

	protected void checkButtonClick() {

		ImageButton myButton = (ImageButton) findViewById(R.id.btn_check);

		myButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				StringBuffer responseText = new StringBuffer();
				responseText.append("The following were selected...\n");

				ArrayList<Task> taskList = dataAdapter.taskList;

				for (int i = 0; i < taskList.size(); i++) {
					Task task = taskList.get(i);

					if (task.isSelected()) {
						responseText.append("\n" + task.getName());
					}
				}

				Toast.makeText(getApplicationContext(), responseText,
						Toast.LENGTH_LONG).show();
			}
		});
	}

	private void newTask() {

	}
}
