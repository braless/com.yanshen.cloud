package top.javatool.canal.example.handler;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import top.javatool.canal.client.annotation.CanalTable;
import top.javatool.canal.client.context.CanalContext;
import top.javatool.canal.client.handler.EntryHandler;
import top.javatool.canal.client.model.CanalModel;
import top.javatool.canal.example.model.WeiBoUrl;


@Component
@CanalTable(value = "weibo_url")
public class WeiBoHandler implements EntryHandler<WeiBoUrl> {


    private Logger logger = LoggerFactory.getLogger(WeiBoHandler.class);


    @Override
    public void insert(WeiBoUrl weiBoUrl) {
        logger.info("微博用户信息uinsert message  {}", weiBoUrl);
    }

    @Override
    public void update(WeiBoUrl before, WeiBoUrl after) {
        CanalModel canal = CanalContext.getModel();
        logger.info(canal.toString());
        logger.info("微博用户信息update before {} ", before.toString());
        logger.info("微博用户信息uupdate after {}", after.toString());
    }

    @Override
    public void delete(WeiBoUrl weiBoUrl) {
        logger.info("微博用户信息udelete  {}", weiBoUrl);
    }
}
