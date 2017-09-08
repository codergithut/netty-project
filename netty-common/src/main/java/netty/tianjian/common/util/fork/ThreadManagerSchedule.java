package netty.tianjian.common.util.fork;

import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author <a href="mailto:Administrator@gtmap.cn">Administrator</a>
 * @version 1.0, 2017/9/7
 * @description
 */
public class ThreadManagerSchedule {
    public static ThreadPoolTaskExecutor getThreadPoolTaskExecutor() {
        ThreadPoolTaskExecutor threadPoolTaskExecutor = new ThreadPoolTaskExecutor();

        /**
         * @author <a href="mailto:tianjian@gtmap.cn">tianjian</a>
         * @description 线程池维护线程所允许的空闲时间
         */
        threadPoolTaskExecutor.setKeepAliveSeconds(300);

        /**
         * @author <a href="mailto:tianjian@gtmap.cn">tianjian</a>
         * @description 最大线程数
         */
        threadPoolTaskExecutor.setMaxPoolSize(25);

        /**
         * @author <a href="mailto:tianjian@gtmap.cn">tianjian</a>
         * @description 最大线程数
         */
        threadPoolTaskExecutor.setMaxPoolSize(10);

        /**
         * @author <a href="mailto:tianjian@gtmap.cn">tianjian</a>
         * @description 核心线程数
         */
        threadPoolTaskExecutor.setCorePoolSize(3);

        /**
         * (1)ThreadPoolExecutor.AbortPolicy策略，是默认的策略,处理程序遭到拒绝将抛出运行时 RejectedExecutionException。
         * (2)ThreadPoolExecutor.CallerRunsPolicy策略 ,调用者的线程会执行该任务,如果执行器已关闭,则丢弃.
         * (3)ThreadPoolExecutor.DiscardPolicy策略，不能执行的任务将被丢弃.
         * (4)ThreadPoolExecutor.DiscardOldestPolicy策略，如果执行程序尚未关闭，则位于工作队列头部的任务将被删除，然后重试执行程序（如果再次失败，则重复此过程）.
         */
        threadPoolTaskExecutor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());

        return threadPoolTaskExecutor;
    }
}
