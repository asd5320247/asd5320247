package com.posserver.posserverspringbootres.web.controller;

import com.alibaba.fastjson.JSONObject;
import com.github.junrar.Archive;
import com.github.junrar.exception.RarException;
import com.github.junrar.rarfile.FileHeader;
import com.posserver.posserverspringbootres.commons.ReturnManage;
import org.apache.commons.io.FileUtils;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.FilenameUtils;
import com.posserver.posserverspringbootres.web.utils.DateUtils;
import com.posserver.posserverspringbootres.pojo.PackPos;
import com.posserver.posserverspringbootres.web.service.PackPosService;
import com.posserver.posserverspringbootres.web.service.SystemService;
import com.posserver.posserverspringbootres.web.utils.InitBean;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Controller
public class PackPosController extends BaseController{
    private static Logger logger = Logger.getLogger(PackPosController.class);
    @Autowired
    SystemService systemService;

    @Autowired
    PackPosService packPosService;

    @Autowired
    ReturnManage returnManage;

    //packpos查询全部 接口，测试成功
    @RequestMapping("manage/packlist.xj")
    @ResponseBody
    public String list(){
        JSONObject data = new JSONObject();

//        if (!isLogin(request)) {
//            mav.setViewName("redirect:/login");
//            return mav;
//        }

        data.put("list",packPosService.selectAllPackPos());
        data.put("groupId",systemService.getDefaultGroupId());
        data.put("groupName",systemService.getDefaultGroupName());
        data.put("shopList",systemService.selectAllShop());
        return returnManage.Success(data);
    }


    //post接口，测试未成功，需要提出的问题为packFile是否要传文件进去
    @RequestMapping("manage/post.xj")
    @ResponseBody
    public String post(@RequestBody JSONObject jsonObject,@RequestParam("packFile")MultipartFile packFile)
            throws IOException, RarException {
        JSONObject data = new JSONObject();
        PackPos packPos = new PackPos(jsonObject.get("shopId").toString(),jsonObject.get("fileName")
                    .toString(),jsonObject.get("version").toString(),jsonObject.get("hash").toString(),
                jsonObject.getInteger("type"),jsonObject.get("createTime").toString());
        if (packPos.getGroupId() == null || packPos.getGroupId().isEmpty()) {
            packPos.setGroupId(systemService.getDefaultGroupId());
        }
        String filePath = InitBean.getDataPath() + "/" + packPos.getGroupId() + "/" + packPos.getShopId();
        String packPosFileName = null;
        logger.debug("filePath:" + filePath);
        logger.debug("getContentType:" + packFile.getContentType());
        if (packFile.getOriginalFilename().toLowerCase().endsWith(".rar")) {
            File tempFile = File.createTempFile("packpos", FilenameUtils.getExtension(packFile.getOriginalFilename()));
            tempFile.deleteOnExit();
            FileUtils.writeByteArrayToFile(tempFile, packFile.getBytes());
            Archive archive = new Archive(tempFile);
            List<FileHeader> headers = archive.getFileHeaders();
            if (headers.size() < 5) {
                for (FileHeader fileHeader : headers) {
                    if (Pattern.matches("packpos.*\\.exe", fileHeader.getFileNameString().toLowerCase())) {
                        logger.info("File Found: " + fileHeader.getFileNameString());
                        packPosFileName = filePath + "/" + fileHeader.getFileNameString();
                        FileOutputStream unOut = new FileOutputStream(new File(packPosFileName));
                        archive.extractFile(fileHeader, unOut);
                        unOut.flush();
                        unOut.close();
                        break;
                    }
                }
            }
            archive.close();
        } else if (Pattern.matches("packpos.*\\.exe", packFile.getOriginalFilename().toLowerCase())) {
            packPosFileName = filePath + "/" + packFile.getOriginalFilename();
            FileUtils.writeByteArrayToFile(new File(packPosFileName), packFile.getBytes());
        }

        if (packPosFileName == null) {
            return returnManage.Defult(data);
        }

        logger.info("packPosFileName: " + packPosFileName);
        Pattern pattern = Pattern.compile("PackPos(\\d{4})(\\d{2})(\\d{2})V(\\d{2})(.*)\\.EXE", Pattern.CASE_INSENSITIVE);

        Matcher m = pattern.matcher(packPosFileName); // 操作的字符串
        while (m.find()) {
            String version = Integer.parseInt(m.group(1)) + "." + Integer.parseInt(m.group(2)) + "." + Integer.parseInt(m.group(3)) + "." + Integer.parseInt(m.group(4));
            logger.info("version:" + version);

            packPos.setFileName(packPosFileName);
            packPos.setVersion(version);
            packPos.setHash(DigestUtils.md5Hex(new FileInputStream(packPosFileName)));
            packPos.setCreateTime(DateUtils.getNowTime());
            packPosService.savePackPos(packPos);
            data.put("fileName",packPosFileName);
            data.put("version",version);
            data.put("hash",packPosFileName);
            data.put("createTime", DateUtils.getNowTime());
        }

        return returnManage.Success(data);
    }

    //pack删除接口，测试成功
    @RequestMapping("manage/packdel.xj")
    @ResponseBody
    public String del(@RequestBody JSONObject jsonObject){
        JSONObject data = new JSONObject();
//        if (!isLogin(request)) {
//            mav.setViewName("redirect:/login");
//            return mav;
//        }
        PackPos packPos = new PackPos(jsonObject.get("shopId").toString(),jsonObject.get("fileName").toString(),
                jsonObject.get("version").toString(),jsonObject.get("hash").toString(),jsonObject.getInteger("type"),
                jsonObject.get("createTime").toString());
        System.out.println(packPos);
        packPosService.deletePackPos(packPos.getShopId(), packPos.getHash());
        data.put("packList",packPosService.selectAllPackPos());
        return returnManage.Success(data);
    }

}
