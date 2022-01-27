package com.pig4cloud.pig.casee.entity;

import lombok.Data;

@Data
public class TaskReminder {

    /**
     * 0 新增
     * 1 节点前有不可跳过节点 无法填写。请确保从【公告前辅助】开始的必填步骤（包括委托任务）部完成。
     * 2 补录
     * 3 待审核
     * 4 委托中
     * 5 查看委托审核页面
     * 6 标的已关联
     * 7 驳回
     */
    public Integer hint;

    /**
     * 提示信息
     */
    public String hintInformation;

}
