package com.mnrc.administration.controllers.mvc;

import com.mnrc.administration.enums.SessionAttribute;
import com.mnrc.administration.services.UserRoleService;
import com.mnrc.administration.ui.forms.LoginForm;
import com.mnrc.administration.ui.forms.UserRoleForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/user/role")
public class UserRoleController extends BaseMVCController {

    @Autowired
    private UserRoleService userRoleService;

    @Override
    protected List<String> getMandatoryFields() {
        List<String> mandatoryFields = new ArrayList<>();
        mandatoryFields.add("roleName");
        return mandatoryFields;
    }

    @GetMapping("/view")
    public ModelAndView userRoleHome(HttpServletRequest httpServletRequest){

        if(this.isNotUserLoggedIn(httpServletRequest)) {
            return new ModelAndView("redirect:/");
        }

        List<UserRoleForm> userRoleForms = this.userRoleService.getUserRoles();
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/user-role");
        modelAndView.addObject("userRoleForms", userRoleForms);
        return modelAndView;
    }

    @GetMapping("/add")
    public ModelAndView returnToUserRoleHome(HttpServletRequest httpServletRequest){
        return userRoleHome(httpServletRequest);
    }

    @PostMapping("/add")
    public ModelAndView addRole(@Valid UserRoleForm userRoleForm, BindingResult bindingResult, HttpServletRequest httpServletRequest){

        if(this.isNotUserLoggedIn(httpServletRequest)) {
            return new ModelAndView("redirect:/");
        }

        if(!bindingResult.hasErrors()){
            LoginForm login = (LoginForm) httpServletRequest.getSession().getAttribute(SessionAttribute.LOGGED_IN_USER.toString());
            try {
                this.userRoleService.addUserRole(userRoleForm.getRoleName(), String.format("%s %s", login.getFirstName(), login.getLastName()).trim());
                List<UserRoleForm> userRoleForms = this.userRoleService.getUserRoles();
                ModelAndView modelAndView = new ModelAndView();
                modelAndView.setViewName("/user-role");
                modelAndView.addObject("userRoleForm", userRoleForm);
                modelAndView.addObject("userRoleForms", userRoleForms);
                return modelAndView;
            } catch (Exception exception) {
                List<UserRoleForm> userRoleForms = this.userRoleService.getUserRoles();
                ModelAndView modelAndView = new ModelAndView();
                modelAndView.setViewName("/user-role");
                modelAndView.addObject("userRoleForm", userRoleForm);
                modelAndView.addObject("userRoleForms", userRoleForms);

                if(DataIntegrityViolationException.class.equals(exception.getClass())){
                    modelAndView.addObject("errorMessage", "User Role already exists...");
                } else {
                    modelAndView.addObject("errorMessage", exception.getMessage());
                }
                return modelAndView;
            }
        } else {
            List<UserRoleForm> userRoleForms = this.userRoleService.getUserRoles();
            ModelAndView modelAndView = new ModelAndView();
            modelAndView.setViewName("/user-role");
            modelAndView.addObject("userRoleForm", userRoleForm);
            modelAndView.addObject("userRoleForms", userRoleForms);
            modelAndView.addObject("errorMessage", this.getError(bindingResult));
            return modelAndView;
        }
    }

