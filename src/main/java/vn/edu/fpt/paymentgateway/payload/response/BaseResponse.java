package vn.edu.fpt.paymentgateway.payload.response;

import lombok.Data;

@Data
public class BaseResponse {
    private Meta meta;
    private Object data;

    public BaseResponse(int code, String message, Object data) {
        this.meta = new Meta(code, message);
        this.data = data;
    }

    public static BaseResponse ok(Object data) {
        return new BaseResponse(200, "OK", data);
    }

    public static BaseResponse internalErr(String message) {
        return new BaseResponse(500, "Internal Error", message);
    }

    public static BaseResponse badReq(String message) {
        return new BaseResponse(400, "Bad Request", message);
    }

    public static class Meta {
        private final int code;
        private final String message;

        public Meta(int code, String message) {
            this.code = code;
            this.message = message;
        }

        public int getCode() {
            return code;
        }

        public String getMessage() {
            return message;
        }
    }
}
