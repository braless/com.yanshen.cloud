package com.yanshen.dev.trigger;

//import com.stanwind.sync.CanalConstant;
//import com.stanwind.sync.SyncContext;
//import com.stanwind.sync.anno.SyncListener;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.stereotype.Service;
//
///**
// * @Auther: cyc
// * @Date: 2023/3/7 20:17
// * @Description:
// */
//@Service
//public class weiBoTrigger {
//
//        private Logger logger = LoggerFactory.getLogger(this.getClass());
//        @SyncListener(table = {"weibo_url"}, type = CanalConstant.EventType.ALL)
//        public void updateBookInfo(long bookId) {
//            logger.info("更新同步book_info..." + bookId);
//            SyncContext.getCurrentRowData();
//        }
//
//}
