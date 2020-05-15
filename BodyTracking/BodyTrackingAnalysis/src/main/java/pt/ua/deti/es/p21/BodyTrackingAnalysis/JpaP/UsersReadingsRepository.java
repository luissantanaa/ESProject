/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pt.ua.deti.es.p21.BodyTrackingAnalysis.JpaP;
import org.springframework.data.jpa.repository.JpaRepository;


/**
 *
 * @author alexandre
 */

public interface UsersReadingsRepository extends JpaRepository<UserReading, String> {
    
}

