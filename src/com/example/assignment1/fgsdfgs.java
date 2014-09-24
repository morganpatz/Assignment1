package com.example.assignment1;

import java.util.ArrayList;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

private class MyCustomAdapter extends ArrayAdapter<Task> {
	 
	  private ArrayList<Task> countryList;
	 
	  public MyCustomAdapter(Context context, int textViewResourceId,
	    ArrayList<Task> countryList) {
	   super(context, textViewResourceId, countryList);
	   this.countryList = new ArrayList<Task>();
	   this.countryList.addAll(countryList);
	  }
	 
	  private class ViewHolder {
	   CheckBox name;
	  }
	 
	  @Override
	  public View getView(int position, View convertView, ViewGroup parent) {
	 
	   ViewHolder holder = null;
	   Log.v("ConvertView", String.valueOf(position));
	 
	   if (convertView == null) {
	   LayoutInflater vi = (LayoutInflater)getSystemService(
	     Context.LAYOUT_INFLATER_SERVICE);
	   convertView = vi.inflate(R.layout.country_info, null);
	 
	   holder = new ViewHolder();
	   holder.code = (TextView) convertView.findViewById(R.id.code);
	   holder.name = (CheckBox) convertView.findViewById(R.id.checkBox1);
	   convertView.setTag(holder);
	 
	    holder.name.setOnClickListener( new View.OnClickListener() { 
	     public void onClick(View v) { 
	      CheckBox cb = (CheckBox) v ; 
	      Country country = (Country) cb.getTag(); 
	      Toast.makeText(getApplicationContext(),
	       "Clicked on Checkbox: " + cb.getText() +
	       " is " + cb.isChecked(),
	       Toast.LENGTH_LONG).show();
	      country.setSelected(cb.isChecked());
	     } 
	    }); 
	   }
	   else {
	    holder = (ViewHolder) convertView.getTag();
	   }
	 
	   Country country = countryList.get(position);
	   holder.code.setText(" (" +  country.getCode() + ")");
	   holder.name.setText(country.getName());
	   holder.name.setChecked(country.isSelected());
	   holder.name.setTag(country);
	 
	   return convertView;
	 
	  }
	 
	 }
	 
	 private void checkButtonClick() {
	 
	 
	  Button myButton = (Button) findViewById(R.id.findSelected);
	  myButton.setOnClickListener(new OnClickListener() {
	 
	   @Override
	   public void onClick(View v) {
	 
	    StringBuffer responseText = new StringBuffer();
	    responseText.append("The following were selected...\n");
	 
	    ArrayList<Country> countryList = dataAdapter.countryList;
	    for(int i=0;i<countryList.size();i++){
	     Country country = countryList.get(i);
	     if(country.isSelected()){
	      responseText.append("\n" + country.getName());
	     }
	    }
	 
	    Toast.makeText(getApplicationContext(),
	      responseText, Toast.LENGTH_LONG).show();
	 
	   }
	  });
	 
	 }
	 
	}