    @PostMapping("/edit")
    public ModelAndView editRole(@Valid UserRoleForm userRoleForm, BindingResult bindingResult, HttpServletRequest httpServletRequest){
        if(this.isNotUserLoggedIn(httpServletRequest)) {
            return new ModelAndView("redirect:/");
        }

        if(!bindingResult.hasErrors()){
            LoginForm login = (LoginForm) httpServletRequest.getSession().getAttribute(SessionAttribute.LOGGED_IN_USER.toString());
            try {
                this.userRoleService.editUserRole(userRoleForm.getRoleId(), userRoleForm.getRoleName(), String.format("%s, %s", login.getFirstName(), login.getLastName()));
                List<UserRoleForm> userRoleForms = this.userRoleService.getUserRoles();
                ModelAndView modelAndView = new ModelAndView();
                modelAndView.setViewName("/user-role");
                modelAndView.addObject("userRoleForm", userRoleForm);
                modelAndView.addObject("userRoleForms", userRoleForms);
                return modelAndView;
            } catch (Exception exception) {
                List<UserRoleForm> userRoleForms = this.userRoleService.getUserRoles();
                ModelAndView modelAndView = new ModelAndView();
                modelAndView.setViewName("/user-role");
                modelAndView.addObject("userRoleForm", userRoleForm);
                modelAndView.addObject("userRoleForms", userRoleForms);

                if(DataIntegrityViolationException.class.equals(exception.getClass())){
                    modelAndView.addObject("errorMessage", "User Role already exists...");
                } else {
                    modelAndView.addObject("errorMessage", exception.getMessage());
                }
                return modelAndView;
            }
        } else {
            List<UserRoleForm> userRoleForms = this.userRoleService.getUserRoles();
            ModelAndView modelAndView = new ModelAndView();
            modelAndView.setViewName("/user-role");
            modelAndView.addObject("userRoleForm", userRoleForm);
            modelAndView.addObject("userRoleForms", userRoleForms);
            modelAndView.addObject("errorMessage", this.getError(bindingResult));
            return modelAndView;
        }
    }

    @GetMapping("/edit")
    public ModelAndView editRole(@RequestParam(name = "uuid") String userRoleId, HttpServletRequest httpServletRequest){
        if(this.isNotUserLoggedIn(httpServletRequest)) {
            return new ModelAndView("redirect:/");
        }

        List<UserRoleForm> userRoleForms = this.userRoleService.getUserRoles();

        if(null == userRoleId || "".equals(userRoleId)){
            ModelAndView modelAndView = new ModelAndView();
            modelAndView.setViewName("/user-role");
            modelAndView.addObject("userRoleForms", userRoleForms);
            modelAndView.addObject("errorMessage", "Invalid uuid parameter...");

            return modelAndView;
        }

        try {
            UserRoleForm userRoleForm = this.userRoleService.getUserRole(userRoleId);
            userRoleForm.setAction("/user/role/edit");

            ModelAndView modelAndView = new ModelAndView();
            modelAndView.setViewName("/user-role");
            modelAndView.addObject("userRoleForm", userRoleForm);
            modelAndView.addObject("userRoleForms", userRoleForms);

            return modelAndView;
        } catch (Exception e) {
            ModelAndView modelAndView = new ModelAndView();
            modelAndView.setViewName("/user-role");
            modelAndView.addObject("userRoleForms", userRoleForms);
            modelAndView.addObject("errorMessage", e.getMessage());

            return modelAndView;
        }
    }

    @GetMapping("/delete")
    public ModelAndView deleteRole(@RequestParam(name = "uuid") String userRoleId, HttpServletRequest httpServletRequest){
        if(this.isNotUserLoggedIn(httpServletRequest)) {
            return new ModelAndView("redirect:/");
        }

        if(null == userRoleId || "".equals(userRoleId)){
            ModelAndView modelAndView = new ModelAndView();
            modelAndView.setViewName("/user-role");
            List<UserRoleForm> userRoleForms = this.userRoleService.getUserRoles();
            modelAndView.addObject("userRoleForms", userRoleForms);
            modelAndView.addObject("errorMessage", "Invalid uuid parameter...");

            return modelAndView;
        }

        try {
            this.userRoleService.deleteUserRole(userRoleId);
            ModelAndView modelAndView = new ModelAndView();
            modelAndView.setViewName("redirect:/user/role/add");
            return modelAndView;
        } catch (Exception e) {
            List<UserRoleForm> userRoleForms = this.userRoleService.getUserRoles();
            ModelAndView modelAndView = new ModelAndView();
            modelAndView.setViewName("/user-role");
            modelAndView.addObject("userRoleForms", userRoleForms);
            modelAndView.addObject("errorMessage", e.getMessage());

            return modelAndView;
        }
    }
}
