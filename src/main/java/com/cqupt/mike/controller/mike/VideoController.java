package com.cqupt.mike.controller.mike;


import com.cqupt.mike.common.Constants;
import com.cqupt.mike.controller.vo.MikeStudentVo;

import com.cqupt.mike.entity.Course;
import com.cqupt.mike.service.CourseService;
import com.cqupt.mike.service.OrderService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


@Controller
public class VideoController {
    @Resource
    private CourseService courseService;
    @Resource
    private OrderService orderService;



    @RequestMapping(value = "/videoPlayByIdAndAdmin",method = RequestMethod.GET)
    public String videoPlayByIdAndAdmin(@RequestParam(value = "id" ,required = false) String id, ModelMap model, HttpServletRequest httpServletRequest, HttpSession httpSession) {
        MikeStudentVo user = (MikeStudentVo) httpSession.getAttribute(Constants.MIKE_STUDENT_SESSION_KEY);
        boolean checkResult = orderService.checkoutPaySuccess(user.getStId(),Long.valueOf(id));
        //若返回信息为登陆成功，则登陆成功
        if (checkResult) {
            Course course = courseService.getCourseById(Long.valueOf(id));
            model.addAttribute("title",course.getCourseIntro());
            model.addAttribute("path", course.getCourseVideo());
            return "mike/video";
        }

        //注册失败
        return "mike/error";
    }

    }



//    private final NonStaticResourceHttpRequestHandler nonStaticResourceHttpRequestHandler;
//
//    public VideoController(NonStaticResourceHttpRequestHandler nonStaticResourceHttpRequestHandler) {
//        this.nonStaticResourceHttpRequestHandler = nonStaticResourceHttpRequestHandler;
//    }
//
//    @GetMapping("/video/{id}")
//    public void videoPreview(@PathVariable("id") String id, HttpServletRequest request, HttpServletResponse response) throws Exception {
//        //假如我把视频1.mp4放在了static下的video文件夹里面
//        //sourcePath 是获取resources文件夹的绝对地址
//        //realPath 即是视频所在的磁盘地址
//        String sourcePath = ClassUtils.getDefaultClassLoader().getResource("").getPath().substring(1);
//        String realPath = sourcePath + "static/video/" + id + ".mp4";
//        System.out.println(realPath);
//        Path filePath = Paths.get(realPath);
//        if (Files.exists(filePath)) {
//            String mimeType = Files.probeContentType(filePath);
//            if (!StringUtils.isEmpty(mimeType)) {
//                response.setContentType(mimeType);
//            }
//            request.setAttribute(NonStaticResourceHttpRequestHandler.ATTR_FILE, filePath);
//            nonStaticResourceHttpRequestHandler.handleRequest(request, response);
//        } else {
//            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
//            response.setCharacterEncoding("UTF-8");
//        }






