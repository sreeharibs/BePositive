package in.codesquad.bepositive;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class Message_Fragment extends Fragment implements ListView.OnItemClickListener {

    private ListView listView;
    private String username,password;
    private String JSON_STRING;

    View myView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        myView=inflater.inflate(R.layout.notifications,container,false);

        SharedPreferences sp = this.getActivity().getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        username = sp.getString(Config.USERNAME_SHARED_PREF,"null");
        password = sp.getString(Config.PASSWORD_SHARED_PREF,"null");

                listView = (ListView) myView.findViewById(R.id.listView);
        listView.setOnItemClickListener(this);
        getJSON();
        return myView;
    }


    private void showNotifications(){
        JSONObject jsonObject = null;
        ArrayList<HashMap<String,String>> list = new ArrayList<HashMap<String, String>>();
        try {
            jsonObject = new JSONObject(JSON_STRING);
            JSONArray result = jsonObject.getJSONArray(Config.TAG_JSON_ARRAY);

            for(int i = 0; i<result.length(); i++){
                JSONObject jo = result.getJSONObject(i);
                String name = jo.getString(Config.TAG_NAME);
                String message = jo.getString(Config.TAG_MESSAGE);


                HashMap<String,String> employees = new HashMap<>();
                employees.put(Config.TAG_NAME,name);
                employees.put(Config.TAG_MESSAGE,message);

                list.add(employees);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        ListAdapter adapter = new SimpleAdapter(
                this.getActivity(), list, R.layout.message_list,
                new String[]{Config.TAG_NAME,Config.TAG_MESSAGE},
                new int[]{R.id.tvName, R.id.tvMessage});

        listView.setAdapter(adapter);
    }

    private void getJSON(){
        class GetJSON extends AsyncTask<Void,Void,String>{

            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(getActivity(),"Fetching Data","Wait...",false,false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                JSON_STRING = s;
                showNotifications();
            }

            @Override
            protected String doInBackground(Void... params) {

                HashMap<String,String> param = new HashMap<>();



                param.put(Config.KEY_EMP_USERNAME,username);
                param.put(Config.KEY_EMP_PASSWORD,password);

                RequestHandler rh = new RequestHandler();
                String s = rh.sendPostRequest(Config.URL_MESSAGE,param);
                return s;
            }
        }
        GetJSON gj = new GetJSON();
        gj.execute();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        /*Intent intent = new Intent(this, ViewEmployee.class);
        HashMap<String,String> map =(HashMap)parent.getItemAtPosition(position);
        String empId = map.get(Config.TAG_ID).toString();
        intent.putExtra(Config.EMP_ID,empId);
        startActivity(intent);*/

        Toast.makeText(this.getActivity(), "OnClick", Toast.LENGTH_SHORT).show();
    }
}