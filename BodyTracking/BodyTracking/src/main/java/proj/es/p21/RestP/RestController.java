/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proj.es.p21.RestP;

import java.lang.ProcessBuilder.Redirect;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import proj.es.p21.JpaP.JointCollectionRepository;
import proj.es.p21.JpaP.LoggerRep;
import proj.es.p21.JpaP.User;
import proj.es.p21.JpaP.UsersRepository;
/**
 *
 * @author alexandre
 */

@Controller
public class RestController {

    private HashMap<String, Boolean> loggedIn = new HashMap<String, Boolean>();
    
    @Autowired
    UsersRepository usersRep;
    
    @Autowired
    JointCollectionRepository jointsRep;
    
    
    //user kafka appender para depois meter isto automatico
    @Autowired
    LoggerRep logRep;


    @GetMapping("/home")
    public String get_home(Model m){
        return "index.html";
    }

    @GetMapping("/home/{username}")
    public String get_home(Model m){
        model.addAttribute("username", username);
        return "index.html";
    }

    
    
    @GetMapping("/login")
    public String get_login(Model m){
        return "login";
    }
    
    
    @PostMapping("/login")
    public String login(@ModelAttribute User user, Model m){
        User user_tmp = usersRep.findById(user.getUsername());
        if(user_tmp.getPass().equals(user.getPass())){
            loggedIn.put(user.getUsername(), true);
            m.addAttribute("username", user.getUsername());
        }

        return "home";
    }
    
    
    @GetMapping("/signin")
    public String get_sign(Model m){
        return "register";
    }
    
    
    @PostMapping("/signin")
    public String sign(@ModelAttribute User user){
        usersRep.save(user);
        return "login";
    }
    
    
    @RequestMapping("/real_time/{username}")
    public String real_time_page(Model m){
        if(loggedIn.containsKey(username)){
            if(loggedIn.get(username)){
                m.addAttribute("username", username);
                return "real_time";
            }
        }
        return "home";
        
    }
    
    
    @RequestMapping("/real_time/data")
    public String real_time_socket(){
        return null;
    }
    
    
    @RequestMapping("/stored/{username}")
    public String stored_page(Model m){
        if(loggedIn.containsKey(username)){
            if(loggedIn.get(username)){
                m.addAttribute("username", username);
                return "stored_data";
            }
        }
        return "redirect:home";
        
    }
    
    
    @RequestMapping("/stored/data")
    public String stored_info(Model m){
        return null;
    }
}
