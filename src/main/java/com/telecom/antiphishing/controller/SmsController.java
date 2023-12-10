package com.telecom.antiphishing.controller;

import com.telecom.antiphishing.model.Sms;
import com.telecom.antiphishing.service.SmsProcessorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/***
 * @author - Kiryl Karpuk
 */
@RestController
@RequestMapping("/sms")
@RequiredArgsConstructor
public class SmsController {

    private final SmsProcessorService smsProcessorService;

    @PostMapping
    public ResponseEntity<String> processSms(@RequestBody Sms sms) {
        return ResponseEntity.ok(smsProcessorService.processSms(sms));
    }

}
