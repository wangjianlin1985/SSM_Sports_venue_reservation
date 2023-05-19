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
import com.chengxusheji.service.SpaceTypeService;
import com.chengxusheji.po.SpaceType;

//SpaceType管理控制层
@Controller
@RequestMapping("/SpaceType")
public class SpaceTypeController extends BaseController {

    /*业务层对象*/
    @Resource SpaceTypeService spaceTypeService;

	@InitBinder("spaceType")
	public void initBinderSpaceType(WebDataBinder binder) {
		binder.setFieldDefaultPrefix("spaceType.");
	}
	/*跳转到添加SpaceType视图*/
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String add(Model model,HttpServletRequest request) throws Exception {
		model.addAttribute(new SpaceType());
		return "SpaceType_add";
	}

	/*客户端ajax方式提交添加场地类型信息*/
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public void add(@Validated SpaceType spaceType, BindingResult br,
			Model model, HttpServletRequest request,HttpServletResponse response) throws Exception {
		String message = "";
		boolean success = false;
		if (br.hasErrors()) {
			message = "输入信息不符合要求！";
			writeJsonResponse(response, success, message);
			return ;
		}
        spaceTypeService.addSpaceType(spaceType);
        message = "场地类型添加成功!";
        success = true;
        writeJsonResponse(response, success, message);
	}
	/*ajax方式按照查询条件分页查询场地类型信息*/
	@RequestMapping(value = { "/list" }, method = {RequestMethod.GET,RequestMethod.POST})
	public void list(Integer page,Integer rows, Model model, HttpServletRequest request,HttpServletResponse response) throws Exception {
		if (page==null || page == 0) page = 1;
		if(rows != 0)spaceTypeService.setRows(rows);
		List<SpaceType> spaceTypeList = spaceTypeService.querySpaceType(page);
	    /*计算总的页数和总的记录数*/
	    spaceTypeService.queryTotalPageAndRecordNumber();
	    /*获取到总的页码数目*/
	    int totalPage = spaceTypeService.getTotalPage();
	    /*当前查询条件下总记录数*/
	    int recordNumber = spaceTypeService.getRecordNumber();
        response.setContentType("text/json;charset=UTF-8");
		PrintWriter out = response.getWriter();
		//将要被返回到客户端的对象
		JSONObject jsonObj=new JSONObject();
		jsonObj.accumulate("total", recordNumber);
		JSONArray jsonArray = new JSONArray();
		for(SpaceType spaceType:spaceTypeList) {
			JSONObject jsonSpaceType = spaceType.getJsonObject();
			jsonArray.put(jsonSpaceType);
		}
		jsonObj.accumulate("rows", jsonArray);
		out.println(jsonObj.toString());
		out.flush();
		out.close();
	}

	/*ajax方式按照查询条件分页查询场地类型信息*/
	@RequestMapping(value = { "/listAll" }, method = {RequestMethod.GET,RequestMethod.POST})
	public void listAll(HttpServletResponse response) throws Exception {
		List<SpaceType> spaceTypeList = spaceTypeService.queryAllSpaceType();
        response.setContentType("text/json;charset=UTF-8"); 
		PrintWriter out = response.getWriter();
		JSONArray jsonArray = new JSONArray();
		for(SpaceType spaceType:spaceTypeList) {
			JSONObject jsonSpaceType = new JSONObject();
			jsonSpaceType.accumulate("spaceTypeId", spaceType.getSpaceTypeId());
			jsonSpaceType.accumulate("spaceTypeName", spaceType.getSpaceTypeName());
			jsonArray.put(jsonSpaceType);
		}
		out.println(jsonArray.toString());
		out.flush();
		out.close();
	}

	/*前台按照查询条件分页查询场地类型信息*/
	@RequestMapping(value = { "/frontlist" }, method = {RequestMethod.GET,RequestMethod.POST})
	public String frontlist(Integer currentPage, Model model, HttpServletRequest request) throws Exception  {
		if (currentPage==null || currentPage == 0) currentPage = 1;
		List<SpaceType> spaceTypeList = spaceTypeService.querySpaceType(currentPage);
	    /*计算总的页数和总的记录数*/
	    spaceTypeService.queryTotalPageAndRecordNumber();
	    /*获取到总的页码数目*/
	    int totalPage = spaceTypeService.getTotalPage();
	    /*当前查询条件下总记录数*/
	    int recordNumber = spaceTypeService.getRecordNumber();
	    request.setAttribute("spaceTypeList",  spaceTypeList);
	    request.setAttribute("totalPage", totalPage);
	    request.setAttribute("recordNumber", recordNumber);
	    request.setAttribute("currentPage", currentPage);
		return "SpaceType/spaceType_frontquery_result"; 
	}

