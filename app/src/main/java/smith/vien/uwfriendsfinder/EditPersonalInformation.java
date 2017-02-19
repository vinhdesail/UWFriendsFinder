package smith.vien.uwfriendsfinder;

import android.content.Context;
import android.net.Uri;
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
import android.widget.TextView;
import android.widget.Toast;

import java.net.URLEncoder;


/**
 * Fragment that handle the edit information for the current user.
 *
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link EditPersonalInformation.EditInfomationListener} interface
 * to handle interaction events.
 * Use the {@link EditPersonalInformation#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EditPersonalInformation extends Fragment {

    /** Main URL to update data.*/
    private static final String UPDATE_URL = "http://cssgate.insttech.washington.edu/~vvien/TCSS450MobileApps/updateMyData.php?";

    /** Listener for the class. */
    private EditInfomationListener mListener;

    // Commented out for future code.
//    private TextView myCurrentDisplayName;
//    private TextView myCurrentLocation;
//    private TextView myCurrentFeelings;
    private EditText myEmail;
    private EditText myNewDisplayname;
    private EditText myNewLocation;
    private Spinner myNewFeelings;
    private String email;
    /**
     * Default constructor.
     */
    public EditPersonalInformation(){

    }
    public void setEmail(String email) {
        // Required empty public constructor
        this.email = email;
    }
    /**
     * Creates new fragment.
     *
     * @return A new instance of fragment EditPersonalInformation.
     */
    public static EditPersonalInformation newInstance() {
        EditPersonalInformation fragment = new EditPersonalInformation();
        return fragment;
    }

    /**
     * Runs when when fragment is created.
     * @param savedInstanceState Saved State.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    /**
     * Create the view for the fragment.
     * @param inflater Inflater.
     * @param container Container.
     * @param savedInstanceState Saved State.
     * @return View.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_edit_personal_information, container, false);

//        myCurrentDisplayName = (TextView) v.findViewById(R.id.show_current_display_name);
//        myCurrentLocation = (TextView) v.findViewById(R.id.show_current_location);
//        myCurrentFeelings = (TextView) v.findViewById(R.id.show_current_feelings);
        myEmail = (EditText) v.findViewById(R.id.edit_email);
        myEmail.setText(email);
        myNewDisplayname = (EditText) v.findViewById(R.id.edit_display_name);
        myNewLocation = (EditText) v.findViewById(R.id.edit_location);
        myNewFeelings = (Spinner) v.findViewById(R.id.edit_feelings);

        Button editInfoButton = (Button) v.findViewById(R.id.button_update_info);
        editInfoButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                String url = buildCourseURL(v);
                mListener.editInfo(url);
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
        if (context instanceof EditInfomationListener) {
            mListener = (EditInfomationListener) context;
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
    public interface EditInfomationListener {
        void editInfo(String url);
    }

    /**
     * Builds the url for request.
     * @param v View.
     * @return String that represent the url.
     */
    private String buildCourseURL(View v) {

        StringBuilder sb = new StringBuilder(UPDATE_URL);

        try {

            String email = myEmail.getText().toString();
            sb.append("email=");
            sb.append(URLEncoder.encode(email, "UTF-8"));

            String displayName = myNewDisplayname.getText().toString();
            sb.append("&displayName=");
            sb.append(URLEncoder.encode(displayName, "UTF-8"));

            String location = myNewLocation.getText().toString();
            sb.append("&location=");
            sb.append(URLEncoder.encode(location, "UTF-8"));

            String feelings = myNewFeelings.getSelectedItem().toString();
            sb.append("&feelings=");
            sb.append(URLEncoder.encode(feelings, "UTF-8"));
            //sb.append(URLEncoder.encode(feelings, "UTF-8"));
            Log.i("UpdateFragment", sb.toString());

        }
        catch(Exception e) {
            Toast.makeText(v.getContext(), "Something wrong with the url" + e.getMessage(), Toast.LENGTH_LONG)
                    .show();
        }
        return sb.toString();
    }

}
