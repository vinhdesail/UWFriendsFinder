package smith.vien.uwfriendsfinder;

import android.content.Intent;
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
import java.net.HttpURLConnection;
import java.net.URL;
/**
 * All functionality for user login is contained in this class.
 */
public class MainActivity extends AppCompatActivity {

    private static final String USER_URL = "http://cssgate.insttech.washington.edu/~azoni/Android/movie_add.php?";
    private static final String REGISTER_URL = "http://cssgate.insttech.washington.edu/~azoni/Android/list.php?";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void loginOnClick(View view){
        AddCourseTask task = new AddCourseTask();
        EditText email = (EditText) findViewById(R.id.email);
        EditText pass = (EditText) findViewById(R.id.password);
        String p = pass.getText().toString();
        String e = email.getText().toString();
        task.execute(USER_URL + "email=" + e + "&" + "password=" + p);
        getSupportFragmentManager().popBackStackImmediate();
    }
    public void registerOnClick(View view) {
        EditText email = (EditText) findViewById(R.id.email);
        EditText pass = (EditText) findViewById(R.id.password);
        String p = pass.getText().toString();
        String e = email.getText().toString();
        AddCourseTask task = new AddCourseTask();
        task.execute(REGISTER_URL + "email=" + e + "&password=" + p);
        getSupportFragmentManager().popBackStackImmediate();
    }

    private class AddCourseTask extends AsyncTask<String, Void, String> {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

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
                    response = "Unable to add course, Reason: "
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
         * @param result
         */
        @Override
        protected void onPostExecute(String result) {
            // Something wrong with the network or the URL.
            try {
                JSONObject jsonObject = new JSONObject(result);
                String status = (String) jsonObject.get("result");
                if (status.equals("success")) {
                    Toast.makeText(getApplicationContext(), "Login successfully added!"
                            , Toast.LENGTH_LONG)
                            .show();
                    Intent intent = new Intent(MainActivity.this, FriendsActivities.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(getApplicationContext(), "Failed to add: "
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
