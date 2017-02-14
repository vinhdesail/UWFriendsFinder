package smith.vien.uwfriendsfinder;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import smith.vien.uwfriendsfinder.friendlisting.Friends;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link FriendsDetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FriendsDetailFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    public static final String DETAIL_PARAM = "detail_param";
    public final static String FRIEND_SELECTED = "friend_selected";

    // TODO: Rename and change types of parameters
    private Friends myFreindItem;

    private TextView myDisplayName;
    private TextView myLocation;
    private TextView myId;
    private TextView myFeeling;


    //private OnFragmentInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment CourseDetailFragment.
     */
    public static FriendsDetailFragment getCourseDetailFragment(
            Friends friendItem) {
        FriendsDetailFragment fragment = new FriendsDetailFragment();
        Bundle args = new Bundle();
        args.putSerializable(DETAIL_PARAM, friendItem);
        fragment.setArguments(args);
        return fragment;
    }


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param theName Parameter 1.
     * @param theLocation Parameter 2.
     * @return A new instance of fragment FriendsDetailFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FriendsDetailFragment newInstance(String theName, String theLocation) {
        FriendsDetailFragment fragment = new FriendsDetailFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, theName);
        args.putString(ARG_PARAM2, theLocation);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            myFreindItem = (Friends)
                    getArguments().getSerializable(DETAIL_PARAM);
        }
    }

    public void updateView(Friends item) {
        if (item != null) {
            myId.setText(item.getId());
            myDisplayName.setText(item.getDisplayName());
            myFeeling.setText(item.getFname());
            myLocation.setText(item.getLocation());
        }

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_friends_detail, container, false);
        myId = (TextView) view.findViewById(R.id.friend_id);
        myDisplayName = (TextView) view.findViewById(R.id.friend_name);
        myFeeling = (TextView) view.findViewById(R.id.friend_feeling);
        myLocation = (TextView) view.findViewById(R.id.friend_location);

        return view;

    }

    @Override
    public void onStart() {
        super.onStart();

        // During startup, check if there are arguments passed to the fragment.
        // onStart is a good place to do this because the layout has already been
        // applied to the fragment at this point so we can safely call the method
        // below that sets the article text.
        Bundle args = getArguments();
        if (args != null) {
            // Set article based on argument passed in
            updateView((Friends) args.getSerializable(FRIEND_SELECTED));
        }
    }



    // TODO: Rename method, update argument and hook method into UI event
//    public void onButtonPressed(Uri uri) {
//        if (mListener != null) {
//            mListener.onFragmentInteraction(uri);
//        }
//    }

//    @Override
//    public void onAttach(Context context) {
//        super.onAttach(context);
//        if (context instanceof OnFragmentInteractionListener) {
//            mListener = (OnFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
//    }
//
//    @Override
//    public void onDetach() {
//        super.onDetach();
//        mListener = null;
//    }

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
//    public interface OnFragmentInteractionListener {
//        // TODO: Update argument type and name
//        void onFragmentInteraction(Uri uri);
//    }
}
