package vn.edu.fpt.paymentgateway.third_party.vnpay.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import vn.edu.fpt.paymentgateway.third_party.vnpay.contants.VNPAYConstants;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class VNPAYService {

    @Value("${vnpay.paymentUrl}")
    private String paymentUrl;

    @Value("${vnpay.tmnCode}")
    private String tmnCode;

    @Value("${vnpay.bankCodeAtm}")
    private String bankCodeAtm;

    @Value("${vnpay.locale}")
    private String locale;

    @Value("${vnpay.expriedTransaction.minutes}")
    private int exprieTrans;

    @Value("${vnpay.returnUrl}")
    private String callBackUrl;

    @Value("${vnpay.hashSecret}")
    private String hashSecret;

    private static final String localTimezone = "Etc/GMT+7";


    private static final SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");


    public String createPaymentUrl(HttpServletRequest request, String orderInfo, int amount) {
        final Calendar currentTime = Calendar.getInstance(TimeZone.getTimeZone(localTimezone));

        Map<String, String> vnpayParam = new HashMap<>();
        vnpayParam.put(VNPAYConstants.Params.VNPAY_VERSION, VNPAYConstants.VNPAY_VERSION_VALUE);
        vnpayParam.put(VNPAYConstants.Params.VNPAY_COMMAND, VNPAYConstants.VNPAY_COMMAND_PAY);
        vnpayParam.put(VNPAYConstants.Params.VNPAY_TMN_CODE, tmnCode);
        vnpayParam.put(VNPAYConstants.Params.VNPAY_AMOUNT, String.valueOf(amount));
        vnpayParam.put(VNPAYConstants.Params.VNPAY_BANK_CODE, bankCodeAtm);
        vnpayParam.put(VNPAYConstants.Params.VNPAY_CREATE_DATE, formatter.format(currentTime.getTime()));

        currentTime.add(Calendar.MINUTE, exprieTrans);
        vnpayParam.put(VNPAYConstants.Params.VNPAY_EXPIRE_DATE, formatter.format(currentTime.getTime()));

        vnpayParam.put(VNPAYConstants.Params.VNPAY_CURR_CODE, VNPAYConstants.VNPAY_CURR_CODE_VALUE);
        vnpayParam.put(VNPAYConstants.Params.VNPAY_IP_ADDR, VNPAYUtils.getIpAddress(request));
        vnpayParam.put(VNPAYConstants.Params.VNPAY_LOCAL, locale);
        vnpayParam.put(VNPAYConstants.Params.VNPAY_ORDER_INFO, orderInfo);
        vnpayParam.put(VNPAYConstants.Params.VNPAY_RETURN_URL, callBackUrl);
        vnpayParam.put(VNPAYConstants.Params.VNPPAY_TNX_REF, VNPAYUtils.getRandomNumber(100));

        final String hashData = VNPAYUtils.hashAllFields(vnpayParam, hashSecret);
        vnpayParam.put(VNPAYConstants.Params.VNPAY_HASH, hashData);

        String finalQuery = VNPAYUtils.buildQuery(vnpayParam);

        return paymentUrl + finalQuery;
    }
}
