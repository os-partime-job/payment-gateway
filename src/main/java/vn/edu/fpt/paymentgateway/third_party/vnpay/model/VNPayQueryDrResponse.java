package vn.edu.fpt.paymentgateway.third_party.vnpay.model;

import lombok.Data;

@Data
public class VNPayQueryDrResponse {
    private String vnp_ResponseId;
    private String vnp_Command;
    private String vnp_TxnRef;
    private String vnp_TmnCode;
    private String vnp_OrderInfo;
    private long vnp_Amount;
    private int vnp_ResponseCode;
    private String vnp_Message;
    private String vnp_BankCode;
    private long vnp_PayDate;
    private long vnp_TransactionNo;
    private String vnp_TransactionType;
    private int vnp_TransactionStatus;
    private String vnp_SecureHash;
}
