package schedulers;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

public class BirthdayScheduler {
    public BirthdayScheduler() throws SchedulerException {
        SchedulerFactory factory = new StdSchedulerFactory();
        Scheduler scheduler = factory.getScheduler();

        JobDetail jobDetail = JobBuilder.newJob(BirthdayJob.class).build();

        Trigger trigger = TriggerBuilder.newTrigger()
                .withIdentity("birthday")
                .withSchedule(CronScheduleBuilder.cronSchedule("0 0 12 * * ?"))
                .startNow()
                .build();
        scheduler.scheduleJob(jobDetail, trigger);
        scheduler.start();
    }
}
