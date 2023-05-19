package com.chengxusheji.po;

import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.NotEmpty;
import org.json.JSONException;
import org.json.JSONObject;

public class SpaceType {
    /*类型id*/
    private Integer spaceTypeId;
    public Integer getSpaceTypeId(){
        return spaceTypeId;
    }
    public void setSpaceTypeId(Integer spaceTypeId){
        this.spaceTypeId = spaceTypeId;
    }

    /*场地类型*/
    @NotEmpty(message="场地类型不能为空")
    private String spaceTypeName;
    public String getSpaceTypeName() {
        return spaceTypeName;
    }
    public void setSpaceTypeName(String spaceTypeName) {
        this.spaceTypeName = spaceTypeName;
    }

    /*价格(小时)*/
    @NotNull(message="必须输入价格(小时)")
    private Float price;
    public Float getPrice() {
        return price;
    }
    public void setPrice(Float price) {
        this.price = price;
    }

    public JSONObject getJsonObject() throws JSONException {
    	JSONObject jsonSpaceType=new JSONObject(); 
		jsonSpaceType.accumulate("spaceTypeId", this.getSpaceTypeId());
		jsonSpaceType.accumulate("spaceTypeName", this.getSpaceTypeName());
		jsonSpaceType.accumulate("price", this.getPrice());
		return jsonSpaceType;
    }}