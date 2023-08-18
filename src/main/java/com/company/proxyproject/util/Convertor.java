package com.company.proxyproject.util;

import java.math.RoundingMode;
import java.text.DecimalFormat;

public class Convertor {
    public static Double fromAmperes(Double amperes, Double minLimit, Double maxLimit) {
        if (amperes < 4) {
            return getFormatted(minLimit);
        } else if (amperes > maxLimit) {
            return getFormatted(maxLimit);
        } else {
            return getFormatted(((maxLimit - minLimit) / (20 - 4)) * (amperes - 4) + minLimit);
        }
    }

    public static Double fromKgfCm2ToBar(Double amount) {
        return getFormatted(amount * 0.980665);
    }

    public static Double fromBarToKgfCm2(Double amount) {
        return getFormatted(amount * 1.01972);
    }


    public static Double getFormatted(Double amount) {
        DecimalFormat df = new DecimalFormat("###.###");
        df.setRoundingMode(RoundingMode.HALF_UP);
        return Double.parseDouble(df.format(amount));

    }

}
