package com.iitb.clicker;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;



import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.RectF;

import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;



abstract public class GraphView extends LinearLayout 
{
	abstract public void drawSeries(Canvas canvas, GraphViewData[] values, float graphwidth, float graphheight, float border, double minX, double minY, double diffX, double diffY, float horstart);
	protected final Paint paint;

	private String title;
	private boolean scrollable;

	
	private final List<GraphViewSeries> graphSeries;
	private boolean showLegend = true;
	private float legendWidth = 200;
	private LegendAlign legendAlign = LegendAlign.TOP;
	private boolean manualYAxis;
	
	
//constructor	
	   public GraphView(Context context, String title)
	   {
		                      super(context);
		                      setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));

		                      if (title == null)
			                             title = "Reports";
		                      else
			                             this.title = title;

		                       paint = new Paint();
		                       graphSeries = new ArrayList<GraphViewSeries>();

		                    
		                       addView(new GraphViewContentView(context), new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT, 1));
	 }
	
	
	    static final private class GraphViewConfig 
	    {
	               	static final float BORDER = 20;
		            static final float VERTICAL_LABEL_WIDTH = 100;
		            static final float HORIZONTAL_LABEL_HEIGHT = 80;
	    }
	    public enum LegendAlign 
		{
			TOP, MIDDLE, BOTTOM
		}
	    private class GraphViewContentView extends View 
        {
		             private float lastTouchEventX;
		             private float graphwidth;

		             public GraphViewContentView(Context context)
		             {
			                        super(context);
			                        setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
		             }

	
		             @Override
		             protected void onDraw(Canvas canvas)
		             {

                                      paint.setAntiAlias(true);paint.setStrokeWidth(0);

			                          float border = GraphViewConfig.BORDER;float horstart = 0;float height = getHeight()-100;float width = 60*(graphSeries.get(0).values.length);//getWidth() - 1;
			                          double maxY = 100;double minY = 0;double diffY = maxY - minY;double maxX = width;//getMaxX(false);
			                          double minX = 10;//getMinX(false);
			                          double diffX = maxX - minX;float graphheight = height - (2 * border);graphwidth = width;

			                        

			                          paint.setTextAlign(Align.CENTER);
			                          canvas.drawText(title, (graphwidth / 2) + horstart, border - 4, paint);

			                          if (maxY != minY) 
			                          {
				                                      paint.setStrokeCap(Paint.Cap.ROUND);

				                                      for (int i=0; i<graphSeries.size(); i++) 
				                                      {
					                                                       paint.setStrokeWidth(graphSeries.get(i).style.thickness);
					                                                       paint.setColor(graphSeries.get(i).style1.color);
					                                                       GraphViewData[] values = graphSeries.get(i).values;
					                             		                   List<GraphViewData> listData = new ArrayList<GraphViewData>();
					                                                       for (int j=0; j<values.length; j++)
					                                                       {
					                                                                   listData.add(values[j]);
					                                                       }
					                                                  
					                                                       drawSeries(canvas,listData.toArray(new GraphViewData[listData.size()]), graphwidth, graphheight, border, minX, minY, diffX, diffY, horstart);
				                                      }

				                                      if (showLegend) drawLegend(canvas, height, width);
			                          }
		          }
        }
	    protected void drawLegend(Canvas canvas, float height, float width) 
		{
			               int shapeSize = 10;

			               // rect
			                paint.setARGB(180, 100, 100, 100);
			                float legendHeight = (shapeSize+5)*graphSeries.size() +5;
			                float lLeft =20;// width-legendWidth - 50;
			                float lTop;
			                switch (legendAlign)
			                {
			                            case TOP:lTop = 10;break;
			                            case MIDDLE:lTop = height/2 - legendHeight/2;break;
			                            default:lTop = height - GraphViewConfig.BORDER - legendHeight -10;
			                }
			               
			                float lRight = lLeft+legendWidth;
			                float lBottom = lTop+legendHeight;
			               // canvas.drawRoundRect(new RectF(lLeft, lTop, lRight+50, lBottom), 8, 8, paint);

			               for (int i=0; i<graphSeries.size(); i++)
			               {   
			            	            

			            	              
				                          if (graphSeries.get(i).description != null) 
				                          {
				                        	  paint.setColor(Color.rgb(11, 165, 41));
					                          canvas.drawRect(new RectF(lLeft+10, lTop+5+(i*(shapeSize+5)), lLeft+15+shapeSize, lTop+((i+1)*(shapeSize+5))), paint);
					                                  paint.setTextAlign(Align.LEFT);
					                                  canvas.drawText(graphSeries.get(i).description, lLeft+20+shapeSize+5, lTop+shapeSize+(i*(shapeSize+5)), paint);
				                          }
				                          
				                          if (graphSeries.get(i).description1!= null) 
				                          {           paint.setColor(Color.rgb(215, 39, 45));
				                                       canvas.drawRect(new RectF(lLeft+120, lTop+5+(i*(shapeSize+5)), lLeft+125+shapeSize, lTop+((i+1)*(shapeSize+5))), paint);
					                                  
					                                  paint.setTextAlign(Align.LEFT);
					                                  canvas.drawText(graphSeries.get(i).description1, lLeft+130+shapeSize+5, lTop+shapeSize+(i*(shapeSize+5)), paint);
				                          }
				                        
				                          if (graphSeries.get(i).description2!= null) 
				                          {
				                        	          paint.setColor(Color.BLUE);
					                                  canvas.drawRect(new RectF(lLeft+220, lTop+5+(i*(shapeSize+5)), lLeft+225+shapeSize, lTop+((i+1)*(shapeSize+5))), paint);
					                                  paint.setTextAlign(Align.LEFT);
					                                  canvas.drawText(graphSeries.get(i).description2, lLeft+235+shapeSize+5, lTop+shapeSize+(i*(shapeSize+5)), paint);
				                          }
			               }
		}	  //.....................................................................................................................
	    
	    
	    
   
	static public class GraphViewStyle 
	   {
		              public int color = 0xffff0000;
		              public int color1 = 0xff0000ff;
		              public int thickness = 3;
		              public GraphViewStyle()
		              {
			                          super();
		              }
		             public GraphViewStyle(int color, int thickness)
		             {
			                           super();
			
			                           this.color = color;
			                           this.thickness = thickness;
	                 }
	  }
	public static class GraphViewData
	  {
		              public final String valueX;
		              public final double valueY;
		              public GraphViewData(String valueX, double valueY)
		              {
			                            super();
			                            this.valueX = valueX;
			                            this.valueY = valueY;
		              }
	  }
	static public class GraphViewSeries 
	{
		             final String description;
		             final String description1;
		             final String description2;
		             final GraphViewStyle style;
		             final GraphViewStyle style1;
		             final GraphViewData[] values;
		           
		             public GraphViewSeries(String description,String description1,String description2,GraphViewData[] values)
		             {
			                            super();
			                            this.description = description;
			                            this.description1=description1;
			                            this.description2=description2;
			                            this.values = values;

			                            style = new GraphViewStyle(Color.GREEN,3);
			                            style1 = new GraphViewStyle(Color.RED,3);
		            }
	    }
	public void addSeries(GraphViewSeries series)
	{
		graphSeries.add(series);
	}
  
    public void setShowLegend(boolean showLegend)
	{
		this.showLegend = showLegend;
	}
   
}
