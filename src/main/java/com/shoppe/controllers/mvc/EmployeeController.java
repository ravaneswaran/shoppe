package com.shoppe.controllers.mvc;

import com.shoppe.controllers.BaseController;
import com.shoppe.services.AdminService;
import com.shoppe.ui.forms.Admin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/employee")
public class EmployeeController extends BaseController {

    Logger logger = LoggerFactory.getLogger(EmployeeController.class);

    @Autowired
    private AdminService adminService;

    @Override
    protected List<String> getMandatoryFields() {
        List<String> mandatoryFields = new ArrayList<>();

        mandatoryFields.add("firstName");
        mandatoryFields.add("emailId");
        mandatoryFields.add("mobileNo");

        return mandatoryFields;
    }

    @GetMapping("/home")
    public ModelAndView adminHome(HttpServletRequest httpServletRequest){
        if(this.isNotUserLoggedIn(httpServletRequest)) {
            return new ModelAndView("redirect:/");
        }
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("employee-create");
        return modelAndView;
    }

    @PostMapping("/create")
    public ModelAndView addAdmin(@Valid @ModelAttribute("admin") Admin admin, BindingResult bindingResult, HttpServletRequest httpServletRequest) {
        if(this.isNotUserLoggedIn(httpServletRequest)) {
            return new ModelAndView("redirect:/");
        }

        if(!bindingResult.hasErrors()){
            Admin response = this.adminService.addAdmin(admin.getFirstName(), admin.getMiddleInitial(), admin.getLastName(), admin.getEmailId(), admin.getUniqueId(), admin.getMobileNo());
            if(null != response) {
                String redirect = String.format("redirect:/admin/info?uuid=%s", response.getAdminId());
                return new ModelAndView(redirect);
            } else {
                ModelAndView modelAndView = new ModelAndView();
                modelAndView.setViewName("employee-create");
                modelAndView.addObject("admin",admin);
                modelAndView.addObject("errorMessage", "Unable to add admin information...");
                return modelAndView;
            }
        } else {
            ModelAndView modelAndView = new ModelAndView();
            modelAndView.setViewName("employee-create");
            modelAndView.addObject("admin", admin);
            modelAndView.addObject("errorMessage", this.getError(bindingResult));

            return modelAndView;
        }
    }

    @GetMapping("/info")
    public ModelAndView getAdminInfo(@RequestParam(name = "uuid") String uuid, HttpServletRequest httpServletRequest){
        if(this.isNotUserLoggedIn(httpServletRequest)) {
            return new ModelAndView("redirect:/");
        }

        if(null != uuid && !"".equals(uuid)){
            Admin admin = this.adminService.getAdmin(uuid);
            if(null != admin){
                ModelAndView modelAndView = new ModelAndView();
                modelAndView.setViewName("employee-info");
                modelAndView.addObject("admin", admin);
                return modelAndView;
            } else {
                ModelAndView modelAndView = new ModelAndView("redirect:/404");
                modelAndView.setStatus(HttpStatus.NOT_FOUND);
                return modelAndView;
            }
        } else {
            logger.error("Request parameter uuid is found to be invalid...");
            ModelAndView modelAndView = new ModelAndView();
            modelAndView.setViewName("employee-info");
            return modelAndView;
        }
    }

    @GetMapping("/list")
    public ModelAndView listAdmins(HttpServletRequest httpServletRequest){
        if(this.isNotUserLoggedIn(httpServletRequest)) {
            return new ModelAndView("redirect:/");
        }

        ModelAndView modelAndView = new ModelAndView();
        List<Admin> admins =  this.adminService.listAdmins();
        modelAndView.setViewName("employee-list");
        modelAndView.addObject("admins", admins);

        return modelAndView;
    }

    @GetMapping("/block")
    public ModelAndView blockAdmin(@RequestParam(name = "uuid") String uuid, HttpServletRequest httpServletRequest){
        if(this.isNotUserLoggedIn(httpServletRequest)) {
            return new ModelAndView("redirect:/");
        }

        ModelAndView modelAndView = new ModelAndView();
        this.adminService.blockAdmin(uuid);
        List<Admin> admins =  this.adminService.listAdmins();
        modelAndView.setViewName("employee-list");
        modelAndView.addObject("admins", admins);

        return modelAndView;
    }

    @GetMapping("/unblock")
    public ModelAndView unblockAdmin(@RequestParam(name = "uuid") String uuid, HttpServletRequest httpServletRequest){
        if(this.isNotUserLoggedIn(httpServletRequest)) {
            return new ModelAndView("redirect:/");
        }

        ModelAndView modelAndView = new ModelAndView();
        this.adminService.unblockAdmin(uuid);
        List<Admin> admins =  this.adminService.listAdmins();
        modelAndView.setViewName("employee-list");
        modelAndView.addObject("admins", admins);

        return modelAndView;
    }

    @GetMapping("/delete")
    public ModelAndView deleteAdmin(@RequestParam(name = "uuid") String uuid, HttpServletRequest httpServletRequest){
        if(this.isNotUserLoggedIn(httpServletRequest)) {
            return new ModelAndView("redirect:/");
        }

        ModelAndView modelAndView = new ModelAndView();
        this.adminService.deleteAdmin(uuid);
        List<Admin> admins =  this.adminService.listAdmins();
        modelAndView.setViewName("employee-list");
        modelAndView.addObject("admins", admins);

        return modelAndView;
    }
}