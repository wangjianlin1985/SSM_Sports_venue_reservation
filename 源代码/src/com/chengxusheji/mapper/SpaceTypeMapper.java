package com.chengxusheji.mapper;

import java.util.ArrayList;
import org.apache.ibatis.annotations.Param;
import com.chengxusheji.po.SpaceType;

public interface SpaceTypeMapper {
	/*添加场地类型信息*/
	public void addSpaceType(SpaceType spaceType) throws Exception;

	/*按照查询条件分页查询场地类型记录*/
	public ArrayList<SpaceType> querySpaceType(@Param("where") String where,@Param("startIndex") int startIndex,@Param("pageSize") int pageSize) throws Exception;

	/*按照查询条件查询所有场地类型记录*/
	public ArrayList<SpaceType> querySpaceTypeList(@Param("where") String where) throws Exception;

	/*按照查询条件的场地类型记录数*/
	public int querySpaceTypeCount(@Param("where") String where) throws Exception; 

	/*根据主键查询某条场地类型记录*/
	public SpaceType getSpaceType(int spaceTypeId) throws Exception;

	/*更新场地类型记录*/
	public void updateSpaceType(SpaceType spaceType) throws Exception;

	/*删除场地类型记录*/
	public void deleteSpaceType(int spaceTypeId) throws Exception;

}
