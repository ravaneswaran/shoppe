package com.mnrc.administration.controllers.rest.ajax;

import com.mnrc.administration.services.UserRoleService;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/ajax/administration-app")
public class AdministrationAppAjaxController extends AbstractAjaxController {

    Logger logger = LoggerFactory.getLogger(AdministrationAppAjaxController.class);

    @Autowired
    private UserRoleService userRoleService;

    @GetMapping("/access/for-user-role/{userRoleId}")
    public ResponseEntity<?> toggleCanAccessAdministrationApp(
            @PathVariable(name = "userRoleId") String userRoleId,
            @RequestParam(name = "canAccessAdministrationApp") boolean canAccessAdministrationApp,
            HttpServletRequest httpServletRequest) {
        if(this.isNotUserLoggedIn(httpServletRequest)) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("response", "BAD-REQUEST");
            jsonObject.put("status", "failure");
            this.logger.error(jsonObject.toString());
            return ResponseEntity.badRequest().contentType(MediaType.APPLICATION_JSON).body(jsonObject.toString());
        }
        try {
            String response = this.userRoleService.toggleCanAccessAdministrationApp(userRoleId, canAccessAdministrationApp);
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("response", response);
            jsonObject.put("status", "success");
            this.logger.info(jsonObject.toString());
            return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(jsonObject.toString());
        } catch (Exception exception) {
            this.logger.error(exception.getMessage(), exception);
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("response", exception.getMessage());
            jsonObject.put("status", "failure");
            this.logger.error(jsonObject.toString());
            return ResponseEntity.badRequest().contentType(MediaType.APPLICATION_JSON).body(jsonObject);
        }
    }
}