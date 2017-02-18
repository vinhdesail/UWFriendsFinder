package smith.vien.uwfriendsfinder;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import smith.vien.uwfriendsfinder.FriendsFragment.OnListFragmentInteractionListener;
import smith.vien.uwfriendsfinder.friendlisting.Friends;

import java.util.List;

/**
 * The recycle to help list all friends.
 *
 * {@link RecyclerView.Adapter} that can display a {@link Friends} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyfriendRecyclerViewAdapter extends RecyclerView.Adapter<MyfriendRecyclerViewAdapter.ViewHolder> {

    private final List<Friends> mValues;
    private final OnListFragmentInteractionListener mListener;

    /**
     * Constructor for friends and listener.
     * @param items Friends.
     * @param listener theListener.
     */
    public MyfriendRecyclerViewAdapter(List<Friends> items, OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    /**
     * This calls a xml to hold the view.
     * @param parent Parent.
     * @param viewType viewType.
     * @return ViewHold.
     */
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_friend, parent, false);
        return new ViewHolder(view);
    }

    /**
     * This set the view on the fragment.
     * @param holder The holder.
     * @param position The position.
     */
    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.mIdView.setText(mValues.get(position).getDisplayName());
        holder.mContentView.setText(mValues.get(position).getLocation());


        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListFragmentInteraction(holder.mItem);
                }
            }
        });
    }

    /**
     * Count the amount of itmes.
     * @return The count.
     */
    @Override
    public int getItemCount() {
        return mValues.size();
    }

    /**
     * The class to hold current view.
     */
    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mIdView;
        public final TextView mContentView;
        public Friends mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mIdView = (TextView) view.findViewById(R.id.email);
            mContentView = (TextView) view.findViewById(R.id.content);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }
}
