package com.example.z.thelastrow_client_gamedeal.fragment.inputcells;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;


import com.example.z.thelastrow_client_gamedeal.R;

public class SimpleTextInputCellFragment extends Fragment {
	
	TextView label;
	EditText edit;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.fragment_inputcell_simpletext, container);
		label = (TextView) view.findViewById(R.id.label);
		edit = (EditText) view.findViewById(R.id.edit);
		return view;
	}
	
	public void setLabelText(String labelText){
		label.setText(labelText);
	}
	
	public void setHintText(String hintText){
		edit.setHint(hintText);
	}
	
	public String getText(){
		return edit.getText().toString();
	}
	
	public void setIsPassword(boolean isPassword){
		if(isPassword){
			edit.setInputType(EditorInfo.TYPE_CLASS_TEXT| EditorInfo.TYPE_TEXT_VARIATION_PASSWORD);
		}else{
			edit.setInputType(EditorInfo.TYPE_CLASS_TEXT);
		}		
	}
}
