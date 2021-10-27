package com.market.factory;

import com.market.model.FundParameter;

import java.util.regex.Pattern;

public class FundFactory {
    public static FundParameter getFund(String code) {
        FundParameter parameter = null;
        code = code.toLowerCase();
        if (code.equals("bvpf")) {
            parameter = FundParameter.builder()
                    .baseURL("https://baovietfund.com.vn/san-pham/BVPF")
                    .pattern(Pattern.compile("<ul> <li> NAV/CCQ tại ng&#224;y (\\d{2}/\\d{2}/\\d{4}?) <strong>(\\d+.\\d+?) VNĐ</strong></li>"))
                    .code("BVPF")
                    .name("Quỹ Đầu tư Cổ phiếu Triển vọng Bảo Việt")
                    .dateMatchGroup(1)
                    .navMatchGroup(2)
                    .build();
        } else if (code.equals("pvbf")) {
            parameter = FundParameter.builder()
                    .baseURL("https://pvcomcapital.com.vn/quy-pvbf")
                    .pattern(Pattern.compile("NAV/CCQ&nbsp;</span></span>tại kỳ GD (\\d{2}/\\d{2}/\\d{4}?)<span class=\"(\\w+)\">: (\\d+,\\d+?).(\\d+) VND&nbsp;"))
                    .code("PVBF")
                    .name("QUỸ ĐẦU TƯ TRÁI PHIẾU PVCOM")
                    .dateMatchGroup(1)
                    .navMatchGroup(3)
                    .build();
        }
        return parameter;
    }
}
