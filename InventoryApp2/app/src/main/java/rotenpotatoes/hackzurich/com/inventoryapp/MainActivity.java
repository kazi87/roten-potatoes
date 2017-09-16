package rotenpotatoes.hackzurich.com.inventoryapp;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import rotenpotatoes.hackzurich.com.inventoryapp.data.GeoInventory;
import rotenpotatoes.hackzurich.com.inventoryapp.data.Item;
import rotenpotatoes.hackzurich.com.inventoryapp.data.MockInventoryDB;

public class MainActivity extends AppCompatActivity {

    private final MockInventoryDB mockInventoryDB = new MockInventoryDB();
    private GeoInventory geoInv;
    private DateFormat df = new SimpleDateFormat("MM/dd/yyyy");


    private TextView mUserTextMessage;
    private TextView mLatTextMessage;
    private TextView mLatLngTextMessage;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.random_inventory:
                    randomInventory();
                    finish();
                    startActivity(getIntent());
                    return true;
                case R.id.navigation_dashboard:
                    return true;
                case R.id.navigation_notifications:
                    postData();
                    Toast.makeText(getBaseContext(), "Data has been uploaded to server...", 121);
                    return true;
            }
            return false;
        }

    };

    public void postData() {
        Log.i("Transfer", "Transferring list: " + geoInv);
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    //instantiates httpclient to make request
                    DefaultHttpClient httpclient = new DefaultHttpClient();

                    //url with the post data
                    HttpPost httpost = new HttpPost("http://10.0.2.2:8080/inventory");

                    //convert parameters into JSON object

                    ObjectMapper objectMapper = new ObjectMapper();

                    //passes the results to a string builder/entity
                    StringEntity se = new StringEntity(objectMapper.writeValueAsString(geoInv));


                    //sets the post request as the resulting string
                    httpost.setEntity(se);
                    //sets a request header so the page receving the request
                    //will know what to do with it
//                    httpost.setHeader("Accept", "application/json");
                    httpost.setHeader("Content-type", "application/json");

                    //Handles what is returned from the page
                    ResponseHandler responseHandler = new BasicResponseHandler();
                    Object response = httpclient.execute(httpost, responseHandler);

                    Log.i("REST", "Data: " + response);

                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                } catch (ClientProtocolException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final ListView listview = (ListView) findViewById(R.id.articlesListView);

        randomInventory();

        final ListAdapter adapter = new ArrayAdapter(this,
                android.R.layout.simple_list_item_1, geoInv.getItems()) {

            @NonNull
            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

                LayoutInflater vi;
                vi = LayoutInflater.from(getContext());
                View v = vi.inflate(R.layout.article, null);

                Item a = (Item) getItem(position);

                if (a != null) {
                    TextView tt1 = (TextView) v.findViewById(R.id.name);
                    TextView tt2 = (TextView) v.findViewById(R.id.quantity);
                    TextView tt3 = (TextView) v.findViewById(R.id.expiration);

                    if (tt1 != null) {
                        tt1.setText(a.getName());
                    }

                    if (tt2 != null) {
                        tt2.setText(a.getQuantity() + " " + a.getUnit());
                    }

                    if (tt3 != null) {
                        tt3.setText(df.format(a.getExpirationDate()));
                    }
                }
                return v;
            }
        };
        listview.setAdapter(adapter);

//        mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    private void randomInventory() {
        geoInv = mockInventoryDB.getGeoInventory(46.766206, 8.085938);
        geoInv.setTimestamp(new Date().getTime());
    }

}
