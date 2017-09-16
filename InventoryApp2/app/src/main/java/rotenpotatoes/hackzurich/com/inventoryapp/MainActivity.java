package rotenpotatoes.hackzurich.com.inventoryapp;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.util.JsonReader;
import android.util.JsonWriter;
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
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

import rotenpotatoes.hackzurich.com.inventoryapp.data.Article;
import rotenpotatoes.hackzurich.com.inventoryapp.data.InventoryDB;

public class MainActivity extends AppCompatActivity {

    private final List<Article> list = new InventoryDB().getRandomList();
    private DateFormat df = new SimpleDateFormat("MM/dd/yyyy");


//    private TextView mTextMessage;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    postData();
//                    Toast.makeText(getBaseContext(), "", 121);
                    return true;
                case R.id.navigation_dashboard:
                    return true;
                case R.id.navigation_notifications:
                    return true;
            }
            return false;
        }

    };

    public void postData() {
        Log.i("Transfer", "Transferring list: " + list);
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    //instantiates httpclient to make request
                    DefaultHttpClient httpclient = new DefaultHttpClient();

                    //url with the post data
                    HttpPost httpost = new HttpPost("http://localhost:8080");

                    //convert parameters into JSON object

                    ObjectMapper objectMapper = new ObjectMapper();

                    //passes the results to a string builder/entity
                    StringEntity se = new StringEntity(objectMapper.writeValueAsString(new Article("name", 1d, "kg", new Date())));


                    //sets the post request as the resulting string
                    httpost.setEntity(se);
                    //sets a request header so the page receving the request
                    //will know what to do with it
                    httpost.setHeader("Accept", "application/json");
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


        final ListAdapter adapter = new ArrayAdapter(this,
                android.R.layout.simple_list_item_1, list) {

            @NonNull
            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

                LayoutInflater vi;
                vi = LayoutInflater.from(getContext());
                View v = vi.inflate(R.layout.article, null);

                Article a = (Article) getItem(position);

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

}
