package smith.vien.uwfriendsfinder.friendlisting;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.List;

/**
 * Helper class for providing sample fname for user interfaces created by
 * Android template wizards.
 * <p>
 *     Content about friends.
 * </p>
 */
public class Friends implements Serializable{

    private String displayName;
    private String feelings;
    private String location;

    public Friends(String displayName,
                   String feelings, String location) {
        this.displayName = displayName;
        this.feelings = feelings;
        this.location = location;
    }

    public Friends(){
        this("Vinh", "Happy", "cp206");
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
                    Friends friend = new Friends(obj.getString("name")
                            , obj.getString("feeling"), obj.getString("location"));
                    courseList.add(friend);
                }
            } catch (JSONException e) {
                reason =  "Unable to parse data, Reason: " + e.getMessage();
            }

        }
        return reason;
    }

    public String getDisplayName() {
        return displayName;
    }
    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }
    public String getFeelings() {
        return feelings;
    }
    public void setFeelings(String feelings) {
        this.feelings = feelings;
    }
    public String getLocation() {
        return location;
    }
    public void setLocation(String location) {
        this.location = location;
    }
}
