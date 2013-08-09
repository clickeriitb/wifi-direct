package com.iitb.clicker;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;


public class BarGraphView extends GraphView
{
	int correctness[]={0,0,0,0,0};
	int stu_count[]=null;
	int k,flag=0;
	public BarGraphView(Context context, String title,int []correctness,int []stu_count,int flag)
	{
		super(context, title);
		this.stu_count=new int[stu_count.length];
		this.correctness=new int[correctness.length];
		for(k=0;k<correctness.length&&k<stu_count.length;k++)
		{
			    this.correctness[k]=correctness[k];
		        this.stu_count[k]=stu_count[k];
		        this.flag=flag;
		}
	}

	  @Override
	  public void drawSeries (Canvas canvas,GraphViewData[] values,float graphwidth, float graphheight,float border,double minX,
			double minY,double diffX,double diffY,float horstart)
	  {
		float colwidth = (float)60;/*(graphwidth - (2 * border)) / values.length;*/
        graphheight-=100;
        horstart=10;
		// draw data
		for (int i = 0; i < values.length; i++)
		{
			float valY = (float) (values[i].valueY - minY);
			float ratY = (float) (valY / diffY);
			float y = graphheight * ratY;
		  
			if(correctness[i]>0)
				   paint.setColor(Color.rgb(11, 165, 41));
				else if(correctness[i]==0)
					 paint.setColor(Color.rgb(215, 39, 45));
				else if(correctness[i]<0)
					 paint.setColor(Color.BLUE);
			
			canvas.drawText((new Integer(stu_count[i])).toString(), (i * colwidth) + horstart+20, (border - y-40) + graphheight, paint);
			if(flag==0)
			canvas.drawText("("+new Double((values[i].valueY)).toString()+"% )", (i * colwidth) + horstart+30, (border - y-20) + graphheight, paint);
			//else
			//canvas.drawText("(marks )", (i * colwidth) + horstart+30, (border - y-20) + graphheight, paint);
			canvas.drawText(values[i].valueX, (i * colwidth) + horstart+30,graphheight+40, paint);
			canvas.drawRect((i * colwidth) + horstart, (border - y) + graphheight, ((i * colwidth) + horstart) + (colwidth - 20), graphheight + border - 1, paint);
		}
	}
}
