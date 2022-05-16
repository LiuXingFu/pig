package com.pig4cloud.pig.casee.utils;

import com.pig4cloud.pig.casee.vo.paifu.ProjectPaifuExcelExportVO;
import com.pig4cloud.pig.casee.vo.paifu.ProjectPaifuExportVO;
import lombok.extern.slf4j.Slf4j;
import freemarker.template.Template;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.URLEncoder;
import java.util.Objects;

/**
 * 下载ftl公告
 */
@Component
@Slf4j
public class DownloadUtils {

    @Autowired
    private FreeMarkerConfigurer freeMarkerConfigurer;

    private static final String AFFICHE = "/paifuDownload/";

    //拍卖移送表
    private static final String PAIFU_LEDGER = "paifuLedger";


    /**
     * 下载拍卖移送表
     * @param response
     * @param param
     */
    public void downloadPaifuLedger(HttpServletResponse response, ProjectPaifuExcelExportVO param) {
        OutputStreamWriter osw = null;
        try {
            osw = getOutputStreamWriter(response, "拍辅台账表.xls");
            getTemplate(PAIFU_LEDGER).process(param, osw);
        } catch (Exception e) {
            //e.printStackTrace();
        }
        try {
            if (Objects.nonNull(osw))
                osw.close();
        } catch (IOException e) {
			log.error("下载失败:{}",e.getMessage());
        }
    }

    /**
     * 获取转换
     * @param response
     * @param fileName 下载文件名(需要带后缀)
     * @return
     * @throws IOException
     */
    private OutputStreamWriter getOutputStreamWriter(HttpServletResponse response, String fileName) throws IOException {
        response.setHeader("Content-disposition",
                "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));
        response.setContentType("application/msword");// 定义输出类型
        return new OutputStreamWriter(response.getOutputStream());
    }

    /**
     * 获取模板
     * @param templateName
     * @return
     * @throws IOException
     */
    private Template getTemplate(String templateName) throws IOException {
        return freeMarkerConfigurer.getConfiguration().getTemplate(AFFICHE+templateName+".ftl");
    }
}
