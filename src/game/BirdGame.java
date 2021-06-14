package game;

import javax.imageio.ImageIO;
import java.util.*;

import javax.swing.*;

import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import javax.imageio.*;
import java.awt.*;

public class BirdGame extends JPanel {
	
	
	Image background;//背景图片
	Image startImage;//开始图片
	Image overImage;//游戏结束图片
	Ground ground;//地面
	Column column1,column2;//两根柱子
	Bird bird;//小鸟
	int score;//游戏分数
	int state;//游戏状态
	//状态常量
	public static final int START=0;//开始
	public static final int RUNNING=1;//运行
	public static final int GAME_OVER=2;//结束
	
	public BirdGame() throws Exception
	{
		//初始背景图片
		//background = new ImageIcon("D:\\java\\elipse\\project_workplace\\Bird\\src\\resources\\bg.png").getImage();//绝对路径
		//background = new ImageIcon("src\\resources\\bg.png").getImage();//相对路径，一开始默认位置是Bird项目文件夹
		background = new ImageIcon("./source/bg.png").getImage();
		startImage = new ImageIcon("./source/start.png").getImage();
		overImage=new ImageIcon("./source/gameover.png").getImage();
		//初始化地面、柱子、小鸟、分号、状态
		ground=new Ground();
		column1=new Column(1);
		column2=new Column(2);
		bird=new Bird();
		score=0;
		state=0;
	}
	
	public void paint(Graphics g)
	{
		//绘制背景
		g.drawImage(background, 0, 0,null);
		//绘制地面
		g.drawImage(ground.image, ground.x, ground.y, null);
		//绘制柱子
		g.drawImage(column1.image,column1.x-column1.width/2,column1.y-column1.height/2,null);
		g.drawImage(column2.image,column2.x-column2.width/2,column2.y-column2.height/2,null);
		//绘制小鸟
		Graphics2D g2=(Graphics2D) g;
		g2.rotate(-bird.alpha,bird.x,bird.y);
		g.drawImage(bird.image,bird.x-bird.width/2,bird.y-bird.height/2,null);
		g2.rotate(bird.alpha,bird.x,bird.y);
		//绘制分数
		Font f=new Font(Font.SANS_SERIF,Font.BOLD,40);
		g.setFont(f);
		g.drawString(""+score, 40, 60);
		g.setColor(Color.WHITE);
		g.drawString(""+score,40-3, 60-3);//一个阴影效果
		//绘制开始和结束界面
		switch(state)
		{
		case START:
			g.drawImage(startImage, 0, 0, null);
			break;
		case GAME_OVER:
			g.drawImage(overImage, 0, 0, null);
			break;
		}
	}
	
	public void action() throws Exception
	{
		//不断重复和绘制
		MouseListener l=new MouseAdapter()
		{
			public void mousePressed(MouseEvent e)
			{
				try {
					switch(state) {
					case START:
						//在开始前按下鼠标转为运行状态
						state=RUNNING;
						break;
					case RUNNING:
						//在运行状态，按下鼠标小鸟向上飞行
						bird.flappy();
						break;
					case GAME_OVER:
						//在结束状态，按下鼠标重置数据变为开始
						column1=new Column(1);
						column2=new Column(2);
						bird=new Bird();
						score=0;
						state=START;
						break;
					}
				}
				catch (Exception ex)
				{
					ex.printStackTrace();
				}
			}
			
		};
		addMouseListener(l);
		while(true)
		{
			switch(state)
			{
			case START:
				bird.fly();
				ground.step();
				break;
			case RUNNING:
				ground.step();
				column1.step();
				column2.step();
				bird.fly();
				bird.step();
//				if(bird.x==column1.x||bird.x==column2.x)
//				{
//					score++;
//				}
				score++;
				//检测是否碰撞
				if(bird.hit(ground)||bird.hit(column1)||bird.hit(column2))
				{
					state=GAME_OVER;
				}
				break;
			}	
			//休眠1000/60毫秒
			Thread.sleep(1000/60);
			repaint();
		}
	}
	
	//启动方法
	
	public static void main(String[] args) throws Exception
	{
		
		JFrame frame=new JFrame();
		BirdGame game=new BirdGame();
		frame.add(game);
		frame.setSize(440,670);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		game.action();
	}
	
}
