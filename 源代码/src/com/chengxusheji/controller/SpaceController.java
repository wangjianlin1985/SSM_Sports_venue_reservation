package com.chengxusheji.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.chengxusheji.utils.ExportExcelUtil;
import com.chengxusheji.utils.UserException;
import com.chengxusheji.service.SpaceService;
import com.chengxusheji.po.Space;
import com.chengxusheji.service.SpaceTypeService;
import com.chengxusheji.po.SpaceType;

//Space管理控制层
@Controller
@RequestMapping("/Space")
public class SpaceController extends BaseController {

    /*业务层对象*/
    @Resource SpaceService spaceService;

    @Resource SpaceTypeService spaceTypeService;
	@InitBinder("spaceTypeObj")
	public void initBinderspaceTypeObj(WebDataBinder binder) {
		binder.setFieldDefaultPrefix("spaceTypeObj.");
	}
	@InitBinder("space")
	public void initBinderSpace(WebDataBinder binder) {
		binder.setFieldDefaultPrefix("space.");
	}
	/*跳转到添加Space视图*/
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String add(Model model,HttpServletRequest request) throws Exception {
		model.addAttribute(new Space());
		/*查询所有的SpaceType信息*/
		List<SpaceType> spaceTypeList = spaceTypeService.queryAllSpaceType();
		request.setAttribute("spaceTypeList", spaceTypeList);
		return "Space_add";
	}

	/*客户端ajax方式提交添加场地信息*/
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public void add(@Validated Space space, BindingResult br,
			Model model, HttpServletRequest request,HttpServletResponse response) throws Exception {
		String message = "";
		boolean success = false;
		if (br.hasErrors()) {
			message = "输入信息不符合要求！";
			writeJsonResponse(response, success, message);
			return ;
		}
		if(spaceService.getSpace(space.getSpaceNo()) != null) {
			message = "场地号已经存在！";
			writeJsonResponse(response, success, message);
			return ;
		}
		try {
			space.setSpacePhoto(this.handlePhotoUpload(request, "spacePhotoFile"));
		} catch(UserException ex) {
			message = "图片格式不正确！";
			writeJsonResponse(response, success, message);
			return ;
		}
        spaceService.addSpace(space);
        message = "场地添加成功!";
        success = true;
        writeJsonResponse(response, success, message);
	}
	/*ajax方式按照查询条件分页查询场地信息*/
	@RequestMapping(value = { "/list" }, method = {RequestMethod.GET,RequestMethod.POST})
	public void list(String spaceNo,@ModelAttribute("spaceTypeObj") SpaceType spaceTypeObj,String spaceState,Integer page,Integer rows, Model model, HttpServletRequest request,HttpServletResponse response) throws Exception {
		if (page==null || page == 0) page = 1;
		if (spaceNo == null) spaceNo = "";
		if (spaceState == null) spaceState = "";
		if(rows != 0)spaceService.setRows(rows);
		List<Space> spaceList = spaceService.querySpace(spaceNo, spaceTypeObj, spaceState, page);
	    /*计算总的页数和总的记录数*/
	    spaceService.queryTotalPageAndRecordNumber(spaceNo, spaceTypeObj, spaceState);
	    /*获取到总的页码数目*/
	    int totalPage = spaceService.getTotalPage();
	    /*当前查询条件下总记录数*/
	    int recordNumber = spaceService.getRecordNumber();
        response.setContentType("text/json;charset=UTF-8");
		PrintWriter out = response.getWriter();
		//将要被返回到客户端的对象
		JSONObject jsonObj=new JSONObject();
		jsonObj.accumulate("total", recordNumber);
		JSONArray jsonArray = new JSONArray();
		for(Space space:spaceList) {
			JSONObject jsonSpace = space.getJsonObject();
			jsonArray.put(jsonSpace);
		}
		jsonObj.accumulate("rows", jsonArray);
		out.println(jsonObj.toString());
		out.flush();
		out.close();
	}

