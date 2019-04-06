package top.lothar.sdims.util;

public class PageCalculator {
	/**
	 * Service->Dao时页码对应DAO中的检索范围
	  *  例如第二页，pageIndex=2  就是从0到2-1 * pagesize 这里的检索
	  *  ？前边是执行条件，:前边是true执行 ， 后边是false执行
	  *  也就是说前台传过来的是页码，对应在dao中检索把页码转换为查询的limit起始位置
	 * 2 页  每页5条	起始位置就是5 
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	public static int calculatorRowIndex(int pageIndex , int pageSize) {
		return (pageIndex > 0)?(pageIndex-1)*pageSize:0;
	}
	
}
