package vn.edu.fpt.paymentgateway.third_party.vnpay.api;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class VNPAYApi {

    @Value("${vnpay.queryDr.url}")
    private String queryTransUrl;

    @Value("${vnpay.tmnCode}")
    private String tmnCode;

    private final String contentType = "Application/json";

//    public VNPayQueryDrResponse queryTrans(int tid) {
//        final String requestId = UUID.randomUUID().toString();
//        final String vnpVersion = VNPAYConstants.VNPAY_VERSION_VALUE;
//        final String vnpCommand = VNPAYConstants.VNPAY_QUERY_DR_COMMAND;
//        final String txnRef = VNPAYUtils.getRandomNumber(50);
//        final String hashData = requestId + "|" + vnpVersion + "|" + tmnCode + "|" + txnRef;
//        return null;
//    }
}
