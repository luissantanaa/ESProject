/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proj.es.p21.RestP;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import proj.es.p21.JpaP.JointCollectionRepository;
import proj.es.p21.JpaP.LoggerRep;
import proj.es.p21.JpaP.UsersRepository;
/**
 *
 * @author alexandre
 */

@Controller
public class RestController {
    
    @Autowired
    UsersRepository usersRep;
    
    @Autowired
    JointCollectionRepository jointsRep;
    
    
    //user kafka appender para depois meter isto automatico
    @Autowired
    LoggerRep logRep;
    
    
    @RequestMapping("/login")
    public String get_login(Model m){
        return null;
    }
    
    
    @RequestMapping("/login/{username}/{password}")
    public String login(Model m){
        return null;
    }
    
    
    @RequestMapping("/real_time")
    public String real_time_page(Model m){
        return "real_time";
    }
    
    
    @RequestMapping("/real_time/data")
    public String real_time_socket(){
        return null;
    }
    
    
    @RequestMapping("/stored")
    public String stored_page(Model m){
        return "stored_data";
    }
    
    
    @RequestMapping("/stored/data")
    public String stored_info(Model m){
        return null;
    }
}
