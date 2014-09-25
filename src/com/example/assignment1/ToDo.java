package com.example.assignment1;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class ToDo extends Activity {
	MyCustomAdapter dataAdapter = null;
	private static final String FILENAME = "file.sav";
	private ArrayList<Task> tasks = new ArrayList<Task>();;
	private EditText newTask;
	private ListView todoList;


	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_todo);
		// Generate list View from ArrayList
		loadFromFile();
		//displayListView();
		checkButtonClick();
		newTask();
	}

	private void displayListView() {
		// Array list of countries
		ArrayList<Task> taskList = new ArrayList<Task>();
		Task tasks = new Task("First todo", false);
		taskList.add(tasks);
		// create an ArrayAdaptar from the String Array
		dataAdapter = new MyCustomAdapter(this, R.layout.task_info, taskList);
		ListView listView = (ListView) findViewById(R.id.listView1);
		// Assign adapter to ListView
		listView.setAdapter(dataAdapter);
		listView.setOnItemClickListener(new OnItemClickListener() {
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

	private void checkButtonClick() {
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

	private void loadFromFile() {
		
		try {
			FileInputStream fis = openFileInput(FILENAME);
			BufferedReader in = new BufferedReader(new InputStreamReader(fis));
			Gson gson = new Gson();
			dataAdapter = new MyCustomAdapter(this, R.layout.task_info, tasks);
			// Following line from
			// https://google-gson.googlecode.com/svn/trunk/gson/docs/javadocs/com/google/gson/Gson.html
			// on September 23, 2014
			Type listType = new TypeToken<ArrayList<Task>>() {
			}.getType();
			tasks = gson.fromJson(in, listType);

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void saveInFile() {
		try {
			FileOutputStream fos = openFileOutput(FILENAME, 0); //0 defaults to WRITE_MODE
			Gson gson = new Gson(); 
			OutputStreamWriter osw = new OutputStreamWriter(fos);
			gson.toJson(tasks,osw);
			osw.flush();
			fos.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	private void newTask() {
		
		newTask = (EditText) findViewById(R.id.editText1);
		ImageButton saveButton = (ImageButton) findViewById(R.id.btn_add);
		todoList = (ListView) findViewById(R.id.listView1);
	

		saveButton.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				setResult(RESULT_OK);
				String text = newTask.getText().toString();
				tasks.add(new Task(text, false));
				dataAdapter.notifyDataSetChanged();
				saveInFile();

			}
		});
	}

}