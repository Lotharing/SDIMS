package top.lothar.sdims.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import top.lothar.sdims.entity.Repository;

public interface RepositoryDao {
	/*-------------------------------------------------仓库管理----------------------------------------------------*/
	/**
	  * 添加仓库
	 * @param repository
	 * @return
	 */
	int insertRepository(Repository repository);
	/**
	  * 根据仓库ID删除指定仓库
	 * @param repositoryId
	 * @return
	 */
	int deleteRepositoryById(long repositoryId);
	/**
	  * 更新仓库信息
	 * @param repository
	 * @return
	 */
	int updateRepository(Repository repository);
	/**
	 * 根据ID查询仓库
	 * @param repoId
	 * @return
	 */
	Repository queryRepositoryById(long repoId);
	/**
	 * 得到所有仓库信息列表，用于渲染订单select的仓库选择
	 * @return
	 */
	List<Repository> queryAllRepositoryList();
	/**
	  * 分页查询所有仓库信息(包括携带条件)
	 * @param repositoryName
	 * @param rowIndex
	 * @param pageSize
	 * @return
	 */
	List<Repository> queryRepositoryList(@Param("repositoryName")String repositoryName,@Param("rowIndex")int rowIndex, @Param("pageSize")int pageSize);
	/**
	  * 查询商品总数量(包括携带条件)
	 * @return
	 */
	int queryRepositoryCount(@Param("repositoryName")String repositoryName);
}
