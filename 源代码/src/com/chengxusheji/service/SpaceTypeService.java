package com.chengxusheji.service;

import java.util.ArrayList;
import javax.annotation.Resource; 
import org.springframework.stereotype.Service;
import com.chengxusheji.po.SpaceType;

import com.chengxusheji.mapper.SpaceTypeMapper;
@Service
public class SpaceTypeService {

	@Resource SpaceTypeMapper spaceTypeMapper;
    /*每页显示记录数目*/
    private int rows = 10;;
    public int getRows() {
		return rows;
	}
	public void setRows(int rows) {
		this.rows = rows;
	}

    /*保存查询后总的页数*/
    private int totalPage;
    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }
    public int getTotalPage() {
        return totalPage;
    }

    /*保存查询到的总记录数*/
    private int recordNumber;
    public void setRecordNumber(int recordNumber) {
        this.recordNumber = recordNumber;
    }
    public int getRecordNumber() {
        return recordNumber;
    }

    /*添加场地类型记录*/
    public void addSpaceType(SpaceType spaceType) throws Exception {
    	spaceTypeMapper.addSpaceType(spaceType);
    }

    /*按照查询条件分页查询场地类型记录*/
    public ArrayList<SpaceType> querySpaceType(int currentPage) throws Exception { 
     	String where = "where 1=1";
    	int startIndex = (currentPage-1) * this.rows;
    	return spaceTypeMapper.querySpaceType(where, startIndex, this.rows);
    }

    /*按照查询条件查询所有记录*/
    public ArrayList<SpaceType> querySpaceType() throws Exception  { 
     	String where = "where 1=1";
    	return spaceTypeMapper.querySpaceTypeList(where);
    }

    /*查询所有场地类型记录*/
    public ArrayList<SpaceType> queryAllSpaceType()  throws Exception {
        return spaceTypeMapper.querySpaceTypeList("where 1=1");
    }

    /*当前查询条件下计算总的页数和记录数*/
    public void queryTotalPageAndRecordNumber() throws Exception {
     	String where = "where 1=1";
        recordNumber = spaceTypeMapper.querySpaceTypeCount(where);
        int mod = recordNumber % this.rows;
        totalPage = recordNumber / this.rows;
        if(mod != 0) totalPage++;
    }

    /*根据主键获取场地类型记录*/
    public SpaceType getSpaceType(int spaceTypeId) throws Exception  {
        SpaceType spaceType = spaceTypeMapper.getSpaceType(spaceTypeId);
        return spaceType;
    }

    /*更新场地类型记录*/
    public void updateSpaceType(SpaceType spaceType) throws Exception {
        spaceTypeMapper.updateSpaceType(spaceType);
    }

    /*删除一条场地类型记录*/
    public void deleteSpaceType (int spaceTypeId) throws Exception {
        spaceTypeMapper.deleteSpaceType(spaceTypeId);
    }

    /*删除多条场地类型信息*/
    public int deleteSpaceTypes (String spaceTypeIds) throws Exception {
    	String _spaceTypeIds[] = spaceTypeIds.split(",");
    	for(String _spaceTypeId: _spaceTypeIds) {
    		spaceTypeMapper.deleteSpaceType(Integer.parseInt(_spaceTypeId));
    	}
    	return _spaceTypeIds.length;
    }
}
