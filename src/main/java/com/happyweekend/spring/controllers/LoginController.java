package com.happyweekend.spring.controllers;

import com.happyweekend.models.Login;
import com.happyweekend.service.LoginService;
import com.happyweekend.spring.form.LoginForm;
import org.apache.tiles.autotag.core.runtime.annotation.Parameter;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
public class LoginController extends HandlerInterceptorAdapter {


    private LoginService loginService = new LoginService();

    public static final String USER_LOGIN_SESSION = "session-user";

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object controller) throws Exception {

        /*if(request.getSession().getAttribute(USER_LOGIN_SESSION)==null) {

            login(new LoginForm("l", "12345678"), null, request.getSession(), new BeanPropertyBindingResult(null,null));
        }
        //TODO REMOVE THIS
        return true;*/
        //TODO UNCOMENT THIS
        String uri = request.getRequestURI();
        if(uri.endsWith("login")||uri.endsWith("register")
                ||uri.matches(".*/resources.*")
                ||uri.matches(".*/webjars.*")){
            return true;
        }

        if(request.getSession().getAttribute(USER_LOGIN_SESSION) != null) {
            return true;
        }

        response.sendRedirect(request.getContextPath()+"/login");
        return false;
    }

    @GetMapping(path = "/login")
    public String index(){
        return "login.html";
    }

    @PostMapping(path = "/login")
    public String login(LoginForm loginForm, @RequestParam("submit") String submit, HttpSession session, BindingResult result){

        if(submit!=null){
            if(submit.equals("register"))return ("redirect:/register");
        }
        if(result.hasErrors()){
            return "login.html";
        }

        Login login = new Login();

        login.setLoginName(loginForm.getUsername());
        login.setPassword(loginForm.getPassword());

        if(!loginService.validLogin(login)){
            result.addError(new ObjectError("loginForm","{Login incorrect}"));
            return "login.html?error";
        }
        login = loginService.loadByUsername(login.getLoginName());

        session.setAttribute(USER_LOGIN_SESSION,login);


        return "redirect:/";
    }

    @RequestMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }

    public static boolean isAuthenticated(){
        ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        HttpSession session = attr.getRequest().getSession();

        return session.getAttribute(USER_LOGIN_SESSION) != null;
    }


}
