package smith.vien.uwfriendsfinder;


import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.net.URLEncoder;


/**
 * A simple {@link Fragment} subclass.
 */
public class UpdateUsername extends Fragment {

    /** Listener for the class. */
    private EditUsernameListener mListener;

    /** fields */
    private String myEmail;
    private EditText myNewDisplayname;
    private String email;

    /**
     * Default contructor.
     */
    public UpdateUsername() {
        // Required empty public constructor
    }

    /**
     * Set the mail.
     * @param email the email.
     */
    public void setEmail(String email) {
        // Required empty public constructor
        this.email = email;
    }

    /**
     * Run when created
     * @param inflater the inflater.
     * @param container the contatiner.
     * @param savedInstanceState saved State.
     * @return view.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_update_username, container, false);

//        myCurrentDisplayName = (TextView) v.findViewById(R.id.show_current_display_name);
//        myCurrentLocation = (TextView) v.findViewById(R.id.show_current_location);
//        myCurrentFeelings = (TextView) v.findViewById(R.id.show_current_feelings);
        myEmail = email;
        myNewDisplayname = (EditText) v.findViewById(R.id.updatedUsername);

        Button editInfoButton = (Button) v.findViewById(R.id.submitUsername);
        editInfoButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                String url = buildCourseURL(v);
                mListener.editUsername(url);
            }
        });

        FloatingActionButton floatingActionButton = (FloatingActionButton)
                getActivity().findViewById(R.id.fab);
        floatingActionButton.hide();

        return v;
    }

    /**
     * Attach the listener.
     * @param context The Listener.
     */
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof UpdateUsername.EditUsernameListener) {
            mListener = (UpdateUsername.EditUsernameListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    /**
     * Detach the Listener.
     */
    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * Interface for the listener.
     */
    public interface EditUsernameListener {
        void editUsername(String url);
    }

    /**
     * Builds the url for request.
     * @param v View.
     * @return String that represent the url.
     */
    private String buildCourseURL(View v) {

        StringBuilder sb = new StringBuilder(EditPersonalInformation.UPDATE_URL);

        try {

            String email = myEmail;
            sb.append("email=");
            sb.append(URLEncoder.encode(email, "UTF-8"));

            String displayName = myNewDisplayname.getText().toString();
            sb.append("&displayName=");
            sb.append(URLEncoder.encode(displayName, "UTF-8"));

            Log.i("UpdateUserNameFragment", sb.toString());

        }
        catch(Exception e) {
            Toast.makeText(v.getContext(), "Something wrong with the url" + e.getMessage(), Toast.LENGTH_LONG)
                    .show();
        }
        return sb.toString();
    }

}
