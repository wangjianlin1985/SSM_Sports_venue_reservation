package com.chengxusheji.mapper;

import java.util.ArrayList;
import org.apache.ibatis.annotations.Param;
import com.chengxusheji.po.Space;

public interface SpaceMapper {
	/*添加场地信息*/
	public void addSpace(Space space) throws Exception;

	/*按照查询条件分页查询场地记录*/
	public ArrayList<Space> querySpace(@Param("where") String where,@Param("startIndex") int startIndex,@Param("pageSize") int pageSize) throws Exception;

	/*按照查询条件查询所有场地记录*/
	public ArrayList<Space> querySpaceList(@Param("where") String where) throws Exception;

	/*按照查询条件的场地记录数*/
	public int querySpaceCount(@Param("where") String where) throws Exception; 

	/*根据主键查询某条场地记录*/
	public Space getSpace(String spaceNo) throws Exception;

	/*更新场地记录*/
	public void updateSpace(Space space) throws Exception;

	/*删除场地记录*/
	public void deleteSpace(String spaceNo) throws Exception;

}
