package com.example.practice_application;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private TextView resultTextView;
    private String currentInput = "";
    private String operator = "";
    private double firstValue = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        resultTextView = findViewById(R.id.result);
    }

    // Handle digit button click
    public void onDigit(View view) {
        Button button = (Button) view;
        currentInput += button.getText().toString();
        resultTextView.setText(currentInput);
    }

    // Handle operator button click
    public void onOperator(View view) {
        Button button = (Button) view;
        operator = button.getText().toString();
        firstValue = Double.parseDouble(currentInput);
        currentInput = "";
    }

    // Handle equals button click
    public void onEquals(View view) {
        if (!currentInput.isEmpty()) {
            double secondValue = Double.parseDouble(currentInput);
            double result = 0;

            switch (operator) {
                case "+":
                    result = firstValue + secondValue;
                    break;
                case "-":
                    result = firstValue - secondValue;
                    break;
                case "*":
                    result = firstValue * secondValue;
                    break;
                case "/":
                    if (secondValue != 0) {
                        result = firstValue / secondValue;
                    } else {
                        resultTextView.setText("Error");
                        return;
                    }
                    break;
            }

            resultTextView.setText(String.valueOf(result));
            currentInput = String.valueOf(result); // Store the result for further operations
        }
    }

    // Handle clear button click
    public void onClear(View view) {
        currentInput = "";
        firstValue = 0;
        operator = "";
        resultTextView.setText("0");
    }
}



