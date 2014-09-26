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

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class ToDoMain extends Activity {

		private static final String FILENAME = "file.sav";
		private EditText newTask;
		private ListView taskList;
		public ArrayList<Task> tasks;
		private MyCustomAdapter dataAdapter = null;

		/** Called when the activity is first created. */
		@Override
		protected void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			setContentView(R.layout.activity_todo);

			newTask = (EditText) findViewById(R.id.editText1);
			Button saveButton = (Button) findViewById(R.id.add);
			taskList = (ListView) findViewById(R.id.listView1);

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

		@Override
		protected void onStart() {
			// TODO Auto-generated method stub
			super.onStart();
			loadFromFile();
			if (tasks == null) {
				tasks = new ArrayList<Task>();
			}
			dataAdapter = new MyCustomAdapter(this, R.layout.task_info, tasks);
			taskList.setAdapter(dataAdapter);
		}

		private void loadFromFile() {
			try {
				FileInputStream fis = openFileInput(FILENAME);
				BufferedReader in = new BufferedReader(new InputStreamReader(fis));
				Gson gson = new Gson();
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
		
		
		public class MyCustomAdapter extends ArrayAdapter<Task> {
			private ArrayList<Task> list;

			public MyCustomAdapter(Context context, int textViewResourceId,
					ArrayList<Task> taskList) {
				super(context, textViewResourceId, taskList);
				this.list = new ArrayList<Task>();
				this.list.addAll(taskList);
			}

			/**
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
					holder.name = (CheckBox) convertView.findViewById(R.id.checkBox1);
					convertView.setTag(holder);
					holder.name.setOnClickListener(new View.OnClickListener() {
						public void onClick(View v) {
							CheckBox cb = (CheckBox) v;
							Task task = (Task) cb.getTag();
							task.setSelected(cb.isChecked());	
						}
					});
				} else {
					holder = (ViewHolder) convertView.getTag();
				}
				Task task = taskList1.get(position);
				holder.name.setText(task.getName());
				holder.name.setChecked(task.isSelected());
				holder.name.setTag(task);
				return convertView;
			}
			**/

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
	
}