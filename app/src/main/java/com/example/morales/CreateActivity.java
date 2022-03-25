package com.example.morales;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.basgeekball.awesomevalidation.utility.RegexTemplate;

public class CreateActivity extends AppCompatActivity {
    EditText name, description, price, quantity;
    Button insert;
    DBHelper DB;

    AwesomeValidation awesomeValidation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);
        name = findViewById(R.id.name);
        description = findViewById(R.id.description);
        price = findViewById(R.id.price);
        quantity = findViewById(R.id.quantity);
        insert = findViewById(R.id.btnInsert);
        DB = new DBHelper(this);

        //Initialize Validation Style
        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);

        //Adding Validation
        awesomeValidation.addValidation(this, R.id.name, RegexTemplate.NOT_EMPTY, R.string.err_name);
        //awesomeValidation.addValidation(this, R.id.description, RegexTemplate.NOT_EMPTY, R.string.err_description);
        awesomeValidation.addValidation(this, R.id.price, "^[1-9]\\d*.\\d*|0.\\d*[1-9]\\d*$", R.string.err_price);
        awesomeValidation.addValidation(this, R.id.quantity, "^[1-9]\\d*$", R.string.err_quantity);

        insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Check Validation
                if(awesomeValidation.validate()){
                    String nameTXT = name.getText().toString();
                    String descriptionTXT = description.getText().toString();
                    float priceTXT = Float.parseFloat(price.getText().toString());
                    int quantityTXT = Integer.parseInt(quantity.getText().toString());

                    Boolean checkInsert = DB.insertData(nameTXT, descriptionTXT, priceTXT, quantityTXT);
                    if(checkInsert==true){
                        Toast.makeText(CreateActivity.this, "New Record Inserted", Toast.LENGTH_SHORT).show();
                        clearForm();
                    }
                    else
                        Toast.makeText(CreateActivity.this, "New Record Not Inserted", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(CreateActivity.this, "Please Check Error", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    public void clearForm() {

        name.setText("");
        description.setText("");
        price.setText("");
        quantity.setText("");
    }
}