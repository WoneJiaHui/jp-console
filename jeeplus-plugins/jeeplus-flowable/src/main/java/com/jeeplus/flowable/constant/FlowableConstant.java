package com.jeeplus.flowable.constant;

public interface FlowableConstant {
    /**
     * 约定的发起者节点id 前缀
     */
    String INITIATOR = "applyUserId";// 流程发起人变量
    String USERNAME = "userName"; //流程执行人
    String TITLE = "title"; //流程标题
    String SPECIAL_GATEWAY_BEGIN_SUFFIX = "_begin";
    String SPECIAL_GATEWAY_END_SUFFIX = "_end";
    String START_EVENT_LABEL = "开始";
    String START_EVENT_COMMENT = "发起流程";
    String END_EVENT_LABEL = "结束";
    String END_EVENT_COMMENT = "结束流程";
    String WAITING_EVENT_COMMENT = "等待审核";
    String SYSTEM_EVENT_COMMENT = "系统执行";
    String FLOW_ACTION = "_FLOW_ACTION_";
    String PROCESS_STATUS_CODE = "_process_status_code"; //流程状态码
    String PROCESS_STATUS_COMMENT = "_process_status_comment"; //流程状态描述
    String AFTER_ADDSIGN = "after";    //后加签
    String BEFORE_ADDSIGN = "before";    //前加签

}