     /*前台查询SpaceType信息*/
	@RequestMapping(value="/{spaceTypeId}/frontshow",method=RequestMethod.GET)
	public String frontshow(@PathVariable Integer spaceTypeId,Model model,HttpServletRequest request) throws Exception {
		/*根据主键spaceTypeId获取SpaceType对象*/
        SpaceType spaceType = spaceTypeService.getSpaceType(spaceTypeId);

        request.setAttribute("spaceType",  spaceType);
        return "SpaceType/spaceType_frontshow";
	}

	/*ajax方式显示场地类型修改jsp视图页*/
	@RequestMapping(value="/{spaceTypeId}/update",method=RequestMethod.GET)
	public void update(@PathVariable Integer spaceTypeId,Model model,HttpServletRequest request,HttpServletResponse response) throws Exception {
        /*根据主键spaceTypeId获取SpaceType对象*/
        SpaceType spaceType = spaceTypeService.getSpaceType(spaceTypeId);

        response.setContentType("text/json;charset=UTF-8");
        PrintWriter out = response.getWriter();
		//将要被返回到客户端的对象 
		JSONObject jsonSpaceType = spaceType.getJsonObject();
		out.println(jsonSpaceType.toString());
		out.flush();
		out.close();
	}

	/*ajax方式更新场地类型信息*/
	@RequestMapping(value = "/{spaceTypeId}/update", method = RequestMethod.POST)
	public void update(@Validated SpaceType spaceType, BindingResult br,
			Model model, HttpServletRequest request,HttpServletResponse response) throws Exception {
		String message = "";
    	boolean success = false;
		if (br.hasErrors()) { 
			message = "输入的信息有错误！";
			writeJsonResponse(response, success, message);
			return;
		}
		try {
			spaceTypeService.updateSpaceType(spaceType);
			message = "场地类型更新成功!";
			success = true;
			writeJsonResponse(response, success, message);
		} catch (Exception e) {
			e.printStackTrace();
			message = "场地类型更新失败!";
			writeJsonResponse(response, success, message); 
		}
	}
    /*删除场地类型信息*/
	@RequestMapping(value="/{spaceTypeId}/delete",method=RequestMethod.GET)
	public String delete(@PathVariable Integer spaceTypeId,HttpServletRequest request) throws UnsupportedEncodingException {
		  try {
			  spaceTypeService.deleteSpaceType(spaceTypeId);
	            request.setAttribute("message", "场地类型删除成功!");
	            return "message";
	        } catch (Exception e) { 
	            e.printStackTrace();
	            request.setAttribute("error", "场地类型删除失败!");
				return "error";

	        }

	}

	/*ajax方式删除多条场地类型记录*/
	@RequestMapping(value="/deletes",method=RequestMethod.POST)
	public void delete(String spaceTypeIds,HttpServletRequest request,HttpServletResponse response) throws IOException, JSONException {
		String message = "";
    	boolean success = false;
        try { 
        	int count = spaceTypeService.deleteSpaceTypes(spaceTypeIds);
        	success = true;
        	message = count + "条记录删除成功";
        	writeJsonResponse(response, success, message);
        } catch (Exception e) { 
            //e.printStackTrace();
            message = "有记录存在外键约束,删除失败";
            writeJsonResponse(response, success, message);
        }
	}

	/*按照查询条件导出场地类型信息到Excel*/
	@RequestMapping(value = { "/OutToExcel" }, method = {RequestMethod.GET,RequestMethod.POST})
	public void OutToExcel( Model model, HttpServletRequest request,HttpServletResponse response) throws Exception {
        List<SpaceType> spaceTypeList = spaceTypeService.querySpaceType();
        ExportExcelUtil ex = new ExportExcelUtil();
        String _title = "SpaceType信息记录"; 
        String[] headers = { "类型id","场地类型","价格(小时)"};
        List<String[]> dataset = new ArrayList<String[]>(); 
        for(int i=0;i<spaceTypeList.size();i++) {
        	SpaceType spaceType = spaceTypeList.get(i); 
        	dataset.add(new String[]{spaceType.getSpaceTypeId() + "",spaceType.getSpaceTypeName(),spaceType.getPrice() + ""});
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
			response.setHeader("Content-disposition","attachment; filename="+"SpaceType.xls");//filename是下载的xls的名，建议最好用英文 
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
