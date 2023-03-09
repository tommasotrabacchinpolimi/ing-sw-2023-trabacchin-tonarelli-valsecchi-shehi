package model;

import java.io.Serializable;

public class Player implements Serializable {
    private static final long serialVersionUID = 97354642643274L;
    private String nickName;
    private PersonalGoal personalGoal;

    public Player( String  nickName, PersonalGoal personalGoal ){
        this.nickName = nickName;
        this.personalGoal = personalGoal;
    }

    public String getNickName(){
        return nickName;
    }

    public PersonalGoal getPersonalGoal() {
        return personalGoal;
    }
}
