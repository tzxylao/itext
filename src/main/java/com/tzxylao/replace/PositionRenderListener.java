/**********************************************************************
 * <pre>
 * FILE : PositionRenderListener.java
 * CLASS : PositionRenderListener
 *
 * AUTHOR : caoxu-yiyang@qq.com
 *
 * FUNCTION : TODO
 *
 *
 *======================================================================
 * CHANGE HISTORY LOG
 *----------------------------------------------------------------------
 * MOD. NO.|   DATE   |   NAME  | REASON  | CHANGE REQ.
 *----------------------------------------------------------------------
 * 		    |2016年11月9日|caoxu-yiyang@qq.com| Created |
 * DESCRIPTION:
 * </pre>
 ***********************************************************************/

package com.tzxylao.replace;
 
import java.util.HashMap;
import java.util.List;
import java.util.Map;
 
import com.itextpdf.awt.geom.Rectangle2D.Float;
import com.itextpdf.text.pdf.parser.ImageRenderInfo;
import com.itextpdf.text.pdf.parser.RenderListener;
import com.itextpdf.text.pdf.parser.TextRenderInfo;
 
/**
 * pdf渲染监听,当找到渲染的文本时，得到文本的坐标x,y,w,h
 * @user : caoxu-yiyang@qq.com
 * @date : 2016年11月9日
 */
public class PositionRenderListener implements RenderListener{
	
	private List<String> findText;
	private float defaultH;		///出现无法取到值的情况，默认为12
	private float fixHeight;	//可能出现无法完全覆盖的情况，提供修正的参数，默认为2
	public PositionRenderListener(List<String> findText, float defaultH,float fixHeight) {
		this.findText = findText;
		this.defaultH = defaultH;
		this.fixHeight = fixHeight;
	}
 
	public PositionRenderListener(List<String> findText) {
		this.findText = findText;
		this.defaultH = 12;
		this.fixHeight = 2;
	}
	
	@Override
	public void beginTextBlock() {
		
	}
 
	@Override
	public void endTextBlock() {
		
	}
 
	@Override
	public void renderImage(ImageRenderInfo imageInfo) {
	}
 
	private Map<String, ReplaceRegion> result = new HashMap<String, ReplaceRegion>();
	@Override
	public void renderText(TextRenderInfo textInfo) {
		String text = textInfo.getText();
		for (String keyWord : findText) {
			if (null != text && text.equals(keyWord)){
				Float bound = textInfo.getBaseline().getBoundingRectange();
				ReplaceRegion region = new ReplaceRegion(keyWord);
				region.setH(bound.height == 0 ? defaultH : bound.height);
				region.setW(bound.width);
				region.setX(bound.x);
				region.setY(bound.y-this.fixHeight);
				result.put(keyWord, region);
			}
			System.out.println(text);
		}
	}
 
	public Map<String, ReplaceRegion> getResult(int pageNum) {
		for (String key : findText) {
			ReplaceRegion replaceRegion = this.result.get(key);
			if(replaceRegion != null){
				replaceRegion.setPageNum(pageNum);
				this.result.put(key, replaceRegion);
			}
		}
		return this.result;
	}
}
