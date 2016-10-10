package com.pramont.transfer;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

public class TransferActivity extends AppCompatActivity implements View.OnClickListener {
    private String mCtaPaypay;
    private TextView mCtaPaypalTextView;
    private Button mCtaButton;
    private Button mNextButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transfer);

        mCtaPaypalTextView = (TextView) findViewById(R.id.cta_paypal);
        mCtaButton = (Button) findViewById(R.id.ctaPayPal);
        mNextButton = (Button) findViewById(R.id.siguiente1);

        mCtaButton.setOnClickListener(this);
        mNextButton.setOnClickListener(this);
    }

    private void alertCta(){
        // get alert_cta.xml view
        LayoutInflater layoutInflater = LayoutInflater.from(this);
        View promptsView = layoutInflater.inflate(R.layout.alert_cta, null);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

        // set prompts.xml to alertdialog builder
        alertDialogBuilder.setView(promptsView);

        final EditText userInput = (EditText) promptsView.findViewById(R.id.InputAlertDialog);
        userInput.requestFocus();

        ((InputMethodManager)this.getSystemService(Context.INPUT_METHOD_SERVICE)).
                toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);

        // set dialog message
        alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                String newAmount;
                                // get user input and set it to result
                                // edit text
                                newAmount = userInput.getText().toString().trim();
                                if(!newAmount.isEmpty() && !newAmount.equalsIgnoreCase("0") )
                                {
                                    mCtaPaypay = userInput.getText().toString().trim();
                                    mCtaPaypalTextView.setText(mCtaPaypay);
                                }
                               // donate();
                               // hideKeyboard();
                            }
                        })
                .setNegativeButton("Cancelar",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                dialog.cancel();
                               // hideKeyboard();
                            }
                        });

        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();

        // show it
        alertDialog.show();

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.ctaPayPal:
                alertCta();
                break;
            case R.id.siguiente1:
                nextStep();
                break;
        }
    }

    private void nextStep() {
        Intent myIntent = new Intent(TransferActivity.this,PagoDev.class);
        myIntent.putExtra("PAYPAL_ID",mCtaPaypay);
        startActivity(myIntent);
    }
}
