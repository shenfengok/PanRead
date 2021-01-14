package geek.me.javaapi.config;

import geek.me.javaapi.service.SpiderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class MySchedule {

    @Autowired
    private SpiderService spiderService;

    /**
     * 更新极客列表
     */
    @Scheduled(cron = "0 0 */1 * * ?")
//    @Scheduled(cron = "*/5 * * * * ?")
    public void updateQueueList() throws Exception {


        System.out.print("doing updating jike queue list");
        //极客时间加入待采集
        spiderService.queJike();
    }

    /**
     * 更新book列表到/sync/目录
     * 成本和收益不成正比，暂时不做，后面手工维护要同步到网盘的列表
     */
//    @Scheduled(cron = "0 0 */1 * * ?")
//    @Scheduled(cron = "*/5 * * * * ?")
    public void move2Sync() throws InterruptedException {


        System.out.print("moving to sync list");
        //极客时间全部
        //其他入库
    }

    /**
     * 更新book列表
     */
//    @Scheduled(cron = "0 0 */1 * * ?")
    @Scheduled(cron = "*/5 * * * * ?")
    public void updateBookList() throws Exception {


        System.out.print("doing updating book list");
        //极客时间全部
        //其他入库

        spiderService.saveOutLine();
    }

    /**
     * 更新book文件
     * 每小时
     */
    @Scheduled(cron = "0 0 */1 * * ?")
    public void updateBookFiles() throws InterruptedException {


        System.out.print("doing updating book files");
        Thread.sleep(100000);
    }
}
