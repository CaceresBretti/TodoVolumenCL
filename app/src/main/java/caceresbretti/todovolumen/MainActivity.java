package caceresbretti.todovolumen;

import android.app.Fragment;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, ConcertsFragment.OnFragmentInteractionListener, NewsFragment.OnFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();


        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View header_view = navigationView.getHeaderView(0);
        TextView nav_username = (TextView)header_view.findViewById(R.id.nav_username);
        TextView nav_usermail = (TextView)header_view.findViewById(R.id.nav_usermail);

        //Recibe data from Login
        try {
            JSONObject sessionObject = new JSONObject(getIntent().getStringExtra("session_data"));
            try {
                JSONObject userObject = new JSONObject(sessionObject.getString("user"));
                nav_username.setText(userObject.getString("name"));
                nav_usermail.setText(userObject.getString("mail"));
            }
            catch (JSONException e){
                e.printStackTrace();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.

        int id = item.getItemId();
        android.support.v4.app.Fragment fragment;

        if (id == R.id.nav_news) {

            fragment = new NewsFragment();
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.flContent, fragment);
            ft.commit();
            setTitle(item.getTitle());

        } else if (id == R.id.nav_concerts) {

            fragment = new ConcertsFragment();
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.flContent, fragment);
            ft.commit();

            setTitle(item.getTitle());
        } else if (id == R.id.nav_preferences) {

        } else if (id == R.id.nav_logout) {
            if(logout()){
                Intent goToLogin = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(goToLogin);
            }
            else{

            }
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onConcertsFragmentInteraction(Uri uri) {

    }

    public boolean logout(){
        OkHttpClient client = new OkHttpClient();
        RequestBody logoutBody = new FormBody.Builder().build();

        Request logoutRequest = new Request.Builder()
                .url("http://itfactory.cl/todoVolumen/API/user/logout")
                .post(logoutBody)
                .build();

        try{
            Response response = client.newCall(logoutRequest).execute();
            if(response.isSuccessful()){
                return true;
            }
            else {
                return false;
            }
        } catch (IOException e){
            e.printStackTrace();
        }

        return false;
    }

    public void onNewsFragmentInteraction(Uri uri) {

    }
}
