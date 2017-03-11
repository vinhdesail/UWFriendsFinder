package smith.vien.uwfriendsfinder;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import smith.vien.uwfriendsfinder.friendlisting.Friends;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;

/**
 * Created by Vinh Vien on 3/10/2017.
 */

public class FriendsTest {

    private Friends myTestFriend;

    /**
     * Run before every test.
     */
    @Before
    public void setUp(){
        myTestFriend = new Friends("Vinh", "Could Eat", "CP 206");
    }

    /**
     * Testing constructor.
     */
    @Test
    public void testConstructor(){
        assertNotNull(myTestFriend);
    }
    /**
     * Testing constructor.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testConstructorNull1(){
        new Friends(null, "Could eat", "CP 206");
    }
    /**
     * Testing constructor.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testConstructorNull2(){
        new Friends("Vinh", null, "CP 206");
    }
    /**
     * Testing constructor.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testConstructorNull3(){
        new Friends("Vinh", "Could eat", null);
    }

    // ----------- I dont know why the JSON test don't work -------//
    /**
     * Test static method when json works.
     */
    @Test
    public void testParseCourseJSON(){
        List<Friends> testArray = new ArrayList<>();
        String reason = Friends.parseCourseJSON("[{\"name\":\"azoni\",\"location\":\"todds\",\"feeling\":\"Satisfied\"}," +
                "{\"name\":\"Vinh\",\"location\":\"Sci 106\",\"feeling\":\"DND\"}]", testArray);
        assertTrue(reason == null);

    }

    /**
     * Test static method when fail.
     */
    @Test
    public void testParseCourseJSONFail(){
        List<Friends> testArray = new ArrayList<>();
        String reason = Friends.parseCourseJSON("", testArray);
        assertTrue(reason.contains("Unable to parse data, Reason: "));

    }

    /** Test getters and setter*/
    @Test
    public void testSetGetName(){
        myTestFriend.setDisplayName("Charlton");
        assertEquals("Charlton", myTestFriend.getDisplayName());
    }

    /** Test getters and setter*/
    @Test
    public void testSetGetFeeling(){
        myTestFriend.setFeelings("Sad");
        assertEquals("Sad", myTestFriend.getFeelings());
    }

    /** Test getters and setter*/
    @Test
    public void testSetGetLocation(){
        myTestFriend.setLocation("Sci 106");
        assertEquals("Sci 106", myTestFriend.getLocation());
    }
}
