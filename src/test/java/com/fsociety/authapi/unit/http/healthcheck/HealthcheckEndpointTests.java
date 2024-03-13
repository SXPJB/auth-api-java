package com.fsociety.authapi.unit.http.healthcheck;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import com.fsociety.authapi.http.healtcheck.HealthcheckEndpoint;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(HealthcheckEndpoint.class)
class HealthcheckEndpointTests {

  @Autowired private MockMvc mockMvc;

  @Test
  @WithMockUser
  void healthcheck() throws Exception {
    this.mockMvc
        .perform(get("/healthcheck"))
        .andDo(
            result -> {
              assertEquals(200, result.getResponse().getStatus());
              assertEquals("application/json", result.getResponse().getContentType());
              assertEquals(
                  "{\"success\":true,\"data\":\"OK\",\"message\":\"System is healthy\"}",
                  result.getResponse().getContentAsString());
            });
  }
}
