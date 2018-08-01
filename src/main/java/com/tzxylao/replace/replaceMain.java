package com.tzxylao.replace;

import com.alibaba.fastjson.JSON;
import com.itextpdf.text.DocumentException;

import java.io.IOException;

/**
 * Created by lll on 2018/8/1 10:54.
 */
public class replaceMain {
    public static void main(String[] args) throws IOException, DocumentException, DocumentException {
        PdfReplacer textReplacer = new PdfReplacer("E:\\picture2\\租赁合同.pdf");
        textReplacer.replaceText("$$", "  ");
        ReplaceRegion replaceRegion = textReplacer.toPdf("E:\\picture2\\租赁合同_test.pdf");
        System.out.println(JSON.toJSONString(replaceRegion));
    }
}
