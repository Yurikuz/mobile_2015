package com.project.project1;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.EnumMap;



public class Project1 extends Activity {
        private EditText txtResult;

        private Button btnAdd;
        private Button btnDivide;
        private Button btnMultiply;
        private Button btnSubtract;

        private EnumMap<Symbol, Object> commands = new EnumMap(Symbol.class);

        @Override
        protected void onCreate(Bundle savedInstanceState) {


                super.onCreate(savedInstanceState);
                setContentView(R.layout.activity_project1);

                txtResult = (EditText) findViewById(R.id.txtResult);

                btnAdd = (Button) findViewById(R.id.btnAdd);
                btnDivide = (Button) findViewById(R.id.btnDivide);
                btnMultiply = (Button) findViewById(R.id.btnMultiply);
                btnSubtract = (Button) findViewById(R.id.btnSubtract);

                btnAdd.setTag(OperationType.ADD);
                btnDivide.setTag(OperationType.DIVIDE);
                btnMultiply.setTag(OperationType.MULTIPLY);
                btnSubtract.setTag(OperationType.SUBTRACT);
        }

        private OperationType operType;

        public void buttonClick(View view) {

                switch (view.getId()) {
                        case R.id.btnAdd:
                        case R.id.btnDivide:
                        case R.id.btnMultiply:
                        case R.id.btnSubtract: {
                                operType = (OperationType) view.getTag();
                                if (!commands.containsKey(Symbol.OPERATION)) {
                                        if (!commands.containsKey(Symbol.FIRST_DIGIT)) {
                                                commands.put(Symbol.FIRST_DIGIT, txtResult.getText());
                                        }
                                        commands.put(Symbol.OPERATION, operType);
                                } else if (!commands.containsKey(Symbol.SECOND_DIGIT)) {
                                        commands.put(Symbol.SECOND_DIGIT, txtResult.getText());
                                        doCalc();
                                        commands.put(Symbol.OPERATION, operType);
                                        commands.remove(Symbol.SECOND_DIGIT);
                                }
                                break;
                        }
                        case R.id.btnClear: {
                                txtResult.setText("0");
                                commands.clear();
                                break;
                        }
                        case R.id.btnResult:{
                                if (commands.containsKey(Symbol.FIRST_DIGIT) && commands.containsKey(Symbol.OPERATION)) {
                                        commands.put(Symbol.SECOND_DIGIT, txtResult.getText());
                                        doCalc();
                                        commands.put(Symbol.OPERATION, operType);
                                        commands.remove(Symbol.SECOND_DIGIT);
                                }
                                break;
                        }
                        case R.id.btnComma: {
                                if (commands.containsKey(Symbol.FIRST_DIGIT)
                                        && getDouble(txtResult.getText().toString()) == getDouble(commands
                                        .get(Symbol.FIRST_DIGIT).toString())
                                        ) {
                                        txtResult.setText("0" + view.getContentDescription().toString());
                                }

                                if (!txtResult.getText().toString().contains(",")) {
                                        txtResult.setText(txtResult.getText() + ",");
                                }
                                break;
                        }
                        case R.id.btnDelete: {
                                txtResult.setText(txtResult.getText().delete(
                                        txtResult.getText().length() - 1,
                                        txtResult.getText().length()));

                                if (txtResult.getText().toString().trim().length() == 0) {
                                        txtResult.setText("0");
                                }
                                break;
                        }
                        default: {
                                if (txtResult.getText().toString().equals("0")
                                        ||
                                        (commands.containsKey(Symbol.FIRST_DIGIT) && getDouble(txtResult
                                                .getText()) == getDouble(commands.get(Symbol.FIRST_DIGIT)))
                                        ) {
                                        txtResult.setText(view.getContentDescription().toString());
                                } else {
                                        txtResult.setText(txtResult.getText()
                                                + view.getContentDescription().toString());
                                }
                        }
                }
        }

        private double getDouble(Object value) {
                double result = 0;
                try {
                        result = Double.valueOf(value.toString().replace(',', '.')).doubleValue();
                } catch (Exception e) {
                        e.printStackTrace();
                        result = 0;
                }
                return result;
        }

        private void doCalc() {
                OperationType operTypeTmp = (OperationType) commands.get(Symbol.OPERATION);


               double result = calc(operTypeTmp,
                        getDouble(commands.get(Symbol.FIRST_DIGIT)),
                        getDouble(commands.get(Symbol.SECOND_DIGIT)));
                if(result % 1 == 0){
                txtResult.setText(String.valueOf((int) result));
        } else {
                txtResult.setText(String.valueOf(result));
        }

        commands.put(Symbol.FIRST_DIGIT,result);
}

        private Double calc(OperationType operType, double a, double b) {
                switch (operType) {
                        case ADD: {
                                return CalcOperations.add(a, b);
                        }
                        case DIVIDE: {
                                return CalcOperations.divide(a, b);
                        }
                        case MULTIPLY: {
                                return CalcOperations.multiply(a, b);
                        }
                        case SUBTRACT: {
                                return CalcOperations.subtract(a, b);
                        }
                }
                return null;
        }
}


