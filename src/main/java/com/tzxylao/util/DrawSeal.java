package com.tzxylao.util;

import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Calendar;
import java.util.UUID;

import javax.imageio.ImageIO;


import org.apache.commons.lang.StringUtils;

/**
 * 勾画圆形公司章
 * 
 * @author ldd
 */
public class DrawSeal {
    //生成文件路径
    public static final String FILE_PATH = "E:\\picture2";
    
    private final static int WIDTH = 160;
    
    private final static int HEIGHT = 160;
    
    /**
     * 画圆形章
     * @param name1 章的名字
     * @param name2 公司的名字
     * @param numCode 公司的数字编码
     * @return
     */
    public static String drawCircularChapter(String name1, String name2, String numCode){
        OutputStream out = null;
        InputStream in = null;
        byte[] data = null;
        //创建缓存
        BufferedImage bufImg = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
        //获得画布
        Graphics2D gs = bufImg.createGraphics();
        //设置颜色
        gs.setColor(Color.WHITE);
        //填充区域
        gs.fillRect(0, 0, WIDTH, HEIGHT);
        //设置画笔颜色
        gs.setColor(new Color(204,41,41));
        //画五角星
        drawFiveStar(gs, WIDTH/2, HEIGHT/2, HEIGHT/6-3);
        //设置画笔宽度
        gs.setStroke(new BasicStroke(3));
        //画圆形边框
        gs.drawOval(2, 2, WIDTH-4, HEIGHT-4);
        //设置字体
        gs.setFont(new Font("宋体", Font.BOLD, 21));

        gs.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        //画数据(合同专用章)
        drawCenterMessage(gs, name1, WIDTH/2+2, HEIGHT * 4 / 5);
        if (StringUtils.isNotEmpty(name2) && name2.length()>11) {
            int len = name2.length();
            int fontSize = 18;
            switch (len) {
            case 12:
                fontSize = 20;
                break;
            case 13:
                fontSize = 19;
                break;
            case 14:
                fontSize = 19;
                break;
            case 15:
                fontSize = 18;
                break;
            case 16:
                fontSize = 17;
                break;
            case 17:
                fontSize = 16;
                break;
            case 18:
                fontSize = 15;
                break;
            case 19:
                fontSize = 14;
                break;
            case 20:
                fontSize = 14;
                break;
            case 21:
                fontSize = 12;
                break;
            case 22:
                fontSize = 12;
                break;
            case 23:
                fontSize = 10;
                break;
            case 24:
                fontSize = 10;
                break;
            case 25:
                fontSize = 9;
                break;
            default :
                fontSize = 18;
                break;
            }
            //设置字体
            gs.setFont(new Font("宋体", Font.PLAIN, fontSize));
        }
        //画上方的环形字
        drawUpperMessage(gs, name2, WIDTH/2, HEIGHT/2, WIDTH/2-10);
        //设置字体
        gs.setFont(new Font("宋体", Font.BOLD, 9));
        //画下方的环形数字
        drawBelowNumber(gs, numCode, WIDTH/2, HEIGHT/2, HEIGHT/2);
        //释放此图形的上下文以及它使用的所有系统资源
        gs.dispose();
        //输出图片
        try {
            StringBuffer fileDir = new StringBuffer();
            fileDir.append(Calendar.getInstance().get(Calendar.YEAR)).append(File.separator).append(Calendar.getInstance().get(Calendar.MONTH)+1).append(File.separator).append(Calendar.getInstance().get(Calendar.DAY_OF_MONTH)).append(File.separator);
            File ff = new File(FILE_PATH);
            if (!ff.exists()) {
                ff.mkdirs();
            }
            String imgFile = FILE_PATH+"/" + UUID.randomUUID() + ".png";
            out = new FileOutputStream(imgFile);
            ImageIO.write(bufImg, "png", out);
            
            bufImg.flush();
            out.flush();
            
            /*in = new FileInputStream(imgFile);
            data = new byte[in.available()];
            in.read(data);*/
            
            //对字节数组Base64编码
            /*sun.misc.BASE64Encoder encoder = new sun.misc.BASE64Encoder();
            //返回Base64编码过的字节数组字符串
            String encodeStr = encoder.encode(data);
            //将图片文件删除
            File file = new File(imgFile);
            if (file.isFile()) {
                file.delete();
            }*/
            return imgFile;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (null != out) {
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (null != in) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }
    
    public static void drawCenterMessage(Graphics2D gs, String message, double CENTERX, double CENTERY){
        if (message != null) {
            // 根据输入字符串得到字符数组
            String[] messages2 = message.split("", 0);
            String[] messages = new String[messages2.length];
            System.arraycopy(messages2, 0, messages, 0, messages2.length);
            // 输入的字数
            int ilength = messages.length;
            Font f = gs.getFont();
            FontRenderContext context = gs.getFontRenderContext();
            Rectangle2D bounds = f.getStringBounds(message, context);
            // 字符宽度＝字符串长度/字符数
            double char_interval = (bounds.getWidth() / ilength) - 1;
            
            int first = 0, second = 0;
            boolean odd = false;
            if (ilength % 2 == 1) {
                first = (ilength - 1) / 2;
                odd = true;
            } else {
                first = (ilength) / 2 - 1;
                second = (ilength) / 2;
                odd = false;
            }
            if (odd) {
                gs.drawString(messages[first],
                        (float)(CENTERX - char_interval / 2 - char_interval / 10), 
                        (float) CENTERY);
                // 中心点的右边
                for (int i = first + 1; i < ilength; i++) {
                    double x = CENTERX + (i - first - 0.6) * char_interval;
                    gs.drawString(messages[i],(float) x,
                            (float) CENTERY);
                }
                // 中心点的左边
                for (int i = first - 1; i > -1; i--) {
                    double x = CENTERX - (first - i + 0.6) * char_interval;
                    gs.drawString(messages[i],(float) x,
                            (float) CENTERY);
                }
            } else {
                // 中心点的右边
                for (int i = second; i < ilength; i++) {
                    double x = CENTERX + (i - second - 0.1) * char_interval;
                    gs.drawString(messages[i],(float) x,
                            (float) CENTERY);
                }
                // 中心点的左边
                for (int i = first; i > -1; i--) {
                    double x = CENTERX - (first + 1 - i + 0.1) * char_interval;
                    gs.drawString(messages[i],(float) x,
                            (float) CENTERY);
                }
    
            }
        }
    }
    
    /**
     * 画上方圆弧的汉字
     * 
     * @param gs
     * @param message
     * @param CENTERX
     * @param CENTERY
     * @param r
     */
    public static void drawUpperMessage(Graphics2D gs, String message, int CENTERX, int CENTERY, int r) {
        if (message != null) {
            // 根据输入字符串得到字符数组
            String[] messages2 = message.split("", 0);
            String[] messages = new String[messages2.length];
            System.arraycopy(messages2, 0, messages, 0, messages2.length);
            // 输入的字数
            int ilength = messages.length;
            Font f = gs.getFont();
            FontRenderContext context = gs.getFontRenderContext();
            Rectangle2D bounds = f.getStringBounds(message, context);
            // 字符宽度＝字符串长度/字符数
            double char_interval = (bounds.getWidth() / ilength) - 1;
            // 上坡度
            double ascent = -bounds.getY()-2;
            int first = 0, second = 0;
            boolean odd = false;
            if (ilength % 2 == 1) {
                first = (ilength - 1) / 2;
                odd = true;
            } else {
                first = (ilength) / 2 - 1;
                second = (ilength) / 2;
                odd = false;
            }
            double r2 = r - ascent;
            double x0 = CENTERX;
            double y0 = CENTERY - r + ascent;
            // 旋转角度
            double a = 2 * Math.asin(char_interval / (2 * r2));
    
            if (odd) {
                gs.drawString(messages[first],(float)(x0 - char_interval / 2), (float) y0);
                // 中心点的右边
                for (int i = first + 1; i < ilength; i++) {
                    double aa = (i - first) * a;
                    double ax = r2 * Math.sin(aa);
                    double ay = r2 - r2 * Math.cos(aa);
                    AffineTransform transform = AffineTransform.getRotateInstance(aa);
                    Font f2 = f.deriveFont(transform);
                    gs.setFont(f2);
                    gs.drawString(messages[i],(float) (x0 + ax - char_interval / 2 * Math.cos(aa)),
                            (float) (y0 + ay - char_interval / 2 * Math.sin(aa)));
                }
                // 中心点的左边
                for (int i = first - 1; i > -1; i--) {
                    double aa = (first - i) * a;
                    double ax = r2 * Math.sin(aa);
                    double ay = r2 - r2 * Math.cos(aa);
                    AffineTransform transform = AffineTransform.getRotateInstance(-aa);
                    Font f2 = f.deriveFont(transform);
                    gs.setFont(f2);
                    gs.drawString(messages[i],
                    (float) (x0 - ax - char_interval / 2 * Math.cos(aa)),
                    (float) (y0 + ay + char_interval / 2 * Math.sin(aa)));
                }
            } else {
                // 中心点的右边
                for (int i = second; i < ilength; i++) {
                    double aa = (i - second + 0.3) * a;
                    double ax = r2 * Math.sin(aa);
                    double ay = r2 - r2 * Math.cos(aa);
                    AffineTransform transform = AffineTransform.getRotateInstance(aa);
                    Font f2 = f.deriveFont(transform);
                    gs.setFont(f2);
                    gs.drawString(messages[i],
                        (float) (x0 + ax - char_interval / 2 * Math.cos(aa)),
                        (float) (y0 + ay - char_interval / 2 * Math.sin(aa)));
                }
                // 中心点的左边
                for (int i = first; i > -1; i--) {
                    double aa = (first - i + 0.7) * a;
                    double ax = r2 * Math.sin(aa);
                    double ay = r2 - r2 * Math.cos(aa);
                    AffineTransform transform = AffineTransform.getRotateInstance(-aa);
                    Font f2 = f.deriveFont(transform);
                    gs.setFont(f2);
                    gs.drawString(messages[i],
                        (float) (x0 - ax - char_interval / 2 * Math.cos(aa)),
                        (float) (y0 + ay + char_interval / 2* Math.sin(aa)));
                }
            }
        }
    }
    
    /**
     * 画下方圆弧数字编码
     * 
     * @param gs
     * @param message
     * @param CENTERX
     * @param CENTERY
     * @param r
     */
    public static void drawBelowNumber(Graphics2D gs, String message, int CENTERX, int CENTERY, int r) {
        if (message != null) {
            // 根据输入字符串得到字符数组
            String[] messages2 = message.split("", 0);
            String[] messages = new String[messages2.length - 1];
            System.arraycopy(messages2, 1, messages, 0, messages2.length - 1);
            // 输入的字数
            int ilength = messages.length;
            Font f = gs.getFont();
            FontRenderContext context = gs.getFontRenderContext();
            Rectangle2D bounds = f.getStringBounds(message, context);
            // 字符宽度＝字符串长度/字符数
            double char_interval = (bounds.getWidth() / ilength) + 4;
            // 上坡度
            double ascent = -bounds.getY();
            int first = 0, second = 0;
            boolean odd = false;
            if (ilength % 2 == 1) {
                first = (ilength - 1) / 2;
                odd = true;
            } else {
                first = (ilength) / 2 - 1;
                second = (ilength) / 2;
                odd = false;
            }
            double r2 = r - ascent;
            double x0 = CENTERX;
            double y0 = CENTERY - r + ascent;
            // 旋转角度
            double a = 2 * Math.asin(char_interval / (2 * r2));
            if (odd) {
                // 中心点的右边
                for (int i = first + 1; i < ilength; i++) {
                    double aa = (i - first + 0.5) * a;
                    double ax = r2 * Math.sin(aa);
                    double ay = r2 - r2 * Math.cos(aa);
                    AffineTransform transform = AffineTransform.getRotateInstance(2*Math.PI - aa);
                    Font f2 = f.deriveFont(transform);
                    gs.setFont(f2);
                    gs.drawString(messages[i],
                        (float) (x0 + ax - char_interval / 2 * Math.cos(aa)),
                        2*CENTERY - (float) (y0 + ay - char_interval / 2 * Math.sin(aa)));
                }
                // 中心点的左边
                for (int i = first; i > -1; i--) {
                    double aa = (first - i - 0.5) * a;
                    double ax = r2 * Math.sin(aa);
                    double ay = r2 - r2 * Math.cos(aa);
                    AffineTransform transform = AffineTransform.getRotateInstance(2*Math.PI + aa);
                    Font f2 = f.deriveFont(transform);
                    gs.setFont(f2);
                    gs.drawString(messages[i],
                        (float) (x0 - ax - char_interval / 2 * Math.cos(aa)),
                        2*CENTERY - (float) (y0 + ay + char_interval / 2 * Math.sin(aa)));
                }
            } else {
                // 中心点的右边
                for (int i = second; i < ilength; i++) {
                    double aa = (i - second + 1) * a;
                    double ax = r2 * Math.sin(aa);
                    double ay = r2 - r2 * Math.cos(aa);
                    AffineTransform transform = AffineTransform.getRotateInstance(2*Math.PI - aa);
                    Font f2 = f.deriveFont(transform);
                    gs.setFont(f2);
                    gs.drawString(messages[i],
                        (float) (x0 + ax - char_interval / 2 * Math.cos(aa)),
                        2*CENTERY - (float) (y0 + ay - char_interval / 2 * Math.sin(aa)));
                }
                // 中心点的左边
                for (int i = first; i > -1; i--) {
                    double aa = (first - i) * a;
                    double ax = r2 * Math.sin(aa);
                    double ay = r2 - r2 * Math.cos(aa);
                    AffineTransform transform = AffineTransform.getRotateInstance(2*Math.PI + aa);
                    Font f2 = f.deriveFont(transform);
                    gs.setFont(f2);
                    gs.drawString(messages[i],
                        (float) (x0 - ax - char_interval / 2 * Math.cos(aa)),
                        2*CENTERY - (float) (y0 + ay + char_interval / 2* Math.sin(aa)));
                }
            }
        }
    }
    
    /**
     * 画章中间的五角星
     * 
     * @param g
     * @param x
     * @param y
     * @param r
     */
    public static void drawFiveStar(Graphics g, int x, int y, int r) {
        double ch=72*Math.PI/180;
        int x1=x,
            x2=(int)(x-Math.sin(ch)*r),
            x3=(int)(x+Math.sin(ch)*r),
            x4=(int)(x-Math.sin(ch/2)*r),
            x5=(int)(x+Math.sin(ch/2)*r);
        int y1=y-r,
            y2=(int)(y-Math.cos(ch)*r),
            y3=y2,
            y4=(int)(y+Math.cos(ch/2)*r),
            y5=y4;
        int bx=(int)(x+Math.cos(ch)*Math.tan(ch/2)*r);
        int by=y2; 

        Polygon a=new Polygon();
        Polygon b=new Polygon();
        a.addPoint(x2,y2);
        a.addPoint(x5,y5);
        a.addPoint(bx,by);
        b.addPoint(x1,y1);
        b.addPoint(bx,by);
        b.addPoint(x3,y3);
        b.addPoint(x4,y4);

        g.fillPolygon(a);
        g.fillPolygon(b);
    }

    public static void main(String[] args) {
        //生成一个签章（用java.awt 画一个章）  章:上需要几个常量值Constants.CHAPTER_NAME, Constants.CHAPTER_COMPANY_NAME, Constants.CHAPTER_SOCIAL_NUMBER
        String imgPath = DrawSeal.drawCircularChapter("test1", "test2", "123456132736");
        System.out.println("done");
    }
}

/*-----------------------------------------华丽的分割线  如果你需要以上操作 直接复制粘贴即可------------------------------------------------------------------------

//写入到pdf 公章处
            //pdf模板所在路径 (通过类名获取文件路径)
            String templateFileName = ContractServiceImpl.class.getClassLoader().getResource("sb_loan_contract_borrow.pdf").getFile();
            //使用itext的包读取pdf模板
            PdfReader pdfReader = new PdfReader(templateFileName);
            
            // 提取pdf中的表单输入域字段
            AcroFields form = pdfStamper.getAcroFields();
            // 通过域名获取所在页(页数)和坐标，左下角为起点
            int pageNo = form.getFieldPositions("Chapter").get(0).page;
            Rectangle signRect = form.getFieldPositions("Chapter").get(0).position;
            //获取x y 坐标 
            float x = signRect.getLeft();
            float y = signRect.getBottom();

            //生成一个签章（用java.awt 画一个章）  章:上需要几个常量值Constants.CHAPTER_NAME, Constants.CHAPTER_COMPANY_NAME, Constants.CHAPTER_SOCIAL_NUMBER
            String imgPath = DrawSeal.drawCircularChapter(Constants.CHAPTER_NAME, Constants.CHAPTER_COMPANY_NAME, Constants.CHAPTER_SOCIAL_NUMBER);
            
            // 读图片
            Image image = Image.getInstance(imgPath);
            // 获取操作的页面
            PdfContentByte under = pdfStamper.getOverContent(pageNo);
            // 根据域的大小缩放图片
            image.scaleToFit(signRect.getWidth(), signRect.getHeight());
            // 添加图片
            image.setAbsolutePosition(x, y);
            under.addImage(image);
    
            pdfStamper.flush();
            pdfStamper.close();
            pdfReader.close();*/
