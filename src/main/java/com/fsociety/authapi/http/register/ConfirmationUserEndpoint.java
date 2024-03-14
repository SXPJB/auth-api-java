package com.fsociety.authapi.http.register;

import com.fsociety.authapi.app.register.ConfirmUserService;
import com.fsociety.authapi.utils.NotFoundException;
import com.fsociety.authapi.utils.ResponseBody;
import com.fsociety.authapi.utils.ResponseEntityBuilder;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1")
@AllArgsConstructor
public class ConfirmationUserEndpoint {

  private final ConfirmUserService confirmUserService;

  @GetMapping("/confirm-user/{username}/{confirmationCode}")
  public ResponseEntity<ResponseBody<Void>> confirmUser(
      @PathVariable String username, @PathVariable String confirmationCode) {
    var msg =
        """
              Having an error...
              Thank You confirm your registration!
              Your registration has been completed successfully.
            """;
    if (confirmUserService.confirmUser(username, confirmationCode)) {
      msg =
          "Thank You confirm your registration!"
              + "\n Your registration has been completed successfully.";
      return new ResponseEntityBuilder<Void>().withStatus(HttpStatus.OK).withMessage(msg).build();
    }
    return new ResponseEntityBuilder<Void>()
        .withStatus(HttpStatus.BAD_REQUEST)
        .withMessage(msg)
        .build();
  }

  @GetMapping("/resend-confirmation-email/{username}")
  public ResponseEntity<ResponseBody<Void>> reconfirmUser(@PathVariable String username) {
    try {
      confirmUserService.resendConfirmationEmail(username);
      return new ResponseEntityBuilder<Void>()
          .withStatus(HttpStatus.OK)
          .withSuccess(true)
          .withMessage("Confirmation email sent successfully!")
          .build();
    } catch (NotFoundException e) {
      return new ResponseEntityBuilder<Void>()
          .withStatus(HttpStatus.BAD_REQUEST)
          .withSuccess(false)
          .withMessage(e.getMessage())
          .build();
    }
  }
}
