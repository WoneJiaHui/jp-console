package com.jeeplus.quartz.task;

import com.jeeplus.quartz.domain.Task;
import org.quartz.DisallowConcurrentExecution;

@DisallowConcurrentExecution
public class TestTask extends Task {

    @Override
    public void run() {
        System.out.println ( "这是测试任务TestTask。" );

    }

}
