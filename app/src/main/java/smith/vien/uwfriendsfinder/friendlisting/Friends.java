package smith.vien.uwfriendsfinder.friendlisting;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileReader;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Helper class for providing sample fname for user interfaces created by
 * Android template wizards.
 * <p>
 *     Content about friends.
 * </p>
 */
public class Friends implements Serializable{

    private String id;
    private String fname;
    private String lname;
    private String displayName;
    private String userName;
    private String location;

    public Friends(String id, String fname, String lname,
                   String displayName, String userName,
                   String location) {
        this.id = id;
        this.fname = fname;
        this.lname = lname;
        this.displayName = displayName;
        this.userName = userName;
        this.location = location;
    }

    public Friends(){
        this("1", "Vinh", "Vien", "vvien", "vinhdesail", "cp206");
    }


    /**
     * Parses the json string, returns an error message if unsuccessful.
     * Returns course list if success.
     * @param courseJSON
     * @return reason or null if successful.
     */
    public static String parseCourseJSON(String courseJSON, List<Friends> courseList) {
        String reason = null;
        if (courseJSON != null) {
            try {
                JSONArray arr = new JSONArray(courseJSON);

                for (int i = 0; i < arr.length(); i++) {
                    JSONObject obj = arr.getJSONObject(i);
                    // TODO
//                    Friends friend = new Friends(obj.getString(Friends.id), obj.getString(Friends.fname)
//                            , obj.getString(Friends.lname), obj.getString(Friends.displayName)
//                            , obj.getString(Friends.userName), obj.getString(Friends.location));
                    Friends friend = new Friends();
                    courseList.add(friend);
                }
            } catch (JSONException e) {
                reason =  "Unable to parse data, Reason: " + e.getMessage();
            }

        }
        return reason;
    }

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getFname() {
        return fname;
    }
    public String getLname() {
        return lname;
    }
    public String getDisplayName() {
        return displayName;
    }
    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }
    public String getUserName() {
        return userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }
    public String getLocation() {
        return location;
    }
    public void setLocation(String location) {
        this.location = location;
    }
}
