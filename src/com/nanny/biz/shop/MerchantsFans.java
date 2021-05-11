package com.nanny.biz.shop;

import java.util.Map;

public interface MerchantsFans {
	String fansinit(String shopID,Map<String, String> option);

	String getArea(String type);

}
