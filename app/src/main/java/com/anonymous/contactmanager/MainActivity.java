package com.anonymous.contactmanager;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {

    public static final String SERVER = "http://10.0.2.2:3000/";

    EditText fNameET, lNameET, emailET, phoneET;
    Button addtoContactBT, viewContacts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fNameET = findViewById(R.id.fNameET);
        lNameET = findViewById(R.id.lNameET);
        emailET = findViewById(R.id.emailET);
        phoneET = findViewById(R.id.phoneET);
        addtoContactBT = findViewById(R.id.addtoContactBT);
        viewContacts = findViewById(R.id.viewContacts);

        fNameET.setText("Jobanpreet");
        lNameET.setText("Singh");
        emailET.setText("singh47@uwindsor.ca");
        phoneET.setText("4373293444");

        addtoContactBT.setOnClickListener(view -> {

            String fName = fNameET.getText().toString();
            String lName = lNameET.getText().toString();
            String email = emailET.getText().toString();
            String phone = phoneET.getText().toString();


            if (TextUtils.isEmpty(fName)) {
                fNameET.setError("Please enter a value!");
                return;
            }
            if (TextUtils.isEmpty(lName)) {
                lNameET.setError("Please enter a value!");
                return;
            }
            if (TextUtils.isEmpty(email)) {
                emailET.setError("Please enter a value!");
                return;
            }
            if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                emailET.setError("Please enter a value!");
                return;
            }

            if (TextUtils.isEmpty(phone)) {
                phoneET.setError("Please enter a value!");
                return;
            }
            if (!isValidPhone(phone)) {
                phoneET.setError("Please enter a value!");
                return;
            }

            ExecutorService executorService = Executors.newSingleThreadExecutor();
            Runnable runnable = () -> {

                final RequestQueue queue = Volley.newRequestQueue(getApplicationContext());

                queue.start();

                JSONObject jsonObject = new JSONObject();
                try {

                    jsonObject.put("fName", fName);
                    jsonObject.put("lName", lName);
                    jsonObject.put("email", email);
                    jsonObject.put("phone", phone);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.POST,
                        SERVER,
                        jsonObject,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                try {
                                    Toast.makeText(getApplicationContext(), response.getString("message"), Toast.LENGTH_SHORT).show();
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }, new Response.ErrorListener() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), error.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
                queue.add(jsObjRequest);
            };

            executorService.submit(runnable);
            executorService.shutdown();
        });

        viewContacts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(MainActivity.this, ContactsList.class));

            }
        });
    }


    public boolean isValidPhone(String phone) {
        String expression = "^(\\+\\d{1,2}\\s?)?1?\\-?\\.?\\s?\\(?\\d{3}\\)?[\\s.-]?\\d{3}[\\s.-]?\\d{4}$";
        Pattern pattern = Pattern.compile(expression);
        Matcher matcher = pattern.matcher(phone);
        return matcher.matches();
    }
}


