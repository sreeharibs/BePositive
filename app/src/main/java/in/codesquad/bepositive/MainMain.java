package in.codesquad.bepositive;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.google.firebase.messaging.FirebaseMessaging;

import java.util.HashMap;
import java.util.Map;


public class MainMain extends AppCompatActivity implements View.OnClickListener {

    private EditText etUsername,etPassword;
    private Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_main );

        //intro
        Intent intent = new Intent(MainMain.this, MainIntroActivity.class);
        startActivity(intent);

        TextView tvRegister= (TextView) findViewById(R.id.tvUserReg);
        tvRegister.setOnClickListener(this);

        etUsername = (EditText) findViewById(R.id.username);
        etPassword = (EditText) findViewById(R.id.password);
        btnLogin = (Button) findViewById(R.id.btnLogin);

        btnLogin.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId()==R.id.tvUserReg){
            //user Registration
            Intent intent = new Intent(MainMain.this,RegisterUser.class);
            startActivity(intent);
        }

        if (view==btnLogin){
            checkLogin();
        }
    }

    private void checkLogin() {

        final String username = etUsername.getText().toString().toLowerCase().trim();
        final String password = etPassword.getText().toString().trim();


        class CheckLogin extends AsyncTask<Void,Void,String> {

            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(MainMain.this,"Logging In...","Please Wait...",false,false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                Toast.makeText(getBaseContext(),s,Toast.LENGTH_LONG).show();

                if(s.trim().equals("Login Successful")){

                    FirebaseMessaging.getInstance().subscribeToTopic("allUsers");

                    SharedPreferences sp = MainMain.this.getSharedPreferences(Config.SHARED_PREF_NAME , Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sp.edit();

                    editor.putString(Config.LOGGEDIN_SHARED_PREF,"yes");
                    editor.putString(Config.USERNAME_SHARED_PREF,username);
                    editor.putString(Config.PASSWORD_SHARED_PREF,password);



                    editor.apply();

                    /*SharedPreferences sp1 = MainMain.this.getSharedPreferences(Config.SHARED_PREF_NAME , Context.MODE_PRIVATE);
                    Toast.makeText(getBaseContext(), sp1.getString(Config.LOGGEDIN_SHARED_PREF,"null"), Toast.LENGTH_SHORT).show();
  */



                    Intent intent = new Intent(MainMain.this,MainActivity.class);
                    startActivity(intent);
                }

            }

            @Override
            protected String doInBackground(Void... v) {
                HashMap<String,String> params = new HashMap<>();
                params.put(Config.KEY_EMP_USERNAME,username);
                params.put(Config.KEY_EMP_PASSWORD,password);
                params.put(Config.KEY_EMP_FIREBASEID,"null");


                RequestHandler rh = new RequestHandler();
                String res = rh.sendPostRequest(Config.URL_LOGIN, params);
                return res;
            }
        }

        CheckLogin cl = new CheckLogin();
        cl.execute();

    }

    @Override
    protected void onResume() {
        super.onResume();
        //In onresume fetching value from sharedpreference
        SharedPreferences sharedPreferences = getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);

//        Toast.makeText(MainMain.this, "in resume :"+sharedPreferences.getString(Config.USERNAME_SHARED_PREF,"null"), Toast.LENGTH_SHORT).show();

        //If we will get true
        if (sharedPreferences.getString(Config.LOGGEDIN_SHARED_PREF, "no").equals("yes")) {
            //We will start the Profile Activity
            Intent intent = new Intent(MainMain.this,MainActivity.class);
            startActivity(intent);
        }
    }

}
