package top.lothar.sdims;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * 配置spring和junit整合，junit启动时加载springIOC容器
 * @author Lothar
 *
 */
//使用这个类进行单元测试
@RunWith(SpringJUnit4ClassRunner.class)
//告诉JUnit spring配置文件的位置
@ContextConfiguration({"classpath:spring/spring-dao.xml","classpath:spring/spring-service.xml"})
public class BaseTest {

	
}
