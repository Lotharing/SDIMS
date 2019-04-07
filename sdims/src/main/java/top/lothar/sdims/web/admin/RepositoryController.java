package top.lothar.sdims.web.admin;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.ObjectMapper;

import top.lothar.sdims.dto.RepositoryExecution;
import top.lothar.sdims.entity.Repository;
import top.lothar.sdims.service.RepositoryService;
import top.lothar.sdims.util.HttpServletRequestUtil;
import top.lothar.sdims.util.PageBean;
/**
 * 仓库
 * @author Lothar
 *
 */
@Controller
@RequestMapping("/admin")
public class RepositoryController {
	
	@Autowired
	private RepositoryService repositoryService;
	
	private PageBean<Repository> pageBean;
	/**
	 * 得到仓库列表信息
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/getrepositorylist",method=RequestMethod.GET)
	private Map<String, Object> getRepositoryList(HttpServletRequest request){
		Map<String, Object> modelMap = new HashMap<String, Object>();
		//获取页码
		int pageIndex = HttpServletRequestUtil.getInt(request, "pageIndex");
		//获取每页展示的数据量
		int pageSize = 4;
		//获取查询条件（仓库名）
		String repositoryName = HttpServletRequestUtil.getString(request, "repositoryName");
		//非空判断
		if (pageIndex > -1) {
			RepositoryExecution repositoryExecution = repositoryService.getRepositoryList(repositoryName, pageIndex, pageSize);
			pageBean = new PageBean<Repository>();
			//总记录数
			pageBean.setAllRowCounts(repositoryExecution.getCount());
			//仓库列表
			pageBean.setDatas(repositoryExecution.getRepositoryList());
			//每页记录数
			pageBean.setPageSize(3);
			//当前页
			pageBean.setCurPage(pageIndex);
			int sumPages = PageBean.getSumPages(repositoryExecution.getCount(),pageSize);
			//总页数
			pageBean.setSumPages(sumPages);
			//定义数组(用于页码展示组件)
			int[] tempNum = new int[sumPages];
			for (int i = 0; i<sumPages; i++) {
				tempNum[i]=i+1;
			}
			//页码数字
			pageBean.setNavigatepageNums(tempNum);
			modelMap.put("success", true);
			modelMap.put("pageBean", pageBean);
			modelMap.put("stateInfo", repositoryExecution.getStateInfo());
		}else {
			modelMap.put("success", false);
			modelMap.put("errMsg", "pageIndex or pageSize is Null");
		}
		return modelMap;
	}
	/**
	 * 添加仓库信息
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/addrepository",method=RequestMethod.POST)
	private Map<String, Object> addRepository(HttpServletRequest request){
		Map<String, Object> modelMap = new HashMap<String, Object>();
		String repositoryStr = HttpServletRequestUtil.getString(request, "repositoryStr");
		//JSON转化后的仓库存储对象
		Repository repository = null;
		//存放json的对象
		ObjectMapper objectMapper = new ObjectMapper();
		//接受json形式的Employee信息
		try {
			//使用ObjectMapper类把请求中的json信息存放在repository实体类中
			repository = objectMapper.readValue(repositoryStr, Repository.class);
		} catch (Exception e) {
			// TODO: handle exception
			modelMap.put("success", false);
			modelMap.put("errMsg", e.getMessage());
			return modelMap;
		}
		try {
			//非空判断
			if (repository!=null) {
				int effectNum = repositoryService.addRepository(repository);
				if (effectNum < 0) {
					modelMap.put("success", false);
					modelMap.put("errMsg", "插入失败");
				}else {
					modelMap.put("success", true);
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			modelMap.put("success", false);
			modelMap.put("errMsg", e.getMessage());
			return modelMap;
		}
		return modelMap;
	}
	/**
	 * 根据前台传的ID移除对应仓库
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/removerepository",method=RequestMethod.GET)
	private Map<String, Object> removeRepository(HttpServletRequest request){
		Map<String, Object> modelMap = new HashMap<String,Object>();
		long repoId = HttpServletRequestUtil.getLong(request, "repoId");
		try {
			int effectNum = repositoryService.removeRepository(repoId);
			if (effectNum < 0) {
				modelMap.put("success", false);
				modelMap.put("errMsg", "删除失败");
			}else {
				modelMap.put("success", true);
				modelMap.put("successMsg", "删除成功");
			}
		} catch (Exception e) {
			// TODO: handle exception
			modelMap.put("success", false);
			modelMap.put("errMsg", e.getMessage());
			return modelMap;
		}
		return modelMap;
	}
	/**
	 * 根据前台传递的仓库ID去查询信息并返回前台
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="getrepositorybyid",method=RequestMethod.GET)
	private Map<String, Object> getRepositoryById(HttpServletRequest request){
		Map<String, Object> modelMap = new HashMap<String, Object>();
		Long repoId = HttpServletRequestUtil.getLong(request, "repoId");
		Repository repository = new Repository();
		try {
			repository = repositoryService.getRepositoryById(repoId);
			if (repository!=null) {
				modelMap.put("success", true);
				modelMap.put("repository", repository);
			}else {
				modelMap.put("success", false);
				modelMap.put("errMsg", "查询错误");
			}
		} catch (Exception e) {
			// TODO: handle exception
			modelMap.put("success", false);
			modelMap.put("errMsg", e.getMessage());
		}
		return modelMap;
	}
	/**
	 * 根据前台传过来的信息对应ID进行更新
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/modifyrepository",method=RequestMethod.POST)
	private Map<String, Object> modifyRepository(HttpServletRequest request){
		Map<String, Object> modelMap = new HashMap<String,Object>();
		//这里的数据要包含ID因为是根据ID进行更新
		String repositoryStr = HttpServletRequestUtil.getString(request, "repositoryStr");
		//JSON转化后的员工存储对象
		Repository repository = null;
		//存放json的对象
		ObjectMapper objectMapper = new ObjectMapper();
		//接受json形式的Employee信息
		try {
			//使用ObjectMapper类把请求中的json信息存放在Employee实体类中
			repository = objectMapper.readValue(repositoryStr, Repository.class);
			//设置更新的时间
			repository.setUpdateTime(new Date());
		} catch (Exception e) {
			// TODO: handle exception
			modelMap.put("success", false);
			modelMap.put("errMsg", e.getMessage());
			return modelMap;
		}
		try {
			int effectNum = repositoryService.modifyRepository(repository);
			if (effectNum < 1) {
				modelMap.put("success", false);
				modelMap.put("errMsg", "更新员工失败");
			}else {
				modelMap.put("success", true);
			}
		} catch (Exception e) {
			// TODO: handle exception
			modelMap.put("success", false);
			modelMap.put("errMsg", e.getMessage());
			return modelMap;
		}
		return modelMap;
	}
}
