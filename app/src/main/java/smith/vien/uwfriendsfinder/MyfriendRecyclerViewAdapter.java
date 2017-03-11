package smith.vien.uwfriendsfinder;

import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import smith.vien.uwfriendsfinder.FriendsFragment.OnListFragmentInteractionListener;
import smith.vien.uwfriendsfinder.friendlisting.Friends;

import java.util.List;

import static smith.vien.uwfriendsfinder.R.drawable.uw;

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
        holder.mFeelingView.setText(mValues.get(position).getFeelings());

        Log.i("hey",mValues.get(position).getFeelings().toLowerCase() );
        if (mValues.get(position).getFeelings().toLowerCase().equals("satisfied")){
            holder.mHungryview.setBackgroundResource(R.drawable.uw);
        }
        else if (mValues.get(position).getFeelings().toLowerCase().equals("could eat")){
            holder.mHungryview.setBackgroundResource(R.drawable.uw2);
        }
        else if (mValues.get(position).getFeelings().toLowerCase().equals("full")){
            holder.mHungryview.setBackgroundResource(R.drawable.uw3);
        }
        else if (mValues.get(position).getFeelings().toLowerCase().equals("starving")){
            holder.mHungryview.setBackgroundResource(R.drawable.uw4);
        }
        else if (mValues.get(position).getFeelings().toLowerCase().equals("dnd")) {
            holder.mHungryview.setBackgroundResource(R.drawable.uw6);
        }

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
        public final TextView mFeelingView;
        public final ImageView mHungryview;
        public Friends mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mIdView = (TextView) view.findViewById(R.id.email);
            mContentView = (TextView) view.findViewById(R.id.content);
            mFeelingView = (TextView) view.findViewById(R.id.feeling);
            mHungryview = (ImageView) view.findViewById(R.id.hungry);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }
}
