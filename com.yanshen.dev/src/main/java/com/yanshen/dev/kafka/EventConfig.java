//package kafka;
//
//import com.yanshen.boot.canal.CanalUtils;
//import com.yanshen.boot.canal.ChangeHandleManager;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.boot.context.event.ApplicationStartedEvent;
//import org.springframework.context.ApplicationListener;
//import org.springframework.stereotype.Component;
//
///**
// * @Auther: cyc
// * @Date: 2023/3/6 22:47
// * @Description:
// */
//@Component
//@Slf4j
//public class EventConfig implements ApplicationListener<ApplicationStartedEvent> {
//    @Override
//    public void onApplicationEvent(ApplicationStartedEvent event) {
//        EnvironmentAlone.createInstance(event.getApplicationContext().getEnvironment());
//        String dbIp =EnvironmentAlone.getInstance().getEnvironmentParam("canal.ip");
//        String tables =EnvironmentAlone.getInstance().getEnvironmentParam("tables");
//        String canalName =EnvironmentAlone.getInstance().getEnvironmentParam("canal.destination");
//        CanalUtils.getInstance().attachChangeHandler(ChangeHandleManager.getChangeHandler()).connServer(dbIp,tables,canalName);
//    }
//}
//
