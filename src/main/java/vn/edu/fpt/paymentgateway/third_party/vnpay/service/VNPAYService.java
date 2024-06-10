package vn.edu.fpt.paymentgateway.third_party.vnpay.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import vn.edu.fpt.paymentgateway.third_party.vnpay.contants.VNPAYConstants;
import vn.edu.fpt.paymentgateway.third_party.vnpay.contants.VNPAYPaytypeEnum;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class VNPAYService {

    @Value("${vnpay.pay.url}")
    private String paymentUrl;

    @Value("${vnpay.tmnCode}")
    private String tmnCode;

    @Value("${vnpay.locale}")
    private String locale;

    @Value("${vnpay.expriedTransaction.minutes}")
    private int exprieTrans;

    @Value("${payment.callbackUrl}")
    private String callBackUrl;

    @Value("${vnpay.hashSecret}")
    private String hashSecret;

    private static final String localTimezone = "Etc/GMT+7";


    private static final SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");

    public String createPaymentUrl(HttpServletRequest request, String orderId, String orderInfo, long amount, VNPAYPaytypeEnum vnpayPaytypeEnum) {
        final Calendar currentTime = Calendar.getInstance(TimeZone.getTimeZone(localTimezone));
        final Calendar expiredTime = ((Calendar) currentTime.clone());
        expiredTime.add(Calendar.MINUTE, exprieTrans);
        Map<String, String> vnpayParam = new HashMap<>();
        vnpayParam.put(VNPAYConstants.Params.VNPAY_AMOUNT, String.valueOf(amount * 100));
        vnpayParam.put(VNPAYConstants.Params.VNPAY_BANK_CODE, vnpayPaytypeEnum.getValue());
        vnpayParam.put(VNPAYConstants.Params.VNPAY_COMMAND, VNPAYConstants.VNPAY_COMMAND_PAY);
        vnpayParam.put(VNPAYConstants.Params.VNPAY_CREATE_DATE, formatter.format(currentTime.getTime()));
        vnpayParam.put(VNPAYConstants.Params.VNPAY_CURR_CODE, VNPAYConstants.VNPAY_CURR_CODE_VALUE);
        vnpayParam.put(VNPAYConstants.Params.VNPAY_EXPIRE_DATE, formatter.format(expiredTime.getTime()));
        vnpayParam.put(VNPAYConstants.Params.VNPAY_IP_ADDR, VNPAYUtils.getIpAddress(request));
        vnpayParam.put(VNPAYConstants.Params.VNPAY_LOCALE, locale);
        vnpayParam.put(VNPAYConstants.Params.VNPAY_ORDER_INFO, orderInfo);
        vnpayParam.put(VNPAYConstants.Params.VNPAY_ORDER_TYPE, "other");
        final String serverCallbackUrl = callBackUrl + "?orderId=" + orderId;
        vnpayParam.put(VNPAYConstants.Params.VNPAY_RETURN_URL, serverCallbackUrl);
        vnpayParam.put(VNPAYConstants.Params.VNPAY_TMN_CODE, tmnCode);
        vnpayParam.put(VNPAYConstants.Params.VNPPAY_TNX_REF, VNPAYUtils.getRandomNumber(50));
        vnpayParam.put(VNPAYConstants.Params.VNPAY_VERSION, VNPAYConstants.VNPAY_VERSION_VALUE);


        final String finalQuery = VNPAYUtils.buildQuery(vnpayParam);
        final String hashData = VNPAYUtils.hashAllFields(vnpayParam, hashSecret);

        return paymentUrl + finalQuery + "&" + VNPAYConstants.Params.VNPAY_HASH + "=" + hashData;
    }

//    public static void main(String[] args) throws UnsupportedEncodingException {
//        String s = "https%3A%2F%2Fwebhook.site%2F9cc4493c-bbbc-4429-bc72-0ff9cf8836be";
//        System.out.println(URLDecoder.decode(s, StandardCharsets.UTF_8.toString()));
//    }
}
