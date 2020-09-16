package geek.me.javaapi;

import geek.me.javaapi.baidu.js.JsFunc;
import geek.me.javaapi.baidu.service.PcsDownService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import javax.script.ScriptException;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
class JavaApiApplicationTests {

	@Autowired
	private PcsDownService pcsTransferService;
	@Test
	void contextLoads() {
	}

	@Test
	void dlinkTest() throws InterruptedException, ScriptException, NoSuchMethodException {
		List<String> list = new ArrayList<>();
//		list.add("477988620064680");//08丨Flink集群资源管理器支持.mp4
		list.add("17345280166692");
		String res = pcsTransferService.dlink(list);
		Assert.isTrue(!StringUtils.isEmpty(res));
	}

	@Test
	void getLogIdTest() throws Exception {
		String a = JsFunc.getLogId();
		String b = "test";
	}

}
