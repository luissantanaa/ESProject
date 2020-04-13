/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proj.es.p21.JpaP;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author alexandre
 */
public interface UsersRepository extends JpaRepository<String, String> {
    
}
