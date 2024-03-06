package com.fsociety.authapi.http.register;

import com.fsociety.authapi.app.register.ConfirmUserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping("/v1/confirm-user")
public class ConfirmationUserEndpoint {

  private final ConfirmUserService confirmUserService;

  public ConfirmationUserEndpoint(ConfirmUserService confirmUserService) {
    this.confirmUserService = confirmUserService;
  }

  @GetMapping("/{username}/{confirmationCode}")
  public ModelAndView confirmUser(
      @PathVariable String username, @PathVariable String confirmationCode) {
    ModelAndView modelAndView = new ModelAndView();
    if (confirmUserService.confirmUser(username, confirmationCode)) {
      modelAndView.setViewName("user-confirmation-page");
      return modelAndView;
    }
    modelAndView.setViewName("user-confirmation-error-page");
    return modelAndView;
  }
}
