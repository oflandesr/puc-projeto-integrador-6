package br.com.pucc.projetointegradorvi.configs.handlers;
//
//import java.util.HashMap;
//import java.util.Map;
//
//import org.springframework.boot.web.servlet.error.ErrorController;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import jakarta.servlet.http.HttpServletRequest;
//
//@RestController
//public class CustomErrorController implements ErrorController {
//
//    @RequestMapping("/error")
//    public ResponseEntity<Map<String, Object>> handleError(HttpServletRequest request) {
//        Map<String, Object> response = new HashMap<>();
//        Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
//        Throwable throwable = (Throwable) request.getAttribute("javax.servlet.error.exception");
//
//        response.put("status", statusCode != null ? statusCode : HttpStatus.INTERNAL_SERVER_ERROR.value());
//        response.put("error", HttpStatus.valueOf(statusCode != null ? statusCode : 500).getReasonPhrase());
//        response.put("message", throwable != null ? throwable.getMessage() : "Unknown error");
//
//        return new ResponseEntity<>(response, HttpStatus.valueOf(statusCode != null ? statusCode : 500));
//    }
//}