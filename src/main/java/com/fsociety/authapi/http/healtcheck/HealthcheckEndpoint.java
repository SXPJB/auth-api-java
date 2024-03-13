package com.fsociety.authapi.http.healtcheck;

import com.fsociety.authapi.utils.ResponseBody;
import com.fsociety.authapi.utils.ResponseEntityBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/healthcheck")
public class HealthcheckEndpoint {

  @GetMapping
  public ResponseEntity<ResponseBody<String>> healthcheck() {
    return new ResponseEntityBuilder<String>()
        .withData("OK")
        .withStatus(HttpStatus.OK)
        .withSuccess(true)
        .withMessage("System is healthy")
        .build();
  }
}
