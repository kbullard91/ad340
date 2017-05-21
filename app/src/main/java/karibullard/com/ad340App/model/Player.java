package karibullard.com.ad340App.model;

import android.os.Parcel;
import android.os.Parcelable;

import static android.R.attr.description;

public class Player implements Parcelable{

    private int userID;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private int playStatus;
    private String image;

    public Player(){
    }

    public Player(int userID, String firstName, String lastName, String phoneNumber, int playStatus, String image){
        this.userID = userID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.playStatus = playStatus; //0 = not active, 1 = active
        this.image = image; //player avatar
    }

    public String getFirstName(){
        return firstName;
    }

    public void setFirstName(String firstName){
        this.firstName = firstName;
    }

    public String getLastName(){
        return lastName;
    }

    public void setLastName(String lastName){
        this.lastName = lastName;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public int getPlayStatus() {
        return playStatus;
    }

    public void setPlayStatus(int playStatus) {
        this.playStatus = playStatus;
    }

    @Override
    public String toString() {
        return "Player{" +
                "userID='" + userID + '\'' +
                ", itemName='" + firstName + '\'' +
                ", firstName='" + description + '\'' +
                ", lastName='" + lastName + '\'' +
                ", phoneNumber=" + phoneNumber +
                ", playStatus=" + playStatus +
                ", image='" + image + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.userID);
        dest.writeString(this.firstName);
        dest.writeString(this.lastName);
        dest.writeString(this.phoneNumber);
        dest.writeInt(this.playStatus);
        dest.writeString(this.image);
    }

    private Player(Parcel in) {
        this.userID = in.readInt();
        this.firstName = in.readString();
        this.lastName = in.readString();
        this.phoneNumber = in.readString();
        this.playStatus = in.readInt();
        this.image = in.readString();
    }

    public static final Creator<Player> CREATOR = new Creator<Player>() {
        @Override
        public Player createFromParcel(Parcel source) {
            return new Player(source);
        }

        @Override
        public Player[] newArray(int size) {
            return new Player[size];
        }
    };
}
