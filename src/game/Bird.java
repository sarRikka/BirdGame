package game;
import java.awt.*;
import javax.swing.*;
import javax.imageio.ImageIO;

public class Bird {

	Image image;
	int x,y;
	int width,height;
	int size;
	//重力加速度
	double g;
	//位移间隔时间
	double t;
	//最初速度
	double v0;
	//当前上抛速度
	double speed;
	//经过时间t后的位移
	double s;
	//小鸟的倾角（弧度）
	double alpha;
	
	//一组图片记录小鸟动画帧
	Image[] images;
	//帧数下标
	int index;
	
	public Bird() throws Exception
	{
		image=new ImageIcon("source/0.png").getImage();
		width = image.getWidth(null);
		height = image.getHeight(null);
		x=132;
		y=280;
		size=40;
		//位移参数
		g=4;
		v0=20;
		t=0.25;
		speed=v0;
		s=0;
		//小鸟偏转角度
		alpha=0;
		
		//初始化动画帧参数
		images=new Image[8];
		
		for(int i=0;i<8;i++)
		{
			images[i]=new ImageIcon("source/"+i+".png").getImage();
		}
		index=0;
		
	}
	
	//飞行动作（变化一帧）
	public void fly()
	{
		index++;
		image=images[(index/12)%8];
	}
	
	//移动一步
	public void step()
	{
		double v0=speed;
		//计算上跑位移
		s=v0*t+g*t*t/2;
		//计算鸟的坐标
		y=y-(int)s;
		//计算下次移动速度
		double v=v0-g*t;
		speed =v;
		//计算倾角（反正切函数）
		alpha=Math.atan(s/8);
		
	}
	
	//向上飞行
	public void flappy()
	{
		//重置速度
		speed=v0;
	}
	
	//检测小鸟是否碰撞到地面
	public boolean hit(Ground ground)
	{
		boolean hit =y+size/2>ground.y;
		if(hit)
		{
			y=ground.y-size/2;
			alpha=Math.PI/2;
		}
		return hit;
	}
	
	//检测小鸟是否撞到柱子
	public boolean hit(Column column)
	{
		//检测是否在柱子范围内
		if(x>column.x-column.width/2-size/2&&x<column.x+column.width/2+size/2)
		{
			if(y>column.y-column.gap/2+size/2&&y<column.y+column.gap/2-size/2) return false;
			return true;
		}
		return false;
	}
}
