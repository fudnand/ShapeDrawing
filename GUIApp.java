import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;

import javax.swing.*;
public class GUIApp {

	public static void main(String[] args) {
		JFrame f = new AppFrame("My First App Frame");

	}

}
class AppFrame extends JFrame{
	JPanel pnlGraphics, buttons, bottomBTNs, container;
	static JTextField pnlTextBox;
	JButton btnTrianle, btnRect, btnCircle, btnClear;
	static String shape; 
	
	public AppFrame(String title){
		super(title);
		shape = "circle";
	}
	
	public void frameInit(){
		super.frameInit();
		this.setLayout(new GridLayout(1,2));
		pnlGraphics = new GraphicsPanel();
		this.add(createContainer());
		
		this.setSize(450,300);
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public static int getSizeInput(){
		int size = 50;
		try {
			size = Integer.parseInt(pnlTextBox.getText());				
		}catch(Exception e){
			System.out.println("No number entered, defaulted to 50");
		}
		return size;
	}
	
	private JPanel createContainer(){
		container = new JPanel();
		buttons = new JPanel();
		pnlTextBox = new JTextField(10);
		pnlTextBox.setBorder(BorderFactory.createTitledBorder("Enter size:")); 		
		
		btnTrianle = new JButton("Triangle");
		btnRect = new JButton("Rectangle");
		btnCircle = new JButton("Circle");
				
		buttons.add(btnTrianle);
		buttons.add(btnRect);
		buttons.add(btnCircle);
		
		bottomBTNs = new JPanel();
		btnClear = new JButton("Clear");
		bottomBTNs.add(btnClear);
		bottomBTNs.add(pnlTextBox);
		
		container.setLayout(new BorderLayout());
		container.add(buttons, BorderLayout.NORTH);
		container.add(pnlGraphics, BorderLayout.CENTER);
		container.add(bottomBTNs, BorderLayout.SOUTH);
		
		btnRect.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				shape = "rect";			
			}
		});
		
		btnCircle.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				shape = "circle";
				
			}
		});
		btnTrianle.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				shape = "triangle";	
			}
		});
		
		btnClear.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				((GraphicsPanel) pnlGraphics).clear();				
			}
		});		
		return container;
	}
		
}

class GraphicsPanel extends JPanel{
	ArrayList<GeoShape> drawings;
	public GraphicsPanel(){
		super();
		drawings = new ArrayList<GeoShape>();
		this.addMouseListener(new MsListener());
	}
	
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		for(GeoShape art: drawings){
			art.draw(g);
		}
	}
	
	public void clear(){
		drawings.clear();
		repaint();
	}
	
	class MsListener extends MouseAdapter{
		@Override
		public void mousePressed(MouseEvent e) {
			int size = AppFrame.getSizeInput();
			GeoShape s = ShapeFactory.createShape(AppFrame.shape, e.getX(), e.getY(), size); //draws the shape kind mentioned in the brackets
			s.draw(getGraphics());
			drawings.add(s);
		}//event handling method
		
	}//event handling class
}

class ShapeFactory{
	static GeoShape createShape(String kind, int x, int y, int size){
		if(kind.equalsIgnoreCase("rect")){
			return new Rect(x, y, size);
		}
		else if(kind.equalsIgnoreCase("circle")){
			return new Circle(x, y, size);
		}
		else if (kind.equalsIgnoreCase("triangle")){
			return new Triangle(x, y, size);
		}
		return null;
	}
}

abstract class GeoShape{
	int x, y, size;
	public GeoShape(int x, int y, int size){
		this.x = x; this.y = y; this.size = size;
	}
	public abstract void draw(Graphics g);
	
}

class Rect extends GeoShape{
	public Rect(int x, int y, int size) {
		super(x, y, size);
	}

	@Override
	public void draw(Graphics g) {
		g.drawRect(x, y, size, size);
	}
}

class Triangle extends GeoShape{
	public Triangle(int x, int y, int size) {
		super(x, y, size);
	}

	@Override
	public void draw(Graphics g) {
		int t[] = { x, size/2+x, size+x};
		int d[] = {size+y, y, size+y}; 
		g.drawPolygon(t, d, 3);
	}
}

class Circle extends GeoShape{

	public Circle(int x, int y, int size) {
		super(x, y, size);
	}

	@Override
	public void draw(Graphics g) {		
		g.drawOval(x, y, size, size);
	}
}

class TextPanel extends JPanel{
	JTextArea txtEditor;
	JScrollPane scroll;
	
	JButton btnClear;
	JPanel pnlButtons;
	
	public TextPanel(){
		super();
		setup();
	}
	
	private void setup(){
		txtEditor = new JTextArea(5, 10);
		scroll = new JScrollPane(txtEditor);
		scroll.setPreferredSize(new Dimension(450, 270));
		
		btnClear = new JButton("Clear");
		pnlButtons = new JPanel();
		
		this.setLayout(new BorderLayout());
		this.add(scroll, BorderLayout.CENTER); 
		pnlButtons.add(btnClear);
		this.add(pnlButtons, BorderLayout.SOUTH); //puts buttons at south location
		
		btnClear.addActionListener(new ActionListener() { //example of anonymous class			
			@Override
			public void actionPerformed(ActionEvent e) {
				txtEditor.setText("");
			}
		});  //registration method	
	}
}
