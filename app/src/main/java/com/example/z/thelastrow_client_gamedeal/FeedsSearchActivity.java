package com.example.z.thelastrow_client_gamedeal;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.z.thelastrow_client_gamedeal.fragment.sreach.SreachListActivity;

/**
 * Created by Administrator on 2016/12/23.
 */

public class FeedsSearchActivity extends Activity {

    Spinner frag_search_spinner;
    EditText frag_search_text;
    Button frag_search_cancel;
    ImageView frag_search_delete;
    LinearLayout frag_search_history;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedssearch);

        frag_search_spinner = (Spinner) findViewById(R.id.frag_search_spinner);
        frag_search_text = (EditText) findViewById(R.id.frag_search_text);
        frag_search_cancel = (Button) findViewById(R.id.frag_search_cancel);
        frag_search_delete = (ImageView) findViewById(R.id.frag_search_history_delete);
        frag_search_history = (LinearLayout) findViewById(R.id.frag_search_history_text);

        frag_search_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        frag_search_history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        });

        frag_search_text.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (i == EditorInfo.IME_ACTION_SEARCH ) {

                    View view = LayoutInflater.from(FeedsSearchActivity.this).inflate(R.layout.search_history_button, null);
                    Button button = (Button) view.findViewById(R.id.search_history_button);
                    button.setText(textView.getText().toString());
//                    button.setTextColor(Color.BLACK);
//                    button.setPadding(0,0,0,0);
//                    button.setTextSize(10);
                    LinearLayout.LayoutParams linearLayout = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                    linearLayout.setMargins(10,5,10,5);
                    button.setLayoutParams(linearLayout);
                    button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            frag_search_text.setText(((Button) view).getText().toString());
                            showAlertDialog(((Button) view).getText().toString());
                        }
                    });

                    //待实现自动换行
                    frag_search_history.addView(button);
//                    showAlertDialog(textView.getText().toString());
                    startActivity(new Intent(FeedsSearchActivity.this, SreachListActivity.class).putExtra("sreachtext" , textView.getText().toString()));
                    textView.setText("");
                    finish();
                    return true;
                }

                return false;
            }
        });
    }


    private void showAlertDialog(String string){
        new AlertDialog.Builder(FeedsSearchActivity.this)
                .setMessage(string)
                .setPositiveButton("好", null).show();
    }
}
