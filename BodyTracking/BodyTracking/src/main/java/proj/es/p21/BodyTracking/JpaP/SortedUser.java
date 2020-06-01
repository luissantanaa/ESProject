/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proj.es.p21.BodyTracking.JpaP;

import java.util.Objects;

/**
 *
 * @author alexandre
 */
public class SortedUser {
    private String username;
    private String date_reading;

    public SortedUser(String username, String date_reading) {
        this.username = username;
        this.date_reading = date_reading;
    }
    

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getDate_reading() {
        return date_reading;
    }

    public void setDate_reading(String date_reading) {
        this.date_reading = date_reading;
    }


    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final SortedUser other = (SortedUser) obj;
        if (!Objects.equals(this.username, other.username) || !Objects.equals(this.date_reading, other.date_reading)) {
            return false;
        }
        
        return true;
    }

    @Override
    public String toString() {
        return  username +  " "+date_reading ;
    }
    
    
    
    
    
}
