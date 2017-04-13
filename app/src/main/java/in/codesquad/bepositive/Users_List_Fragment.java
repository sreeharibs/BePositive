package in.codesquad.bepositive;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;

public class Users_List_Fragment extends Fragment implements ListView.OnItemClickListener {

    private ListView listView;

    private String JSON_STRING;

    private String search = "";

    View myView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        myView=inflater.inflate(R.layout.notifications_search,container,false);

        final EditText etSearch = (EditText)myView.findViewById(R.id.etSearch);
        etSearch.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable arg0) {
                // TODO Auto-generated method stub
                search = etSearch.getText().toString().toLowerCase(Locale.getDefault());
                showNotifications();
            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1,
                                          int arg2, int arg3) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onTextChanged(CharSequence arg0, int arg1, int arg2,
                                      int arg3) {
                // TODO Auto-generated method stub
            }
        });


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
                String pincode = jo.getString(Config.TAG_PINCODE);
                String blood = jo.getString(Config.TAG_BLOOD);

                HashMap<String,String> employees = new HashMap<>();
                employees.put(Config.TAG_NAME,name);
                employees.put(Config.TAG_PINCODE,pincode);
                employees.put(Config.TAG_BLOOD,blood);
                if (blood.toString().toLowerCase().contains(search)||(name.toString().toLowerCase().contains(search)))
                    list.add(employees);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        ListAdapter adapter = new SimpleAdapter(
                this.getActivity(), list, R.layout.user_list,
                new String[]{Config.TAG_NAME,Config.TAG_PINCODE,Config.TAG_BLOOD},
                new int[]{R.id.tvName, R.id.tvPincode, R.id.tvBlood});

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
                RequestHandler rh = new RequestHandler();
                String s = rh.sendGetRequest(Config.URL_USERS_LIST);
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