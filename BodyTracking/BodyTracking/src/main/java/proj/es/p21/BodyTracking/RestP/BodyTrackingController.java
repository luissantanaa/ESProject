/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proj.es.p21.BodyTracking.RestP;

import java.lang.ProcessBuilder.Redirect;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import proj.es.p21.BodyTracking.JpaP.JointCollection;
import proj.es.p21.BodyTracking.JpaP.JointCollectionRepository;
import proj.es.p21.BodyTracking.JpaP.SortedUser;
import proj.es.p21.BodyTracking.JpaP.User;
import proj.es.p21.BodyTracking.JpaP.UsersRepository;
/**
 *
 * @author alexandre
 */

@Controller
public class BodyTrackingController {


    Logger logger = LogManager.getLogger(BodyTrackingController.class);

    private HashMap<String, Boolean> loggedIn = new HashMap<>();
    
    @Autowired
    UsersRepository usersRep;
    
    @Autowired
    JointCollectionRepository jointsRep;
    
    
    private JSONObject elk_OBJ;

    
    @RequestMapping(value = "/home", method = RequestMethod.GET)
    public String get_home(@RequestParam(required = false) String username , Model m){
        if(username != null){    
            m.addAttribute("username", username);
        }
        return "index";
    }

    
    
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String get_login(Model m){
        m.addAttribute("user", new User());
        return "login_file";
    }
    
    
    
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String login(@ModelAttribute User user, Model m){
        System.out.println("\n" + user.toString());
        Optional<User> optional_user = usersRep.findById(user.getUsername());
        User user_tmp = optional_user.get();
        System.out.print(user_tmp.toString());
        
        elk_OBJ = new JSONObject();
        //String pattern = "yyyy-MM-dd HH:mm:ss";
        //DateFormat df = new SimpleDateFormat(pattern);

        //Date today = Calendar.getInstance().getTime();

        //String reportDate = df.format(today);

        if(user_tmp.getPassword().equals(user.getPassword())){
            loggedIn.put(user.getUsername(), true);
            m.addAttribute("username", user.getUsername());
            /*
            List<JSONObject> ljson = new ArrayList<>();
            
            String[] fields = {"data", "log"};
            
            String[] types = {"string", "string"};
                    
            for(int i=0;i<fields.length;i++){
                JSONObject tmp = new JSONObject();
                tmp.put("field", fields[i]);
                tmp.put("optional", false);
                tmp.put("type", types[i]);
                ljson.add(tmp);
            }
            
            
            JSONObject tmp2 = new JSONObject();
            
             
            tmp2.put("type", "struct");
            tmp2.put("fields", ljson);
            tmp2.put("name", "esp21_logs");
            tmp2.put("optional", false);
            
            
            
            elk_OBJ.put("data", reportDate.replace(" ", "T") + "Z");
            */
            elk_OBJ.put("log", "LOGIN");
            
            
            //JSONObject mess = new JSONObject();
            
            //mess.put("schema", tmp2);
            //mess.put("payload", elk_OBJ);
            
            
            logger.debug(elk_OBJ.toString());
            
            
        }else{
            System.out.print("AQUI1");
            /*
            
            List<JSONObject> ljson = new ArrayList<>();
            
            String[] fields = {"data", "id_user", "username"};
            
            String[] types = {"string", "int32", "string"};
                    
            for(int i=0;i<fields.length;i++){
                JSONObject tmp = new JSONObject();
                tmp.put("field", fields[i]);
                tmp.put("optional", false);
                tmp.put("type", types[i]);
                ljson.add(tmp);
            }
            
            
            JSONObject tmp2 = new JSONObject();
            
             
            tmp2.put("type", "struct");
            tmp2.put("fields", ljson);
            tmp2.put("name", "esp21_alarms");
            tmp2.put("optional", false);
            
            
            
            elk_OBJ.put("data", reportDate.replace(" ", "T") + "Z");
            */
            elk_OBJ.put("username", user.getUsername());
            elk_OBJ.put("id_user", user_tmp.getId());
            
            
            
            //JSONObject mess = new JSONObject();
            
            //mess.put("schema", tmp2);
            //mess.put("payload", elk_OBJ);
            
            
            logger.warn(elk_OBJ.toString());
            System.out.print("AQUI");
        }

        
        return "index";
    }
    
    
    @RequestMapping(value = "/signup", method = RequestMethod.GET)
    public String get_sign(Model m){
        m.addAttribute("user", new User());
        
        
        return "register";
    }
    
    
    @RequestMapping(value = "/signup", method = RequestMethod.POST)
    public String sign(@ModelAttribute User user){
        List<User> list_u = usersRep.findAll();
        System.out.println(list_u.size());
        user.setId(list_u.size());
        
        usersRep.save(user);
        
        
        elk_OBJ = new JSONObject();
        /*
        String pattern = "yyyy-MM-dd HH:mm:ss";
        DateFormat df = new SimpleDateFormat(pattern);

        Date today = Calendar.getInstance().getTime();

        String reportDate = df.format(today);
        List<JSONObject> ljson = new ArrayList<>();
            
        String[] fields = {"data", "log"};

        String[] types = {"string", "string"};

        for(int i=0;i<fields.length;i++){
            JSONObject tmp = new JSONObject();
            tmp.put("field", fields[i]);
            tmp.put("optional", false);
            tmp.put("type", types[i]);
            ljson.add(tmp);
        }


        JSONObject tmp2 = new JSONObject();


        tmp2.put("type", "struct");
        tmp2.put("fields", ljson);
        tmp2.put("name", "esp21_logs");
        tmp2.put("optional", false);



        elk_OBJ.put("data", reportDate.replace(" ", "T") + "Z");
        */
        elk_OBJ.put("log", "SIGNUP");
       


        //JSONObject mess = new JSONObject();

        //mess.put("schema", tmp2);
        //mess.put("payload", elk_OBJ);


        
        logger.debug(elk_OBJ.toString());
        
        return "redirect:login";
    }
    
    
    @RequestMapping(value = "/real_time", method = RequestMethod.GET)
    public String real_time_page(@RequestParam(required = false) String username, Model m){
        System.out.print(username);
        if(username != null){
            if(loggedIn.containsKey(username)){
                if(loggedIn.get(username)){
                    
                    m.addAttribute("username", username);
                    return "real_time";
                }
            }
        }
        return "redirect:home";
        
    }
    
    
    

    
    @RequestMapping("/real_time/data")
    public String real_time_socket(){
        return null;
    }
    
    
    @RequestMapping(value = "/stored", method = RequestMethod.GET)
    public String stored_page(@RequestParam(required = false) String username, Model m){
        if(username != null){
            if(loggedIn.containsKey(username)){
                if(loggedIn.get(username)){
                    m.addAttribute("username", username);
                    List<JointCollection> tmp_list = jointsRep.findAll();
                    List<SortedUser> users_sorted = new ArrayList<>();
                    for(JointCollection j : tmp_list){
                        if(users_sorted.isEmpty()){
                            users_sorted.add(new SortedUser(j.getName(), j.getDate_reading_day()));
                        }else{
                            boolean equals = false;
                            for(SortedUser s: users_sorted){
                                if(s.equals(new SortedUser(j.getName(), j.getDate_reading_day()))){
                                    equals = true;
                                    break;
                                }
                            }

                            if(!equals){
                                users_sorted.add(new SortedUser(j.getName(), j.getDate_reading_day()));
                            }
                        }
                    }
                    
                    m.addAttribute("readings", users_sorted);
                    return "stored_data";
                }
            }
        }
        return "redirect:home";
        
    }
    
    
    
    @RequestMapping(value = "/stored/filtered", method = RequestMethod.GET)
    public String stored_page_sorted(@RequestParam(required = true) String username, @RequestParam(required = true) String patient, @RequestParam(required = true) String date, Model m){
        
        if(loggedIn.containsKey(username)){
            if(loggedIn.get(username)){
                m.addAttribute("username", username);
                
                List<JointCollection> tmp_list = jointsRep.findAll();
                List<JointCollection> coords_list = new ArrayList<>();
                
                for(JointCollection j : tmp_list){
                    if(j.getName().equals(patient) && j.getDate_reading_day().equals(date)){
                        coords_list.add(j);
                    }
                }
                
                m.addAttribute("patient", patient);
                
                m.addAttribute("date", date);
                m.addAttribute("moves", coords_list);
                
                return "stored_data";
            }
        }
        
        return "redirect:home";
        
    }
    
    @RequestMapping("/stored/data")
    public String stored_info(Model m){
        return null;
    }
    
    
    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String get_logout(@RequestParam(required = false) String username, Model m){
        if(loggedIn.containsKey(username)){
            if(loggedIn.get(username)){
                loggedIn.put(username, false);
            }
        }
        return "redirect:home";
    }
}