	/*ajax方式按照查询条件分页查询场地信息*/
	@RequestMapping(value = { "/listAll" }, method = {RequestMethod.GET,RequestMethod.POST})
	public void listAll(HttpServletResponse response) throws Exception {
		List<Space> spaceList = spaceService.queryAllSpace();
        response.setContentType("text/json;charset=UTF-8"); 
		PrintWriter out = response.getWriter();
		JSONArray jsonArray = new JSONArray();
		for(Space space:spaceList) {
			JSONObject jsonSpace = new JSONObject();
			jsonSpace.accumulate("spaceNo", space.getSpaceNo());
			jsonArray.put(jsonSpace);
		}
		out.println(jsonArray.toString());
		out.flush();
		out.close();
	}

	/*前台按照查询条件分页查询场地信息*/
	@RequestMapping(value = { "/frontlist" }, method = {RequestMethod.GET,RequestMethod.POST})
	public String frontlist(String spaceNo,@ModelAttribute("spaceTypeObj") SpaceType spaceTypeObj,String spaceState,Integer currentPage, Model model, HttpServletRequest request) throws Exception  {
		if (currentPage==null || currentPage == 0) currentPage = 1;
		if (spaceNo == null) spaceNo = "";
		if (spaceState == null) spaceState = "";
		List<Space> spaceList = spaceService.querySpace(spaceNo, spaceTypeObj, spaceState, currentPage);
	    /*计算总的页数和总的记录数*/
	    spaceService.queryTotalPageAndRecordNumber(spaceNo, spaceTypeObj, spaceState);
	    /*获取到总的页码数目*/
	    int totalPage = spaceService.getTotalPage();
	    /*当前查询条件下总记录数*/
	    int recordNumber = spaceService.getRecordNumber();
	    request.setAttribute("spaceList",  spaceList);
	    request.setAttribute("totalPage", totalPage);
	    request.setAttribute("recordNumber", recordNumber);
	    request.setAttribute("currentPage", currentPage);
	    request.setAttribute("spaceNo", spaceNo);
	    request.setAttribute("spaceTypeObj", spaceTypeObj);
	    request.setAttribute("spaceState", spaceState);
	    List<SpaceType> spaceTypeList = spaceTypeService.queryAllSpaceType();
	    request.setAttribute("spaceTypeList", spaceTypeList);
		return "Space/space_frontquery_result"; 
	}

     /*前台查询Space信息*/
	@RequestMapping(value="/{spaceNo}/frontshow",method=RequestMethod.GET)
	public String frontshow(@PathVariable String spaceNo,Model model,HttpServletRequest request) throws Exception {
		/*根据主键spaceNo获取Space对象*/
        Space space = spaceService.getSpace(spaceNo);

        List<SpaceType> spaceTypeList = spaceTypeService.queryAllSpaceType();
        request.setAttribute("spaceTypeList", spaceTypeList);
        request.setAttribute("space",  space);
        return "Space/space_frontshow";
	}

	/*ajax方式显示场地修改jsp视图页*/
	@RequestMapping(value="/{spaceNo}/update",method=RequestMethod.GET)
	public void update(@PathVariable String spaceNo,Model model,HttpServletRequest request,HttpServletResponse response) throws Exception {
        /*根据主键spaceNo获取Space对象*/
        Space space = spaceService.getSpace(spaceNo);

        response.setContentType("text/json;charset=UTF-8");
        PrintWriter out = response.getWriter();
		//将要被返回到客户端的对象 
		JSONObject jsonSpace = space.getJsonObject();
		out.println(jsonSpace.toString());
		out.flush();
		out.close();
	}

