package vn.edu.fpt.paymentgateway.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@Profile("dev")
@RestController("/webhook/retrieve")
public class WeebhookRetrieve {

    private static final Logger log = LoggerFactory.getLogger(WeebhookRetrieve.class);

    @PostMapping("")
    public ResponseEntity<?> retrieve(HttpServletRequest httpServletRequest) {
        log.info(httpServletRequest.toString());
        return ResponseEntity.noContent().build();
    }
}
