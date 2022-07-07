package com.posserver.posserverspringbootres.web.controller;

import com.posserver.posserverspringbootres.pojo.BaseDb;
import com.posserver.posserverspringbootres.pojo.PackPos;
import com.posserver.posserverspringbootres.pojo.Syj;
import com.posserver.posserverspringbootres.web.service.BaseDbService;
import com.posserver.posserverspringbootres.web.service.PackPosService;
import com.posserver.posserverspringbootres.web.service.SystemService;
import com.posserver.posserverspringbootres.web.utils.DateUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

@Controller
public class ServiceController extends BaseController {

    private static Logger logger = Logger.getLogger(ServiceController.class);

    @Autowired
    SystemService systemService;

    @Autowired
    PackPosService packageService;

    @Autowired
    BaseDbService baseDbService;

//request代表请求 respone代表响应  要向客户机输出数据，只需要找response对象就行了
    private static void responseFile(HttpServletResponse response, String fileName) throws IOException {
        File file = new File(fileName);

        response.setContentType("application/octet-stream");
        response.setHeader("Content-disposition", "attachment; filename=" + FilenameUtils.getName(fileName));
        response.setHeader("Content-Length", String.valueOf(file.length()));

        BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));
        BufferedOutputStream bos = new BufferedOutputStream(response.getOutputStream());
        byte[] buff = new byte[2048];
        int bytesRead;
        while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
            bos.write(buff, 0, bytesRead);
        }
        bis.close();
        bos.close();
    }

    @RequestMapping({"/{shopId}/update.ashx","/{shopId}/update"})
    //pathVariable注解 意思是url占位符
    public void update(@PathVariable String shopId, HttpServletRequest request, HttpServletResponse response) {
        String version = request.getParameter("version");
        String syjId = request.getParameter("syjh");

        //根据syjh输入的收银机id 查找收银机，如果查到了收银机，则修改version 和updatetime并保存，如果没有查到则新增收银机记录。
        if (!StringUtils.isEmpty(syjId)){
            // 更新收银机状态
            Syj syj = systemService.selectSyj(shopId, syjId);
            if (syj == null) {
                syj = new Syj(shopId, syjId);
            }
            syj.setVersion(version);
            syj.setUpdateTime(DateUtils.getNowTime());
            systemService.saveSyj(syj);
        }

        // 检查更新包
        PackPos pkg = packageService.selectPackageBySyj(shopId, syjId);

        if (pkg != null && !pkg.getVersion().equals(version)) {
            try {
                responseFile(response, pkg.getFileName());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @RequestMapping({"/{shopId}/base.ashx","/{shopId}/base"})
    public void base(@PathVariable String shopId, HttpServletRequest request, HttpServletResponse response) {
        String dbVersion = request.getParameter("version");
        String syjId = request.getParameter("syjh");
        String fileType = request.getParameter("type");

        if (!StringUtils.isEmpty(syjId)) {
            // 更新收银机状态
            Syj syj = systemService.selectSyj(shopId, syjId);
            if (syj == null) {
                syj = new Syj(shopId, syjId);
            }
            syj.setDbVersion(dbVersion);
            syj.setUpdateTime(DateUtils.getNowTime());
            systemService.saveSyj(syj);
        }

        // 不支持旧版本格式的生成
        if (StringUtils.isEmpty(fileType) || fileType.equals("db")) {
            // 检查脱网数据包
            BaseDb baseDb = baseDbService.selectLastBaseDb(shopId);
            if (baseDb != null && !baseDb.getVersion().equals(dbVersion)) {
                logger.debug("baseDb.getVersion(): " + baseDb.getVersion());
                logger.debug("syj.getDbVersion(): " + dbVersion);

                try {
                    responseFile(response, baseDb.getFileName());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
