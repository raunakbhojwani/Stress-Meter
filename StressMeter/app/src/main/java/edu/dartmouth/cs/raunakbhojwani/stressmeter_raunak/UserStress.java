package edu.dartmouth.cs.raunakbhojwani.stressmeter_raunak;

/**
 * Created by RaunakBhojwani on 1/26/17.
 * Useful to access time and stress variables after reading them in from file
 */

public class UserStress {

    public int mUserStress;
    public long mTimeOfResult;

    public UserStress(long timeOfResult, int userStress)
    {
        this.mTimeOfResult = timeOfResult;
        this.mUserStress = userStress;
    }

    public long getTimeOfResult()
    {
        return this.mTimeOfResult;
    }

    public int getUserStress()
    {
        return this.mUserStress;
    }
}