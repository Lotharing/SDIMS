package top.lothar.sdims.service;

import java.util.List;

import top.lothar.sdims.dto.RepositoryExecution;
import top.lothar.sdims.entity.Repository;

public interface RepositoryService {
	/**
	 * 添加仓库
	 * @param repository
	 * @return
	 */
	int addRepository(Repository repository);
	/**
	 * 移除仓库
	 * @param repoId
	 * @return
	 */
	int removeRepository(long repoId);
	/**
	 * 更新仓库
	 * @param repository
	 * @return
	 */
	int modifyRepository(Repository repository);
	/**
	 * 根据ID查询仓库
	 * @param repoId
	 * @return
	 */
	Repository getRepositoryById(long repoId);
	/**
	 * 查询所有仓库列表，并在前台渲染在订单生成的select中的仓库信息
	 * @return
	 */
	List<Repository> getAllRepositoryList();
	/**
	 * 条件分页查询仓库
	 * @param repositoryName
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	RepositoryExecution getRepositoryList(String repositoryName,int pageIndex,int pageSize);
}
