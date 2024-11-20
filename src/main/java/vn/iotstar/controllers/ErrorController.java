package vn.iotstar.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ErrorController {

    @GetMapping("/error")
    public String handleError() {
        return "Bạn không có quyền truy cập vào tài nguyên này!";
    }
}
