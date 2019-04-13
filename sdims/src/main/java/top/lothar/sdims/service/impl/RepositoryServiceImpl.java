package top.lothar.sdims.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import top.lothar.sdims.dao.RepositoryDao;
import top.lothar.sdims.dto.RepositoryExecution;
import top.lothar.sdims.entity.Repository;
import top.lothar.sdims.service.RepositoryService;
import top.lothar.sdims.util.PageCalculator;

@Service
public class RepositoryServiceImpl implements RepositoryService {
	
	@Autowired
	private RepositoryDao repositoryDao;
	
	@Override
	public int addRepository(Repository repository) {
		// TODO Auto-generated method stub
		return repositoryDao.insertRepository(repository);
	}

	@Override
	public int removeRepository(long repoId) {
		// TODO Auto-generated method stub
		return repositoryDao.deleteRepositoryById(repoId);
	}

	@Override
	public int modifyRepository(Repository repository) {
		// TODO Auto-generated method stub
		return repositoryDao.updateRepository(repository);
	}

	@Override
	public Repository getRepositoryById(long repoId) {
		// TODO Auto-generated method stub
		return repositoryDao.queryRepositoryById(repoId);
	}

	@Override
	public List<Repository> getAllRepositoryList() {
		// TODO Auto-generated method stub
		return repositoryDao.queryAllRepositoryList();
	}

	@Override
	public RepositoryExecution getRepositoryList(String repositoryName, int pageIndex, int pageSize) {
		// TODO Auto-generated method stub
		int rowIndex = PageCalculator.calculatorRowIndex(pageIndex, pageSize);
		List<Repository> repositoryList = repositoryDao.queryRepositoryList(repositoryName, rowIndex, pageSize);
		int count = repositoryDao.queryRepositoryCount(repositoryName);
		RepositoryExecution repositoryExecution = new RepositoryExecution();
		if (repositoryList!=null) {
			repositoryExecution.setRepositoryList(repositoryList);
			repositoryExecution.setCount(count);
			repositoryExecution.setStateInfo("成功得到数据");
		}else {
			return new RepositoryExecution("没有对应数据");
		}
		return repositoryExecution;
	}

}
