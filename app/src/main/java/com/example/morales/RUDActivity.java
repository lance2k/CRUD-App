package com.example.morales;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.basgeekball.awesomevalidation.utility.RegexTemplate;

public class RUDActivity extends AppCompatActivity {
    EditText prodId, name, description, price, quantity;
    Button update, delete, view, search;
    DBHelper DB;

    AwesomeValidation updateValidation;
    AwesomeValidation awesomeValidation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retrieve);
        prodId = findViewById(R.id.prodId);
        name = findViewById(R.id.name);
        description = findViewById(R.id.description);
        price = findViewById(R.id.price);
        quantity = findViewById(R.id.quantity);
        update = findViewById(R.id.btnUpdate);
        delete = findViewById(R.id.btnDelete);
        view = findViewById(R.id.btnView);
        search = findViewById(R.id.btnSearch);
        DB = new DBHelper(this);

        //Initialize Validation Style
        updateValidation = new AwesomeValidation(ValidationStyle.BASIC);
        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);

        //Update Validation
        updateValidation.addValidation(this, R.id.prodId, "^[1-9]\\d*$", R.string.err_prodId);
        updateValidation.addValidation(this, R.id.name, RegexTemplate.NOT_EMPTY, R.string.err_name);
        //updateValidation.addValidation(this, R.id.description, RegexTemplate.NOT_EMPTY, R.string.err_description);
        updateValidation.addValidation(this, R.id.price, "^[1-9]\\d*.\\d*|0.\\d*[1-9]\\d*$", R.string.err_price);
        updateValidation.addValidation(this, R.id.quantity, "^[1-9]\\d*$", R.string.err_quantity);

        //Search/Delete Validation
        awesomeValidation.addValidation(this, R.id.prodId, "^[1-9]\\d*$", R.string.err_prodId);

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Clear other validation
                awesomeValidation.clear();
                //Check Validation
                if(updateValidation.validate()){
                    String nameTXT = name.getText().toString();
                    String descriptionTXT = description.getText().toString();
                    float priceTXT = Float.parseFloat(price.getText().toString());
                    int quantityTXT = Integer.parseInt(quantity.getText().toString());
                    int prodTXT = Integer.parseInt(prodId.getText().toString());

                    Boolean checkUpdate = DB.updateData(prodTXT, nameTXT, descriptionTXT, priceTXT, quantityTXT);
                    if(checkUpdate==true)
                        Toast.makeText(RUDActivity.this, "Record Updated", Toast.LENGTH_SHORT).show();
                    else {
                        Toast.makeText(RUDActivity.this, "Product ID Not Found", Toast.LENGTH_SHORT).show();
                        clearForm();
                    }
                }else{
                    Toast.makeText(RUDActivity.this, "Please Check Error", Toast.LENGTH_SHORT).show();
                }

            }        });
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Clear other fields
                clearForm();
                //Clear other validation
                updateValidation.clear();
                //Check Validation
                if(awesomeValidation.validate()){
                    int prodTXT = Integer.parseInt(prodId.getText().toString());
                    Boolean checkDelete = DB.deleteData(prodTXT);
                    if(checkDelete==true)
                        Toast.makeText(RUDActivity.this, "Record Deleted", Toast.LENGTH_SHORT).show();
                    else
                        Toast.makeText(RUDActivity.this, "Product ID Not Found", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(RUDActivity.this, "Please Check Error", Toast.LENGTH_SHORT).show();
                }

            }        });

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Cursor res = DB.getData();
                if(res.getCount()==0){
                    Toast.makeText(RUDActivity.this, "No Record Exists", Toast.LENGTH_SHORT).show();
                    return;
                }
                StringBuffer buffer = new StringBuffer();
                while(res.moveToNext()){
                    buffer.append("Product ID :"+res.getString(0)+"\n");
                    buffer.append("Name :"+res.getString(1)+"\n");
                    buffer.append("Description :"+res.getString(2)+"\n");
                    buffer.append("Price :"+res.getString(3)+"\n");
                    buffer.append("Quantity :"+res.getString(4)+"\n\n");
                }

                AlertDialog.Builder builder = new AlertDialog.Builder(RUDActivity.this);
                builder.setCancelable(true);
                builder.setTitle("Product Entries");
                builder.setMessage(buffer.toString());
                builder.show();
            }        });

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Clear other fields
                clearForm();
                //Clear other validation
                updateValidation.clear();
                //Check Validation
                if(awesomeValidation.validate()){
                    int prodTXT = Integer.parseInt(prodId.getText().toString());
                    Cursor res = DB.searchData(prodTXT);
                    if(res.getCount()==0){
                        Toast.makeText(RUDActivity.this, "Product ID Not Found", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    res.moveToFirst();
                    name.setText(res.getString(1));
                    description.setText(res.getString(2));
                    price.setText(res.getString(3));
                    quantity.setText(res.getString(4));
                }else{
                    Toast.makeText(RUDActivity.this, "Please Check Error", Toast.LENGTH_SHORT).show();
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