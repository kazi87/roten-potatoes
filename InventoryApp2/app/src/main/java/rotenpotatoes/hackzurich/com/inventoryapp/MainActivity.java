package rotenpotatoes.hackzurich.com.inventoryapp;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import rotenpotatoes.hackzurich.com.inventoryapp.data.Article;
import rotenpotatoes.hackzurich.com.inventoryapp.data.InventoryDB;

public class MainActivity extends AppCompatActivity {

    private InventoryDB inventoryDB = new InventoryDB();
    private DateFormat df = new SimpleDateFormat("MM/dd/yyyy");


    private TextView mTextMessage;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    mTextMessage.setText(R.string.title_home);
                    return true;
                case R.id.navigation_dashboard:
                    mTextMessage.setText(R.string.title_dashboard);
                    return true;
                case R.id.navigation_notifications:
                    mTextMessage.setText(R.string.title_notifications);
                    return true;
            }
            return false;
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final ListView listview = (ListView) findViewById(R.id.articlesListView);


        final List<Article> list = inventoryDB.getRandomList();

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