	/*ajax方式更新场地信息*/
	@RequestMapping(value = "/{spaceNo}/update", method = RequestMethod.POST)
	public void update(@Validated Space space, BindingResult br,
			Model model, HttpServletRequest request,HttpServletResponse response) throws Exception {
		String message = "";
    	boolean success = false;
		if (br.hasErrors()) { 
			message = "输入的信息有错误！";
			writeJsonResponse(response, success, message);
			return;
		}
		String spacePhotoFileName = this.handlePhotoUpload(request, "spacePhotoFile");
		if(!spacePhotoFileName.equals("upload/NoImage.jpg"))space.setSpacePhoto(spacePhotoFileName); 


		try {
			spaceService.updateSpace(space);
			message = "场地更新成功!";
			success = true;
			writeJsonResponse(response, success, message);
		} catch (Exception e) {
			e.printStackTrace();
			message = "场地更新失败!";
			writeJsonResponse(response, success, message); 
		}
	}
    /*删除场地信息*/
	@RequestMapping(value="/{spaceNo}/delete",method=RequestMethod.GET)
	public String delete(@PathVariable String spaceNo,HttpServletRequest request) throws UnsupportedEncodingException {
		  try {
			  spaceService.deleteSpace(spaceNo);
	            request.setAttribute("message", "场地删除成功!");
	            return "message";
	        } catch (Exception e) { 
	            e.printStackTrace();
	            request.setAttribute("error", "场地删除失败!");
				return "error";

	        }

	}

	/*ajax方式删除多条场地记录*/
	@RequestMapping(value="/deletes",method=RequestMethod.POST)
	public void delete(String spaceNos,HttpServletRequest request,HttpServletResponse response) throws IOException, JSONException {
		String message = "";
    	boolean success = false;
        try { 
        	int count = spaceService.deleteSpaces(spaceNos);
        	success = true;
        	message = count + "条记录删除成功";
        	writeJsonResponse(response, success, message);
        } catch (Exception e) { 
            //e.printStackTrace();
            message = "有记录存在外键约束,删除失败";
            writeJsonResponse(response, success, message);
        }
	}

	/*按照查询条件导出场地信息到Excel*/
	@RequestMapping(value = { "/OutToExcel" }, method = {RequestMethod.GET,RequestMethod.POST})
	public void OutToExcel(String spaceNo,@ModelAttribute("spaceTypeObj") SpaceType spaceTypeObj,String spaceState, Model model, HttpServletRequest request,HttpServletResponse response) throws Exception {
        if(spaceNo == null) spaceNo = "";
        if(spaceState == null) spaceState = "";
        List<Space> spaceList = spaceService.querySpace(spaceNo,spaceTypeObj,spaceState);
        ExportExcelUtil ex = new ExportExcelUtil();
        String _title = "Space信息记录"; 
        String[] headers = { "场地号","场地类型","场地图片","价格(小时)","楼层","占用状态"};
        List<String[]> dataset = new ArrayList<String[]>(); 
        for(int i=0;i<spaceList.size();i++) {
        	Space space = spaceList.get(i); 
        	dataset.add(new String[]{space.getSpaceNo(),space.getSpaceTypeObj().getSpaceTypeName(),space.getSpacePhoto(),space.getSpacePrice() + "",space.getFloorNum(),space.getSpaceState()});
        }
        /*
        OutputStream out = null;
		try {
			out = new FileOutputStream("C://output.xls");
			ex.exportExcel(title,headers, dataset, out);
		    out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		*/
		OutputStream out = null;//创建一个输出流对象 
		try { 
			out = response.getOutputStream();//
			response.setHeader("Content-disposition","attachment; filename="+"Space.xls");//filename是下载的xls的名，建议最好用英文 
			response.setContentType("application/msexcel;charset=UTF-8");//设置类型 
			response.setHeader("Pragma","No-cache");//设置头 
			response.setHeader("Cache-Control","no-cache");//设置头 
			response.setDateHeader("Expires", 0);//设置日期头  
			String rootPath = request.getSession().getServletContext().getRealPath("/");
			ex.exportExcel(rootPath,_title,headers, dataset, out);
			out.flush();
		} catch (IOException e) { 
			e.printStackTrace(); 
		}finally{
			try{
				if(out!=null){ 
					out.close(); 
				}
			}catch(IOException e){ 
				e.printStackTrace(); 
			} 
		}
    }
}
