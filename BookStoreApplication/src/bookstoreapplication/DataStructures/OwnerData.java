/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bookstoreapplication.DataStructures;

import bookstoreapplication.DataStructures.UserEntity;
import java.io.Serializable;

/**
 *
 * @author LordV
 */
public class OwnerData extends UserEntity implements Serializable{
    public OwnerData(String username, String password){
        super(username, password);
    }
    
    /**
     * Returns a String representation of the type of user this is
     * @return The type of user as a string
     */
    public String getUserType(){ return "Owner"; }  
    
    public int getPoints(){
        return 0; 
    }
}


