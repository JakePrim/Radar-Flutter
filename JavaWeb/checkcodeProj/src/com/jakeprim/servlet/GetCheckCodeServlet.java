package com.jakeprim.servlet;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Robot;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class GetCheckCodeServlet
 */
@WebServlet("/getckeckcode")
public class GetCheckCodeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetCheckCodeServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int width  = 120;
		int height = 30;
		//1. 在内存中生成一张图片
		BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		//2. 设置背景颜色和绘制边框
		Graphics graphics =  bufferedImage.getGraphics();
		//绘制背景
		graphics.setColor(Color.YELLOW);
		graphics.fillRect(0, 0, width, height);
		//绘制边框
		graphics.setColor(Color.BLUE);
		graphics.drawRect(0, 0, width-1, height-1);
		//3.随机生成四个字母或数字 写到图片中
		//设置颜色和字体
		Graphics2D graphics2d = (Graphics2D) graphics;
		graphics2d.setColor(Color.BLACK);
		graphics2d.setFont(new Font("宋体", Font.BOLD, 18));
		String words = "QWERTYUIOPASDFGHJKLZXCVBNMqwertyuiopasdfghjklzxcvbnm1234567890";
		Random random = new Random();
		int x = 10;
		StringBuffer code = new StringBuffer();//存储验证码
		//随机取4个
		for (int i = 0; i < 4; i++) {
			int index = random.nextInt(words.length());
			char word = words.charAt(index);
			code.append(word);
			//获取旋转的角度
			int angle = random.nextInt(60) - 30; //在+30 与 -30之间
			//转换为弧度
			double radian = angle * Math.PI / 180;
			graphics2d.rotate(radian, x, 20);
			//绘制在图片上
			graphics2d.drawString(String.valueOf(word), x, 20);
			//绘制完之后在旋转回来
			graphics2d.rotate(-radian, x, 20);
			x+=30;
		}
		System.out.println("验证码:"+code.toString());
		//将验证码存入到session作用域中
		request.getSession().setAttribute("checkcode1", code.toString());
		//4. 将内存中的图片进行输出到浏览器
		ImageIO.write(bufferedImage, "jpg", response.getOutputStream());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
