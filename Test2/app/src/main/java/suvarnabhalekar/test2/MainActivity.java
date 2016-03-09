package suvarnabhalekar.test2;

import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    Spinner spinnerDropDown;
    TextView car_name,train_name;
    Button navigate_button;

    final String url="http://express-it.optusnet.com.au/sample.json";

    private List<String> id_list=new ArrayList<String>();
    private List<String> name_list=new ArrayList<String>();
    private List<String> car_list=new ArrayList<String>();
    private List<String> train_list=new ArrayList<String>();
    private List<String> lat_list=new ArrayList<String>();
    private List<String> lng_list=new ArrayList<String>();
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow(); // in Activity's onCreate() for instance
            //w.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION, WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            w.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }

        car_name = (TextView)findViewById(R.id.textView5);
        train_name = (TextView)findViewById(R.id.textView6);

        id_list.clear();
        name_list.clear();
        car_list.clear();
        train_list.clear();
        lat_list.clear();
        lng_list.clear();


        try {
            AsyncTaskRunner runner = new AsyncTaskRunner();
            runner.execute();
        }
        catch (Exception e){}


        // Get reference of SpinnerView from layout/main_activity.xml

        spinnerDropDown =(Spinner)findViewById(R.id.spinner1);

        adapter= new ArrayAdapter<String>(this,android.
                R.layout.simple_spinner_dropdown_item ,name_list);

        navigate_button = (Button)findViewById(R.id.button);




    }

    private class AsyncTaskRunner extends AsyncTask<String, String, String> {

        private String resp;

        @Override
        protected String doInBackground(String... params) {

            try {
                ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();


                String result = CustomHttpClient.executeHttpPost(url, nameValuePairs);
                resp = result;
                Log.e("asyntask..", resp);

            } catch (Exception e) {
                e.printStackTrace();
                resp = e.getMessage();
            }
            return resp;
        }


        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();

        }

        /*
         * (non-Javadoc)
         *
         * @see android.os.AsyncTask#onPostExecute(java.lang.Object)
         */
        @Override
        protected void onPostExecute(String result) {
            // execution of result of Long time consuming operation


            try {

                //JSONObject jsonObj = new JSONObject(result);


                // Getting JSON Array node
                JSONArray contacts = new JSONArray(result);

                // looping through All Contacts
                for (int i = 0; i < contacts.length(); i++) {
                    JSONObject c = contacts.getJSONObject(i);

                    String id = c.getString("id");
                    id_list.add(id);

                    String name = c.getString("name");
                    name_list.add(name);
                    // Phone node is JSON Object
                    JSONObject phone = c.getJSONObject("fromcentral");
                    String car = phone.getString("car");
                    car_list.add(car);
                    try {
                        String train = phone.getString("train");
                        train_list.add(train);
                    }
                    catch (Exception e)
                    {
                        String train = "0";
                        train_list.add(train);
                    }
                    // Phone node is JSON Object
                    JSONObject location = c.getJSONObject("location");
                    String latitude = location.getString("latitude");
                    lat_list.add(latitude);
                    String longitude = location.getString("longitude");
                    lng_list.add(longitude);

                }

                spinnerDropDown.setAdapter(adapter);

                spinnerDropDown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, final int position, long id) {
                        // Get select item
                        car_name.setText(car_list.get(position));
                        train_name.setText(train_list.get(position));

                        //Click event on navigate Button
                        navigate_button.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                Intent i = new Intent(getApplicationContext(), SecondActivity.class);
                                //pass value to second activity
                                i.putExtra("Latitude" , lat_list.get(position));
                                i.putExtra("Longitude" , lng_list.get(position));
                                i.putExtra("City_name" , name_list.get(position));
                                startActivity(i);
                            }
                        });
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                        // TODO Auto-generated method stub
                    }
                });



            } catch (Exception e) {
                e.printStackTrace();
            }


        }
    }


}
