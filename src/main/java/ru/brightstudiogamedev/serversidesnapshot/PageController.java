package ru.brightstudiogamedev.serversidesnapshot;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileNotFoundException;

@Controller
public class PageController {
/*
    @GetMapping("/@{id}")
    public String channel(@PathVariable String id,Model model,HttpServletRequest httpServletRequest){

        return "watch";
    }

*/

    @GetMapping({"/watch"})
    public String getWatch(Model model, HttpServletRequest request){
        String v=  request.getParameter("v");
        if(v!=null) {

            return "watch";
        }
        else {
            return "redirect:/";
        }
    }

    /*
    @RequestMapping("/c/{id}")
    public String getChannel(@PathVariable(value = "id")String id){
    return null;
    }
    */

    @GetMapping("/test")
    public String getTest2(){
        return "test1";
    }



    @GetMapping({"/index.html", "/","/index.php"})
    public String index(@CookieValue(name = "db",defaultValue = "none") String db,@CookieValue(name = "auth",defaultValue = "none")String auth,@CookieValue(name = "authif",defaultValue = "none")String authif,@CookieValue(name = "decode",defaultValue = "none") String decode,Model model) throws FileNotFoundException {
        model.addAttribute("ip",ServerSideSnapshotApplication.ip);
        File css = ResourceUtils.getFile("classpath:static/css/index.css");
        File script = ResourceUtils.getFile("classpath:static/js/main.js");
        model.addAttribute("mainScript", "js/main.js?id=" + css.lastModified());
        model.addAttribute("mainCss", "css/index.css?id=" + script.lastModified());
        model.addAttribute("auth",checkIsLogin(db,auth,authif,decode));
      //  model.addAttribute("videos",ServerSideSnapshotApplication.server.base.getLenta(0));
        if(ServerSideSnapshotApplication.server==null || ServerSideSnapshotApplication.server.base==null) {

        }else {
            model.addAttribute("videosJson", ServerSideSnapshotApplication.server.base.getLenta(0, null).toString().replaceAll("'", "&#039;").replaceAll("<", "&lt;").replaceAll(">", "&qt;"));
        }

        return "index";
    }
    public boolean checkIsLogin(String db,String auth,String authif,String decode){

        if(!db.equals("none") && !auth.equals("none") && !authif.equals("none") && !decode.equals("none")) {

            if(ServerSideSnapshotApplication.server==null || ServerSideSnapshotApplication.server.base==null)return false;
            return ServerSideSnapshotApplication.server.authification(db, auth, authif, decode) != null;
        }else{
            return false;
        }
    }

    public void addLoginAttribute(Model model){

        File fileCss = null;
        try {

            fileCss = ResourceUtils.getFile("classpath:static/css/index.css");
            File fileScript = ResourceUtils.getFile("classpath:static/js/login.js");
            model.addAttribute("mainScript","js/login.js?id="+fileScript.lastModified());
            model.addAttribute("mainCss","css/index.css?id="+fileCss.lastModified());

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }
    @GetMapping({"/login","login.html","login.php"})
    public String login(@CookieValue(name = "db",defaultValue = "none") String db,@CookieValue(name = "auth",defaultValue = "none")String auth,@CookieValue(name = "authif",defaultValue = "none")String authif,@CookieValue(name = "decode",defaultValue = "none") String decode, Model model) throws FileNotFoundException {
        //System.out.println(db+" "+auth+" "+authif+" "+decode);
        boolean isLogin = false;

         model.addAttribute("ip",ServerSideSnapshotApplication.ip);
        if((db+" "+auth+" "+authif+" "+decode).contains("none")|| (db+" "+auth+" "+authif+" "+decode).length()<=19){
            addLoginAttribute(model);
            return "login";
        }else{

                if(checkIsLogin(db,auth,authif,decode)){

                    return "redirect:/";//index(db,auth,authif,decode,model);
                }else{
                    addLoginAttribute(model);
                    return "login";
                }

        }


    }


}
