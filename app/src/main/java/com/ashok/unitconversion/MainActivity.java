package com.ashok.unitconversion;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private Spinner typeSpinner;
    private Spinner fromSpinner;
    private Spinner toSpinner;
    private Button convertBTN;
    private EditText valueText;
    private TextView resultView;
    private TextView resultTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        typeSpinner = findViewById(R.id.typespinner);
        fromSpinner = findViewById(R.id.fromspinner);
        toSpinner = findViewById(R.id.toSpinner);
        convertBTN = findViewById(R.id.convertButton);
        valueText = findViewById(R.id.valueEditText);
        resultView = findViewById(R.id.resultView);


        ArrayAdapter<CharSequence> typeAdapter = ArrayAdapter.createFromResource(
                this,
                R.array.conversion_types,
                android.R.layout.simple_spinner_item
        );
        typeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        typeSpinner.setAdapter(typeAdapter);

        ArrayAdapter<CharSequence> unitAdapter = ArrayAdapter.createFromResource(
                this,
                R.array.length_units,
                android.R.layout.simple_spinner_item
        );
        unitAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        fromSpinner.setAdapter(unitAdapter);
        toSpinner.setAdapter(unitAdapter);
        typeSpinner.setOnItemSelectedListener(this);

        convertBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calculation();
            }
        });
        valueText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    // Perform your action here when the user submits the value
                    String inputValueString = valueText.getText().toString().trim();
                    if (inputValueString.isEmpty()) {
                        Toast.makeText(MainActivity.this, "Please enter a value", Toast.LENGTH_SHORT).show();
                        return true;
                    }
                    calculation();
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(valueText.getWindowToken(), 0);
                    return true;
                }
                return false;
            }
        });

        valueText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.toString().isEmpty()) {
                    resultView.setText("");
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


    }

    private void calculation() {
        String typeUnit = typeSpinner.getSelectedItem().toString();
        String fromUnit = fromSpinner.getSelectedItem().toString();
        String toUnit = toSpinner.getSelectedItem().toString();
        String valueString = valueText.getText().toString().trim();

        if (valueString == null || valueString.isEmpty()) {
            // Display an error message to the user
            Toast.makeText(getApplicationContext(), "Please enter value to convert", Toast.LENGTH_SHORT).show();
            return;
        }
        double value = Double.parseDouble(valueString);
        double result = convertLength(value, fromUnit, toUnit, typeUnit);
        String formattedValue = String.format("%.2f", result);
        resultView.setText(formattedValue);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
        String conversionType = parent.getItemAtPosition(pos).toString();
        int unitArrayResId;
        switch (conversionType) {
            case "Length":
                unitArrayResId = R.array.length_units;
                break;
            case "Weight":
                unitArrayResId = R.array.weight_units;
                break;
            case "Temperature":
                unitArrayResId = R.array.temperature_units;
                break;
            default:
                throw new IllegalArgumentException("Invalid conversion type");
        }
        ArrayAdapter<CharSequence> unitAdapter = ArrayAdapter.createFromResource(
                this,
                unitArrayResId,
                android.R.layout.simple_spinner_item
        );
        unitAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        fromSpinner.setAdapter(unitAdapter);
        toSpinner.setAdapter(unitAdapter);


    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    public double convertLength(double value, String fromUnit, String toUnit, String typeUnit) {
        double result = 0.0;
        if (fromUnit == toUnit) {
            Toast.makeText(this, "both unit can't be same", Toast.LENGTH_SHORT).show();
            return 0.0;
        } else {
            if (typeUnit.equals("Length")) {

                switch (fromUnit) {
                    case "Inch":
                        switch (toUnit) {
                            case "Inch":
                                result = value;
                                break;
                            case "Foot":
                                result = value / 12;
                                break;
                            case "Yard":
                                result = value / 36;
                                break;
                            case "Mile":
                                result = value / 63360;
                                break;
                            case "Centimeter":
                                result = value * 2.54;
                                break;
                            case "Kilometer":
                                result = value / 39370.079;
                                break;
                        }
                        break;
                    case "Foot":
                        switch (toUnit) {
                            case "Inch":
                                result = value * 12;
                                break;
                            case "Foot":
                                result = value;
                                break;
                            case "Yard":
                                result = value / 3;
                                break;
                            case "Mile":
                                result = value / 5280;
                                break;
                            case "Centimeter":
                                result = value * 30.48;
                                break;
                            case "Kilometer":
                                result = value / 3280.84;
                                break;
                        }
                        break;
                    case "Yard":
                        switch (toUnit) {
                            case "Inch":
                                result = value * 36;
                                break;
                            case "Foot":
                                result = value * 3;
                                break;
                            case "Yard":
                                result = value;
                                break;
                            case "Mile":
                                result = value / 1760;
                                break;
                            case "Centimeter":
                                result = value * 91.44;
                                break;
                            case "Kilometer":
                                result = value / 1093.613;
                                break;

                        }
                        break;
                    case "Mile":
                        switch (toUnit) {
                            case "Inch":
                                result = value * 63360;
                                break;
                            case "Foot":
                                result = value * 5280;
                                break;
                            case "Yard":
                                result = value * 1760;
                                break;
                            case "Mile":
                                result = value;
                                break;
                            case "Centimeter":
                                result = value * 160934.4;
                                break;
                            case "Kilometer":
                                result = value * 1.609;
                                break;
                        }
                        break;
                    case "Centimeter":
                        switch (toUnit) {
                            case "Inch":
                                result = value / 2.54;
                                break;
                            case "Foot":
                                result = value / 30.48;
                                break;
                            case "Yard":
                                result = value / 91.44;
                                break;
                            case "Mile":
                                result = value / 160934.4;
                                break;
                            case "Centimeter":
                                result = value;
                                break;
                            case "Kilometer":
                                result = value / 100000;
                                break;
                        }
                        break;
                    case "Kilometer":
                        switch (toUnit) {
                            case "Inch":
                                result = value *39370.1;
                                break;
                            case "Foot":
                                result = value *3280.84;
                                break;
                            case "Yard":
                                result = value *1093.61;
                                break;
                            case "Mile":
                                result = value *0.621;
                                break;
                            case "Centimeter":
                                result = value*100000;
                                break;
                        }
                }
            } else if (typeUnit.equals("Weight")) {
                switch (fromUnit) {
                    case "Pound":
                        switch (toUnit) {
                            case "Ounce":
                                result = value * 16;
                                break;
                            case "Ton":
                                result = value / 2000;
                                break;
                        }
                        break;
                    case "Ounce":
                        switch (toUnit) {
                            case "Pound":
                                result = value / 16;
                                break;
                            case "Ton":
                                result = value / 32000;
                                break;
                        }
                        break;
                    case "Ton":
                        switch (toUnit) {
                            case "Pound":
                                result = value * 2000;
                                break;
                            case "Ounce":
                                result = value * 32000;
                                break;
                        }
                        break;
                    default:
                        break;
                }
            } else if (typeUnit.equals("Temperature")) {
                switch (fromUnit) {
                    case "Celsius":
                        switch (toUnit) {
                            case "Fahrenheit":
                                result = (value * 1.8) + 32;
                                break;
                            case "Kelvin":
                                result = value + 273.15;
                                break;
                        }
                        break;
                    case "Fahrenheit":
                        switch (toUnit) {
                            case "Celsius":
                                result = (value - 32) / 1.8;
                                break;
                            case "Kelvin":
                                result = (value + 459.67) * 5 / 9;
                                break;
                        }
                        break;
                    case "Kelvin":
                        switch (toUnit) {
                            case "Celsius":
                                result = value - 273.15;
                                break;
                            case "Fahrenheit":
                                result = (value * 9 / 5) - 459.67;
                                break;
                        }
                        break;
                    default:
                        break;
                }

            }

            return result;
        }
    }
}