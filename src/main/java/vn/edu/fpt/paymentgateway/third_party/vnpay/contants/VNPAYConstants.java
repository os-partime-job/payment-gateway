package vn.edu.fpt.paymentgateway.third_party.vnpay.contants;

public class VNPAYConstants {

    public static final String VNPAY_VERSION_VALUE = "2.1.0";
    public static final String VNPAY_COMMAND_PAY = "pay";
    public static final String VNPAY_CURR_CODE_VALUE = "VND";
    public static final String VNPAY_QUERY_DR_COMMAND = "querydr";

    public static class Params {
        public static final String VNPAY_VERSION = "vnp_Version";
        public static final String VNPAY_COMMAND = "vnp_Command";
        public static final String VNPAY_TMN_CODE = "vnp_TmnCode";
        public static final String VNPAY_AMOUNT = "vnp_Amount";
        public static final String VNPAY_CURR_CODE = "vnp_CurrCode";
        public static final String VNPAY_BANK_CODE = "vnp_BankCode";
        public static final String VNPAY_CREATE_DATE = "vnp_CreateDate";
        public static final String VNPAY_IP_ADDR = "vnp_IpAddr";
        public static final String VNPAY_LOCALE = "vnp_Locale";
        public static final String VNPAY_ORDER_INFO = "vnp_OrderInfo";
        public static final String VNPAY_RETURN_URL = "vnp_ReturnUrl";
        public static final String VNPAY_EXPIRE_DATE = "vnp_ExpireDate";
        public static final String VNPPAY_TNX_REF = "vnp_TxnRef";
        public static final String VNPAY_HASH = "vnp_SecureHash";
        public static final String VNPAY_ORDER_TYPE = "vnp_OrderType";
    }

    public static enum TransactionStatus {
        SUCCESS("00"),
        PENDING("01"),
        FAIL("02");
        private final String status;

        TransactionStatus(String status) {
            this.status = status;
        }

        public String getStatus() {
            return status;
        }
    }

    public static enum TransactionMessage {
        _00("Giao dịch thành công"),
        _01("Giao dịch chưa hoàn tất"),
        _02("Giao dịch bị lỗi"),
        _04("Giao dịch đảo (Khách hàng đã bị trừ tiền tại Ngân hàng nhưng GD chưa thành công ở VNPAY)"),
        _05("VNPAY đang xử lý giao dịch này (GD hoàn tiền)"),
        _06("VNPAY đã gửi yêu cầu hoàn tiền sang Ngân hàng (GD hoàn tiền)"),
        _07("Giao dịch bị nghi ngờ gian lận"),
        _08("GD Hoàn trả bị từ chối"),
        _001("Không rõ");
        private final String message;

        TransactionMessage(String message) {
            this.message = message;
        }

        public String getMessage() {
            return message;
        }

        public static TransactionMessage fromValue(String value) {
            for (TransactionMessage message : TransactionMessage.values()) {
                if (message.name().equalsIgnoreCase("_" + value)) {
                    return message;
                }
            }
            return TransactionMessage._001;
        }
    }
}
