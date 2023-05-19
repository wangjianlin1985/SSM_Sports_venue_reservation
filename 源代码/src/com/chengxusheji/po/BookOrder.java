package com.chengxusheji.po;

import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.NotEmpty;
import org.json.JSONException;
import org.json.JSONObject;

public class BookOrder {
    /*订单id*/
    private Integer orderId;
    public Integer getOrderId(){
        return orderId;
    }
    public void setOrderId(Integer orderId){
        this.orderId = orderId;
    }

    /*预订场地*/
    private Space spaceObj;
    public Space getSpaceObj() {
        return spaceObj;
    }
    public void setSpaceObj(Space spaceObj) {
        this.spaceObj = spaceObj;
    }

    /*场地类型*/
    private SpaceType spaceTypeObj;
    public SpaceType getSpaceTypeObj() {
        return spaceTypeObj;
    }
    public void setSpaceTypeObj(SpaceType spaceTypeObj) {
        this.spaceTypeObj = spaceTypeObj;
    }

    /*预订人*/
    private UserInfo userObj;
    public UserInfo getUserObj() {
        return userObj;
    }
    public void setUserObj(UserInfo userObj) {
        this.userObj = userObj;
    }

    /*预约开始时间*/
    @NotEmpty(message="预约开始时间不能为空")
    private String startTime;
    public String getStartTime() {
        return startTime;
    }
    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    /*预定小时*/
    @NotNull(message="必须输入预定小时")
    private Integer hours;
    public Integer getHours() {
        return hours;
    }
    public void setHours(Integer hours) {
        this.hours = hours;
    }

    /*总价*/
    @NotNull(message="必须输入总价")
    private Float totalMoney;
    public Float getTotalMoney() {
        return totalMoney;
    }
    public void setTotalMoney(Float totalMoney) {
        this.totalMoney = totalMoney;
    }

    /*订单备注*/
    private String orderMemo;
    public String getOrderMemo() {
        return orderMemo;
    }
    public void setOrderMemo(String orderMemo) {
        this.orderMemo = orderMemo;
    }

    /*订单状态*/
    @NotEmpty(message="订单状态不能为空")
    private String orderState;
    public String getOrderState() {
        return orderState;
    }
    public void setOrderState(String orderState) {
        this.orderState = orderState;
    }

    /*预订时间*/
    @NotEmpty(message="预订时间不能为空")
    private String orderTime;
    public String getOrderTime() {
        return orderTime;
    }
    public void setOrderTime(String orderTime) {
        this.orderTime = orderTime;
    }

    public JSONObject getJsonObject() throws JSONException {
    	JSONObject jsonBookOrder=new JSONObject(); 
		jsonBookOrder.accumulate("orderId", this.getOrderId());
		jsonBookOrder.accumulate("spaceObj", this.getSpaceObj().getSpaceNo());
		jsonBookOrder.accumulate("spaceObjPri", this.getSpaceObj().getSpaceNo());
		jsonBookOrder.accumulate("spaceTypeObj", this.getSpaceTypeObj().getSpaceTypeName());
		jsonBookOrder.accumulate("spaceTypeObjPri", this.getSpaceTypeObj().getSpaceTypeId());
		jsonBookOrder.accumulate("userObj", this.getUserObj().getName());
		jsonBookOrder.accumulate("userObjPri", this.getUserObj().getUser_name());
		jsonBookOrder.accumulate("startTime", this.getStartTime().length()>19?this.getStartTime().substring(0,19):this.getStartTime());
		jsonBookOrder.accumulate("hours", this.getHours());
		jsonBookOrder.accumulate("totalMoney", this.getTotalMoney());
		jsonBookOrder.accumulate("orderMemo", this.getOrderMemo());
		jsonBookOrder.accumulate("orderState", this.getOrderState());
		jsonBookOrder.accumulate("orderTime", this.getOrderTime().length()>19?this.getOrderTime().substring(0,19):this.getOrderTime());
		return jsonBookOrder;
    }}