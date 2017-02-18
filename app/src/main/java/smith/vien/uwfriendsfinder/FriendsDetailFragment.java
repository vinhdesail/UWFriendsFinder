package smith.vien.uwfriendsfinder;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import smith.vien.uwfriendsfinder.friendlisting.Friends;


/**
 * The fragment that gives you details about friends.
 *
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link FriendsDetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FriendsDetailFragment extends Fragment {

    public final static String FRIEND_SELECTED = "friend_selected";

    private TextView myDisplayName;
    private TextView myLocation;
    private TextView myFeeling;

    /**
     * Constructor for the class.
     *
     * @return A new instance of fragment FriendsDetailFragment.
     */
    public static FriendsDetailFragment newInstance() {
        FriendsDetailFragment fragment = new FriendsDetailFragment();
        return fragment;
    }

    /**
     * Create the fragment.
     * @param savedInstanceState Saved State.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    /**
     * Get the changes and update.
     * @param item Friend item.
     */
    public void updateView(Friends item) {
        if (item != null) {
            myDisplayName.setText(item.getDisplayName());
            myFeeling.setText(item.getFeelings());
            myLocation.setText(item.getLocation());
        }

    }

    /**
     * Set up the view for the fragment.
     * @param inflater Inflater.
     * @param container Container.
     * @param savedInstanceState Saved State.
     * @return View.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_friends_detail, container, false);
        myDisplayName = (TextView) view.findViewById(R.id.friend_name);
        myFeeling = (TextView) view.findViewById(R.id.friend_feeling);
        myLocation = (TextView) view.findViewById(R.id.friend_location);

        FloatingActionButton floatingActionButton = (FloatingActionButton)
                getActivity().findViewById(R.id.fab);
        floatingActionButton.show();

        return view;

    }

    /**
     * Called when started.
     */
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

}
