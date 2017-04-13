package in.codesquad.bepositive;

import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by SREE-TEST on 16/10/2016.
 */
public class newRequest extends Fragment implements View.OnClickListener {

    View myView;
    private String username;

    private EditText etPatientName,etPatientPhone,etBystanderName,etBystanderPhone,etUnits,etHospital,etPlace;
    private Spinner spBlood;
    private Button btnSubmit;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        myView=inflater.inflate(R.layout.new_request,container,false);

        SharedPreferences sp = this.getActivity().getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        username = sp.getString(Config.USERNAME_SHARED_PREF,"null");

        Spinner spinner = (Spinner)myView.findViewById(R.id.spBlood);
        List<String> list = new ArrayList<String>();
        list.add("Blood Group");
        list.add("A+");list.add("A-");
        list.add("B+");list.add("B-");
        list.add("AB+");list.add("AB-");
        list.add("O+");list.add("O-");

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this.getActivity(), R.layout.spinner,list);
        spinner.setAdapter(adapter);

        etPatientName = (EditText) myView.findViewById(R.id.etPatientName);
        etPatientPhone = (EditText) myView.findViewById(R.id.etPatientPhone);
        etBystanderName = (EditText) myView.findViewById(R.id.etBystanderName);
        etBystanderPhone = (EditText) myView.findViewById(R.id.etBystanderPhone);
        spBlood = (Spinner) myView.findViewById(R.id.spBlood);
        etUnits = (EditText) myView.findViewById(R.id.etUnits);
        etHospital = (EditText) myView.findViewById(R.id.etHospital);
        etPlace = (EditText) myView.findViewById(R.id.etPlace);

        btnSubmit= (Button) myView.findViewById(R.id.btnSubmt);

        btnSubmit.setOnClickListener(this);

        return myView;
    }

    private void addRequest(){


        final String patientName = etPatientName.getText().toString().trim();
        final String patintPhone = etPatientPhone.getText().toString().toLowerCase().trim();
        final String bystanderName = etBystanderName.getText().toString().trim();
        final String bystanderPhone = etBystanderPhone.getText().toString().trim();
        final String hospital = etHospital.getText().toString().trim();
        final String place = etPlace.getText().toString().trim();
        final String units = String.valueOf(Integer.parseInt(etUnits.getText().toString().trim()));
        final String blood = spBlood.getSelectedItem().toString();

        class AddUser extends AsyncTask<Void,Void,String> {

            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(getActivity(),"Adding...","Please Wait...",false,false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                Toast.makeText(getActivity(),s,Toast.LENGTH_LONG).show();

                if(s.trim().equals("Success")){

                    FragmentManager fragmentManager=getFragmentManager();
                    fragmentManager.beginTransaction()
                            .replace(R.id.content_main,new Request_Fragment())
                            .commit();
                }

            }

            @Override
            protected String doInBackground(Void... v) {
                HashMap<String,String> params = new HashMap<>();
                params.put("ptname",patientName);
                params.put("ptphone",patintPhone);
                params.put("byname",bystanderName);
                params.put("byname",bystanderPhone);
                params.put("place",place);
                params.put("blood",blood);
                params.put("units",units);
                params.put("hospital",hospital);
                params.put("addedby",username);


                RequestHandler rh = new RequestHandler();
                String res = rh.sendPostRequest(Config.URL_ADD, params);
                return res;
            }
        }

        AddUser au = new AddUser();
        au.execute();
    }

    @Override
    public void onClick(View view) {
        if (view==btnSubmit){
            addRequest();
        }
    }
}
