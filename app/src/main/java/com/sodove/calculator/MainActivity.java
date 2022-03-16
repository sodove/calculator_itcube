package com.sodove.calculator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity {

    public String[] buttons_text = {"7", "8", "9", "*", "4", "5", "6", "-", "1", "2", "3", "+", "0", ".", "=", "/"};
    public String[] operators = {"*", "-", "+", "/"};
    public Button[] buttons = new Button[16];

    TextView first;
    TextView operator;
    TextView second;
    TextView result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LinearLayout calc_layout = findViewById(R.id.calc_layout);

        first = new TextView(this);
        operator = new TextView(this);
        second = new TextView(this);
        result = new TextView(this);

        calc_layout.addView(first);
        calc_layout.addView(operator);
        calc_layout.addView(second);
        calc_layout.addView(result);

        for (int i = 0; i < 4; i++) {
            LinearLayout button_layout = new LinearLayout(this);
            button_layout.setOrientation(LinearLayout.HORIZONTAL);

            for (int j = i * 4; j < (i + 1) * 4; j++) {
                buttons[j] = new Button(this);
                buttons[j].setText(buttons_text[j]);
                buttons[j].setTag(buttons_text[j]);
                buttons[j].setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        onButtonClick(v);
                    }});
                button_layout.addView(buttons[j]);
            }

            calc_layout.addView(button_layout);
        }

        Button remove = new Button(this);
        remove.setText("Очистить");
        remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clearTextViews();
            }
        });
        calc_layout.addView(remove);
    }

    public void clearTextViews(){
        first.setText("");
        operator.setText("");
        second.setText("");
        result.setText("");
    }

    public void onButtonClick(View view){
        String button_text = (String) view.getTag();
        String result_text = result.getText().toString();
        if (!result_text.equals(""))
        {
            clearTextViews();
        }

        String first_text = first.getText().toString();
        String second_text = second.getText().toString();
        String operator_text = operator.getText().toString();

        boolean isOperator = false;

        for (String s : operators)
            if (button_text.equals(s)) {
                isOperator = true;
                break;
            }

        if (operator_text.equals("") && !isOperator && !button_text.equals("=")) {
            if (button_text.equals(".") && first_text.equals(""))
                first_text = String.valueOf(0);

            first_text = first_text + button_text;
            first.setText(first_text);
        }
        else
        {
            if (button_text.equals(".") && second_text.equals(""))
                second_text = String.valueOf(0);

            if (!isOperator && !button_text.equals("="))
                second_text = second_text + button_text;
            second.setText(second_text);
        }

        if (isOperator)
            operator.setText(button_text);

        if (isOperator && first_text.equals(""))
            first.setText(String.valueOf(0));

        if (button_text.equals("=")) {
            if (!first_text.equals("") && !second_text.equals("") && !operator_text.equals("")) {
                double first_num = Double.parseDouble(first_text);
                double second_num = Double.parseDouble(second_text);
                double result_num = 0;

                switch (operator_text) {
                    case "+":
                        result_num = first_num + second_num;
                        break;
                    case "-":
                        result_num = first_num - second_num;
                        break;
                    case "/":
                        result_num = first_num / second_num;
                        break;
                    case "*":
                        result_num = first_num * second_num;
                        break;
                }
                result.setText(String.valueOf(result_num));
            }
            else
            {
                Snackbar.make(view, "Закончите ввод", Snackbar.LENGTH_LONG).show();
            }
        }
    }
}