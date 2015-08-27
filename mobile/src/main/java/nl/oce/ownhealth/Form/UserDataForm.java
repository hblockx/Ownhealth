package nl.oce.ownhealth.Form;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import nl.oce.ownhealth.JSon.PersonalDataModel;
import nl.oce.ownhealth.R;

public class UserDataForm extends ActionBarActivity {

    private PersonalDataModel personalData;

    private EditText firstNameField;
    private EditText lastNameField;
    private EditText ageField;
    private EditText weightField;
    private RadioGroup genderRadioGroup;
    private RadioButton maleRadioButton;
    private RadioButton femaleRadioButton;
    private Button saveFormButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_data_form);

        Log.e("Hello", "World");

        personalData = new PersonalDataModel();

        initFormComponents();

        atachListeners();

        Log.e("Personal Data", ""+personalData.getSex());
    }

    private void initFormComponents(){
        firstNameField =  (EditText)findViewById(R.id.firstNameField);
        lastNameField =  (EditText)findViewById(R.id.lastNameField);
        ageField =  (EditText)findViewById(R.id.ageField);
        weightField = (EditText) findViewById(R.id.weightField);
        genderRadioGroup = (RadioGroup) findViewById(R.id.genderRadioGroup);
        maleRadioButton = (RadioButton) findViewById(R.id.maleRB);
        femaleRadioButton = (RadioButton) findViewById(R.id.femaleRB);
        saveFormButton = (Button) findViewById(R.id.saveButton);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_user_data_form, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void atachListeners(){
        atachListenerToRadioButtonsGroup();
        attachListenerToTextFileds();
    }

    private void atachListenerToRadioButtonsGroup() {
        final RadioButton.OnCheckedChangeListener onRadioCheckedChangedListener = new RadioButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                String gender = R.id.maleRB == (buttonView.getId()) ? "Male" : "Female";
                personalData.setSex(gender);
            }
        };

        maleRadioButton.setOnCheckedChangeListener(onRadioCheckedChangedListener);
        femaleRadioButton.setOnCheckedChangeListener(onRadioCheckedChangedListener);
    }

    private void attachListenerToTextFileds(){
        firstNameField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                personalData.setFirstName(firstNameField.getText().toString());
            }
        });

        lastNameField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                personalData.setLastName(lastNameField.getText().toString());
            }
        });

        ageField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                final String value = ageField.getText().toString();
                if(value.length() > 0) personalData.setAge(Integer.parseInt(value));
            }
        });

        weightField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                final String value = weightField.getText().toString();
                if(value.length() > 0) personalData.setWeight(Float.parseFloat(weightField.getText().toString()));
            }
        });

        saveFormButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                genderRadioGroup.clearCheck();
                Log.e("Hello", "Hey i'm a " + personalData.getFirstName() + "   " + personalData.getLastName() + "  " + personalData.getSex() + "   " +personalData.getAge() +" "+ personalData.getWeight());
            }
        });


    }

}
