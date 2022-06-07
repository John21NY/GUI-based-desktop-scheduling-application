package model;

/**Model Class User*/
public class User {
    private int userID;
    private String userName;

    /**
     * Constructor for Class User
     * @param addUserName
     * @param addUserID
     */
    public User(Integer addUserID, String addUserName) {
        this.userID = addUserID;
        this.userName = addUserName;
    }
    /**
     * Generate getter for userID
     *
     * @return userID
     */
    public int getUserID() {
        return userID;
    }

    /**
     * Generate getter for userName
     *
     * @return userName
     */
    public String getUserName() {
        return userName;
    }
    /**Setter for user id*/
    public void setUserID(int userID) {
        this.userID = userID;
    }
    /**Setter for username*/
    public  void setUserName(String userName) {
        this.userName = userName;
    }


    @Override
    public String toString(){
        return "" + userID + " " + userName;
    }
}


