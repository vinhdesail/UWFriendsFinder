package smith.vien.uwfriendsfinder;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * All functionality for user login is contained in this class.
 */
public class MainActivity extends AppCompatActivity {
    /**
     * URL for login function.
     */
    private static final String USER_URL = "http://cssgate.insttech.washington.edu/~azoni/Android/movie_add.php?";
    /**
     * URL to add new user to the database.
     */
    private static final String REGISTER_URL = "http://cssgate.insttech.washington.edu/~azoni/Android/list.php?";

    /**
     * Passed Email.
     */
    private String e;

    /**
     * The password.
     */
    private String myPassword;

    /**
     * Shared preferences.
     */
    private SharedPreferences mSharedPreferences;

    /**
     * login.
     */
    private boolean loggedIn;

    /**
     * Register fields.
     */
    private boolean register;

    /**
     * Methods runs when activity is created.
     * Moves to another location if user is already logged in.
     *
     * @param savedInstanceState The saved State.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.register = false;

        mSharedPreferences = getSharedPreferences(getString(R.string.LOGIN_PREFS)
                , Context.MODE_PRIVATE);
        if (!mSharedPreferences.getBoolean(getString(R.string.LOGGEDIN), false)) {
            loggedIn = false;
        } else {
            loggedIn = true;

            try {
                InputStream inputStream = openFileInput(
                        getString(R.string.LOGIN_FILE));

                if ( inputStream != null ) {
                    InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                    BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                    String receiveString = "";

                    while ((receiveString = bufferedReader.readLine()) != null) {
                        String[] info = receiveString.split(" ");
                        e = info[1];
                    }

                    inputStream.close();

                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            Intent intent = new Intent(this, FriendsActivities.class);
            intent.putExtra("email", e);
            startActivity(intent);
            finish();
        }
    }

    /**
     * private method to save data and help the launch of app.
     */
    private void helperLaunch(){
        if(loggedIn) {

            ConnectivityManager connMgr = (ConnectivityManager)
                    getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
            //Store in file

            if (networkInfo != null && networkInfo.isConnected()) {
                //Check if the login and password are valid
                //new LoginTask().execute(url);
                try {
                    OutputStreamWriter outputStreamWriter = new OutputStreamWriter(
                            openFileOutput(getString(R.string.LOGIN_FILE)
                                    , Context.MODE_PRIVATE));
                    outputStreamWriter.write("email " + e);
                    outputStreamWriter.write(" password " + myPassword);
                    outputStreamWriter.close();
//                Toast.makeText(this,"Stored in File Successfully!", Toast.LENGTH_LONG)
//                        .show();
                } catch (Exception e) {
                    e.printStackTrace();
                }

            } else {
                Toast.makeText(this, "No network connection available. Cannot authenticate user",
                        Toast.LENGTH_SHORT).show();
                return;
            }

            mSharedPreferences
                    .edit()
                    .putBoolean(getString(R.string.LOGGEDIN), true)
                    .commit();
            Intent intent = new Intent(MainActivity.this, FriendsActivities.class);
            intent.putExtra("email", e);
            startActivity(intent);
            finish();
        }
    }

    /**
     * Button to allow user to login with information that was inputted into EditText fields.
     * @param view The View.
     */
    public void loginOnClick(View view){
        UserAsync task = new UserAsync();
        EditText email = (EditText) findViewById(R.id.email);
        EditText pass = (EditText) findViewById(R.id.password);
        myPassword = pass.getText().toString();
        e = email.getText().toString();

        task.execute(USER_URL + "email=" + e + "&" + "password=" + myPassword);




        getSupportFragmentManager().popBackStackImmediate();
    }
    /**
     * Button to allow user to register information that was inputted into EditText fields.
     * @param view the view.
     */
    public void registerOnClick(View view) {
        EditText email = (EditText) findViewById(R.id.email);
        EditText pass = (EditText) findViewById(R.id.password);
        String p = pass.getText().toString();
        e = email.getText().toString();
        UserAsync task = new UserAsync();
        this.register = true;
        task.execute(REGISTER_URL + "email=" + e + "&password=" + p);
        getSupportFragmentManager().popBackStackImmediate();
    }

    private class UserAsync extends AsyncTask<String, Void, String> {


        /**
         * The pre-execute methods.
         */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        /**
         * Asynctask the runs in background.
         * @param urls The URL.
         * @return the response;
         */
        @Override
        protected String doInBackground(String... urls) {
            String response = "";
            HttpURLConnection urlConnection = null;
            for (String url : urls) {
                try {
                    URL urlObject = new URL(url);
                    urlConnection = (HttpURLConnection) urlObject.openConnection();

                    InputStream content = urlConnection.getInputStream();

                    BufferedReader buffer = new BufferedReader(new InputStreamReader(content));
                    String s = "";
                    while ((s = buffer.readLine()) != null) {
                        response += s;
                    }

                } catch (Exception e) {
                    response = "Unable to login, Reason: "
                            + e.getMessage();
                } finally {
                    if (urlConnection != null)
                        urlConnection.disconnect();
                }
            }
            return response;
        }


        /**
         * It checks to see if there was a problem with the URL(Network) which is when an
         * exception is caught. It tries to call the parse Method and checks to see if it was successful.
         * If not, it displays the exception.
         *
         * @param result The result.
         */
        @Override
        protected void onPostExecute(String result) {
            // Something wrong with the network or the URL.
            try {
                JSONObject jsonObject = new JSONObject(result);
                String status = (String) jsonObject.get("result");
                if (status.equals("success")) {
                    String toastMessage = "Login successful!";
                    loggedIn = true;
                    if(register){
                        toastMessage = "Registration successful";
                        loggedIn = false;
                        register = false;
                    }
                    Toast.makeText(getApplicationContext(), toastMessage
                            , Toast.LENGTH_LONG)
                            .show();
                    helperLaunch();

                } else {
                    Toast.makeText(getApplicationContext(), "Failed login: "
                                    + jsonObject.get("error")
                            , Toast.LENGTH_LONG)
                            .show();
                }
            } catch (JSONException e) {
                Log.i("error", result);
                Toast.makeText(getApplicationContext(), "Something wrong with the data" +
                        e.getMessage(), Toast.LENGTH_LONG).show();
            }
        }
    }

}
