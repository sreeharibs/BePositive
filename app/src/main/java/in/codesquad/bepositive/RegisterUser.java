package in.codesquad.bepositive;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by SREE on 10/10/2016.
 */
public class RegisterUser extends AppCompatActivity implements View.OnClickListener {

    private EditText etName,etEmail,etMobile,etPincode,etPassword;
    private Spinner spBlood;
    private Button btnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_user   );


        Spinner spinner = (Spinner)findViewById(R.id.spinner);
        List<String> list = new ArrayList<String>();
        list.add("Blood Group");
        list.add("A+");list.add("A-");
        list.add("B+");list.add("B-");
        list.add("AB+");list.add("AB-");
        list.add("O+");list.add("O-");

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.spinner,list);
        spinner.setAdapter(adapter);

        etName = (EditText) findViewById(R.id.name);
        etEmail = (EditText) findViewById(R.id.email);
        etMobile = (EditText) findViewById(R.id.phone);
        etPincode = (EditText) findViewById(R.id.pincode);
        etPassword = (EditText) findViewById(R.id.password);
        spBlood = (Spinner) findViewById(R.id.spinner);
        btnRegister= (Button) findViewById(R.id.btnRegister);

        btnRegister.setOnClickListener(this);




    }

    //Adding an user
    private void addUser(){


        final String name = etName.getText().toString().trim();
        final String email = etEmail.getText().toString().toLowerCase().trim();
        final String mobile = etMobile.getText().toString().trim();
        final String pincode = etPincode.getText().toString().trim();
        final String password = etPassword.getText().toString().trim();
        final String blood = spBlood.getSelectedItem().toString();


        User user = new User(name, email,mobile,pincode,password,blood);


        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference mDatabaseReference = database.getReference();

        mDatabaseReference.child("user").child(mobile).setValue(user);

        SharedPreferences sp= getSharedPreferences(SharedPref.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor spEditor = sp.edit();
        spEditor.putString(SharedPref.NAME_SHARED_PREF,name);
        spEditor.putString(SharedPref.EMAIL_SHARED_PREF,email);
        spEditor.putString(SharedPref.PHONE_SHARED_PREF,mobile);
        spEditor.putString(SharedPref.PIN_SHARED_PREF,pincode);
        spEditor.putString(SharedPref.GROUP_SHARED_PREF,blood);
        spEditor.apply();

        FragmentManager fragmentManager=getFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.content_main,new Notifications_Fragment())
                .commit();

    }

    @Override
    public void onClick(View view) {
        if (view==btnRegister){
            addUser();
        }
    }
}
