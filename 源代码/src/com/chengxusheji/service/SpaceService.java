package com.chengxusheji.service;

import java.util.ArrayList;
import javax.annotation.Resource; 
import org.springframework.stereotype.Service;
import com.chengxusheji.po.SpaceType;
import com.chengxusheji.po.Space;

import com.chengxusheji.mapper.SpaceMapper;
@Service
public class SpaceService {

	@Resource SpaceMapper spaceMapper;
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

    /*添加场地记录*/
    public void addSpace(Space space) throws Exception {
    	spaceMapper.addSpace(space);
    }

    /*按照查询条件分页查询场地记录*/
    public ArrayList<Space> querySpace(String spaceNo,SpaceType spaceTypeObj,String spaceState,int currentPage) throws Exception { 
     	String where = "where 1=1";
    	if(!spaceNo.equals("")) where = where + " and t_space.spaceNo like '%" + spaceNo + "%'";
    	if(null != spaceTypeObj && spaceTypeObj.getSpaceTypeId()!= null && spaceTypeObj.getSpaceTypeId()!= 0)  where += " and t_space.spaceTypeObj=" + spaceTypeObj.getSpaceTypeId();
    	if(!spaceState.equals("")) where = where + " and t_space.spaceState like '%" + spaceState + "%'";
    	int startIndex = (currentPage-1) * this.rows;
    	return spaceMapper.querySpace(where, startIndex, this.rows);
    }

    /*按照查询条件查询所有记录*/
    public ArrayList<Space> querySpace(String spaceNo,SpaceType spaceTypeObj,String spaceState) throws Exception  { 
     	String where = "where 1=1";
    	if(!spaceNo.equals("")) where = where + " and t_space.spaceNo like '%" + spaceNo + "%'";
    	if(null != spaceTypeObj && spaceTypeObj.getSpaceTypeId()!= null && spaceTypeObj.getSpaceTypeId()!= 0)  where += " and t_space.spaceTypeObj=" + spaceTypeObj.getSpaceTypeId();
    	if(!spaceState.equals("")) where = where + " and t_space.spaceState like '%" + spaceState + "%'";
    	return spaceMapper.querySpaceList(where);
    }

    /*查询所有场地记录*/
    public ArrayList<Space> queryAllSpace()  throws Exception {
        return spaceMapper.querySpaceList("where 1=1");
    }

    /*当前查询条件下计算总的页数和记录数*/
    public void queryTotalPageAndRecordNumber(String spaceNo,SpaceType spaceTypeObj,String spaceState) throws Exception {
     	String where = "where 1=1";
    	if(!spaceNo.equals("")) where = where + " and t_space.spaceNo like '%" + spaceNo + "%'";
    	if(null != spaceTypeObj && spaceTypeObj.getSpaceTypeId()!= null && spaceTypeObj.getSpaceTypeId()!= 0)  where += " and t_space.spaceTypeObj=" + spaceTypeObj.getSpaceTypeId();
    	if(!spaceState.equals("")) where = where + " and t_space.spaceState like '%" + spaceState + "%'";
        recordNumber = spaceMapper.querySpaceCount(where);
        int mod = recordNumber % this.rows;
        totalPage = recordNumber / this.rows;
        if(mod != 0) totalPage++;
    }

    /*根据主键获取场地记录*/
    public Space getSpace(String spaceNo) throws Exception  {
        Space space = spaceMapper.getSpace(spaceNo);
        return space;
    }

    /*更新场地记录*/
    public void updateSpace(Space space) throws Exception {
        spaceMapper.updateSpace(space);
    }

    /*删除一条场地记录*/
    public void deleteSpace (String spaceNo) throws Exception {
        spaceMapper.deleteSpace(spaceNo);
    }

    /*删除多条场地信息*/
    public int deleteSpaces (String spaceNos) throws Exception {
    	String _spaceNos[] = spaceNos.split(",");
    	for(String _spaceNo: _spaceNos) {
    		spaceMapper.deleteSpace(_spaceNo);
    	}
    	return _spaceNos.length;
    }
}
