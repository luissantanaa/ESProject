/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proj.es.p21.BodyTracking.JpaP;

import org.springframework.data.jpa.repository.JpaRepository;
import proj.es.p21.BodyTracking.JpaP.User;

/**
 *
 * @author alexandre
 */
public interface UsersRepository extends JpaRepository<User, String> {
    
}
