package vn.edu.fpt.paymentgateway.third_party.vnpay.contants;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Getter;
import lombok.ToString;

@Getter
public enum VNPAYPaytypeEnum {
    BANK("VNBANK"),
    BANK_ACCOUNT("INTCARD"),
    QR("VNPAYQR");
    private final String value;

    VNPAYPaytypeEnum(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }

    @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
    public static VNPAYPaytypeEnum fromValue(String value) {
        for (VNPAYPaytypeEnum paytypeEnum : VNPAYPaytypeEnum.values()) {
            if (paytypeEnum.name().equalsIgnoreCase(value)) {
                return paytypeEnum;
            }
        }
        return null;
    }
}
