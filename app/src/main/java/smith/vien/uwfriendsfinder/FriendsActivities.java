package smith.vien.uwfriendsfinder;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import smith.vien.uwfriendsfinder.friendlisting.Friends;


/**
 * The activity that shows all friends.
 */
public class FriendsActivities extends AppCompatActivity
        implements FriendsFragment.OnListFragmentInteractionListener,
            EditPersonalInformation.EditInfomationListener{

    /**
     * Method gets call when activity get created.
     * Show the friend fragment and adds the floating button.
     * @param savedInstanceState Saved State.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends);
        if (savedInstanceState == null || getSupportFragmentManager().findFragmentById(R.id.list) == null) {
            FriendsFragment courseFragment = new FriendsFragment();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_container, courseFragment)
                    .commit();
        }
        Intent intent = getIntent();
        final String str = intent.getStringExtra("email");
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditPersonalInformation editInfo = new EditPersonalInformation();
                editInfo.setEmail(str);
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, editInfo)
                        .addToBackStack(null)
                        .commit();
            }
        });




    }

    /**
     * The listener for each individual friend.
     * When selected open up another fragment.
     * @param item The friend item selected.
     */
    @Override
    public void onListFragmentInteraction(Friends item) {
        FriendsDetailFragment courseDetailFragment = new FriendsDetailFragment();
        Bundle args = new Bundle();
        args.putSerializable(FriendsDetailFragment.FRIEND_SELECTED, item);
        courseDetailFragment.setArguments(args);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, courseDetailFragment)
                .addToBackStack(null)
                .commit();
    }

    /**
     * The listener to let user edit and update personal information.
     * @param url The webservice url.
     */
    @Override
    public void editInfo(String url){
        EditInfoTask task = new EditInfoTask();
        task.execute(new String[]{url.toString()});

        getSupportFragmentManager().popBackStackImmediate();
    }

    /**
     * Private Async task to allow user to edit personal information.
     */
    private class EditInfoTask extends AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        /**
         * Establish the connection.
         * @param urls The URL.
         * @return String the represent the condition of the connection.
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
                    response = "Unable to add update, Reason: "
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
         * @param result The result status.
         */
        @Override
        protected void onPostExecute(String result) {
            // Something wrong with the network or the URL.
            try {
                JSONObject jsonObject = new JSONObject(result);
                String status = (String) jsonObject.get("status");
                if (status.equals("success")) {
                    Toast.makeText(getApplicationContext(), "Info successfully updated!"
                            , Toast.LENGTH_LONG)
                            .show();
                } else {
                    Toast.makeText(getApplicationContext(), "Failed to update: "
                                    + jsonObject.get("error")
                            , Toast.LENGTH_LONG)
                            .show();
                }
            } catch (JSONException e) {
                Toast.makeText(getApplicationContext(), "Error, Please Fill out all values", Toast.LENGTH_LONG).show();
            }
        }
    }


}
