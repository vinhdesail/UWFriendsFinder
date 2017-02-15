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
import android.widget.TextView;
import android.widget.Toast;

import java.net.URLEncoder;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link EditPersonalInformation.EditInfomationListener} interface
 * to handle interaction events.
 * Use the {@link EditPersonalInformation#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EditPersonalInformation extends Fragment {

    // TODO PUT URL HERE
    private static final String UPDATE_URL = "";

    private EditInfomationListener mListener;

    private TextView myCurrentDisplayName;
    private TextView myCurrentLocation;
    private TextView myCurrentFeelings;
    private EditText myNewDisplayname;
    private EditText myNewLocation;
    private EditText myNewFeelings;

    public EditPersonalInformation() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment EditPersonalInformation.
     */
    // TODO: Rename and change types and number of parameters
    public static EditPersonalInformation newInstance(String param1, String param2) {
        EditPersonalInformation fragment = new EditPersonalInformation();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_edit_personal_information, container, false);

        myCurrentDisplayName = (TextView) v.findViewById(R.id.show_current_display_name);
        myCurrentLocation = (TextView) v.findViewById(R.id.show_current_location);
        myCurrentFeelings = (TextView) v.findViewById(R.id.show_current_feelings);
        myNewDisplayname = (EditText) v.findViewById(R.id.edit_display_name);
        myNewLocation = (EditText) v.findViewById(R.id.edit_location);
        myNewFeelings = (EditText) v.findViewById(R.id.edit_feelings);

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

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface EditInfomationListener {
        // TODO: Update argument type and name
        void editInfo(String url);
    }

    // TODO fix the edited base on database
    private String buildCourseURL(View v) {

        StringBuilder sb = new StringBuilder(UPDATE_URL);

        try {

            String displayName = myNewDisplayname.getText().toString();
            sb.append("displayName=");
            sb.append(displayName);


            String location = myNewLocation.getText().toString();
            sb.append("&location=");
            sb.append(URLEncoder.encode(location, "UTF-8"));


            String feelings = myNewFeelings.getText().toString();
            sb.append("&feelings=");
            sb.append(URLEncoder.encode(feelings, "UTF-8"));

            Log.i("CourseAddFragment", sb.toString());

        }
        catch(Exception e) {
            Toast.makeText(v.getContext(), "Something wrong with the url" + e.getMessage(), Toast.LENGTH_LONG)
                    .show();
        }
        return sb.toString();
    }

}
