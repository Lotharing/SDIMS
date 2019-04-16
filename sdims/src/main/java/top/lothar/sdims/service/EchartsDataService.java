package top.lothar.sdims.service;

import java.util.List;

import top.lothar.sdims.entity.EchartsData;

public interface EchartsDataService {
	/**
	 * 得到12月份的12个存储总数和总额的实体-采购单
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	List<EchartsData> getEchartsDataList();
	/**
	 * 得到12月份的12个存储总数和总额的实体-销售单
	 * @return
	 */
	List<EchartsData> getSaleOrderEchartsDataList();
}
