package model;

public class Player {
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
