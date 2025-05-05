package hoselabs.re_tap.global.jwt;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.HashMap;
import java.util.Map;

public class JsonResponse {
    private String message;
    private String status;
    private Object errors;
    private String code;

    public JsonResponse(String statusCode, String code, String message, Object errors) {
        this.message = message;
        this.status = statusCode;
        this.errors = errors;
        this.code = code;
    }

    public JsonResponse(String statusCode, String code, String message) {
        this(statusCode, code, message, null);
    }

    public String toJson() throws JsonProcessingException {
        Map<String, Object> jsonResponse = new HashMap<>();

        jsonResponse.put("message", message);
        jsonResponse.put("status", status);
        jsonResponse.put("errors", errors);
        jsonResponse.put("code", code);

        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(jsonResponse);
    }

    public String getStatusCode() {
        return status;
    }

    public void setStatusCode(String statusCode) {
        this.status = statusCode;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return errors;
    }

    public void setData(Object data) {
        this.errors = data;
    }
}
