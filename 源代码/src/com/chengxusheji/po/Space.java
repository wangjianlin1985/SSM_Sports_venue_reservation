package com.chengxusheji.po;

import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.NotEmpty;
import org.json.JSONException;
import org.json.JSONObject;

public class Space {
    /*场地号*/
    @NotEmpty(message="场地号不能为空")
    private String spaceNo;
    public String getSpaceNo(){
        return spaceNo;
    }
    public void setSpaceNo(String spaceNo){
        this.spaceNo = spaceNo;
    }

    /*场地类型*/
    private SpaceType spaceTypeObj;
    public SpaceType getSpaceTypeObj() {
        return spaceTypeObj;
    }
    public void setSpaceTypeObj(SpaceType spaceTypeObj) {
        this.spaceTypeObj = spaceTypeObj;
    }

    /*场地图片*/
    private String spacePhoto;
    public String getSpacePhoto() {
        return spacePhoto;
    }
    public void setSpacePhoto(String spacePhoto) {
        this.spacePhoto = spacePhoto;
    }

    /*价格(小时)*/
    @NotNull(message="必须输入价格(小时)")
    private Float spacePrice;
    public Float getSpacePrice() {
        return spacePrice;
    }
    public void setSpacePrice(Float spacePrice) {
        this.spacePrice = spacePrice;
    }

    /*楼层*/
    @NotEmpty(message="楼层不能为空")
    private String floorNum;
    public String getFloorNum() {
        return floorNum;
    }
    public void setFloorNum(String floorNum) {
        this.floorNum = floorNum;
    }

    /*占用状态*/
    @NotEmpty(message="占用状态不能为空")
    private String spaceState;
    public String getSpaceState() {
        return spaceState;
    }
    public void setSpaceState(String spaceState) {
        this.spaceState = spaceState;
    }

    /*场地描述*/
    @NotEmpty(message="场地描述不能为空")
    private String spaceDesc;
    public String getSpaceDesc() {
        return spaceDesc;
    }
    public void setSpaceDesc(String spaceDesc) {
        this.spaceDesc = spaceDesc;
    }

    public JSONObject getJsonObject() throws JSONException {
    	JSONObject jsonSpace=new JSONObject(); 
		jsonSpace.accumulate("spaceNo", this.getSpaceNo());
		jsonSpace.accumulate("spaceTypeObj", this.getSpaceTypeObj().getSpaceTypeName());
		jsonSpace.accumulate("spaceTypeObjPri", this.getSpaceTypeObj().getSpaceTypeId());
		jsonSpace.accumulate("spacePhoto", this.getSpacePhoto());
		jsonSpace.accumulate("spacePrice", this.getSpacePrice());
		jsonSpace.accumulate("floorNum", this.getFloorNum());
		jsonSpace.accumulate("spaceState", this.getSpaceState());
		jsonSpace.accumulate("spaceDesc", this.getSpaceDesc());
		return jsonSpace;
    }}