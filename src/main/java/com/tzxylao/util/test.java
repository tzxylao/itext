package com.tzxylao.util;

import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;

/**
 * Created by lll on 2018/7/31 16:10.
 */
public class test {
    public static void drawBelowNumber(Graphics2D gs, String message, double CENTERX, double CENTERY, int r) {
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
            double char_interval = (bounds.getWidth() / ilength);
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
                            (float) (2*CENTERY) - (float) (y0 + ay - char_interval / 2 * Math.sin(aa)));
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
                            (float) (2*CENTERY) - (float) (y0 + ay + char_interval / 2 * Math.sin(aa)));
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
                            (float) (2*CENTERY) - (float) (y0 + ay - char_interval / 2 * Math.sin(aa)));
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
                            (float) (2*CENTERY) - (float) (y0 + ay + char_interval / 2* Math.sin(aa)));
                }
            }
        }
    }
}
