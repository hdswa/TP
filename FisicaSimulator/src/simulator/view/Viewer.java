package simulator.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.border.TitledBorder;

import simulator.control.Controller;
import simulator.misc.Vector2D;
import simulator.model.Body;
import simulator.model.SimulatorObserver;

public class Viewer extends JComponent implements SimulatorObserver {
	
	private static final long serialVersionUID = 1L;
	private int _centerX;
	private int _centerY;
	private List<Body> _bodies;
	private double _scale;
	private boolean _showHelp;
	private boolean _showVector;
	private boolean doOnce=true;
	
	Viewer(Controller ctrl) {
		_bodies=ctrl.getSim().getBodies();
		_showHelp = true;
		_showVector = true;
		initGUI();
		ctrl.addObserver(this);
	}

	private void initGUI() {
		setLayout(new BorderLayout());
		setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.black), "Viewer", TitledBorder.LEFT, TitledBorder.TOP)); setBackground(Color.WHITE);
		
		addKeyListener(new KeyListener() {
			@Override
			public void keyPressed(KeyEvent e) {
				switch (e.getKeyChar()) {
					case '+':
						_scale = Math.max(1000.0, _scale / 1.1);
						repaint();
						break;
					case '-':
						_scale = _scale * 1.1;
						repaint();
						break;
					case '*':
						autoScale();
						repaint();
						break;
					case 'v':
						_showVector = !_showVector;
						repaint();
						break;
					case 'h':
						_showHelp = !_showHelp;
						repaint();
						break;
					default:
				}
			}
		
			@Override
			public void keyTyped(KeyEvent e) {
			}
		
			@Override
			public void keyReleased(KeyEvent e) {
		
			}
				});
			addMouseListener(new MouseListener() {
			public void mouseEntered(MouseEvent e) {
			requestFocus();
			}
		
			@Override
			public void mouseClicked(MouseEvent e) {
			}
		
			@Override
			public void mousePressed(MouseEvent e) {	
			}
		
			@Override
			public void mouseReleased(MouseEvent e) {
			}
		
			@Override
			public void mouseExited(MouseEvent e) {
			}
		});
	}

	@Override
	protected void paintComponent(Graphics g) {
		if(doOnce) {
			this.autoScale();
			doOnce=false;
		}
		super.paintComponent(g);
		Graphics2D gr = (Graphics2D) g;
		
		gr.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		gr.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		
		// calculate the center
		_centerX = getWidth() / 2;
		_centerY = getHeight() / 2;
		
		// draw a red color cross at center of the viewer
		gr.setColor(Color.RED);
		gr.drawLine(_centerX - 10, _centerY, _centerX + 10, _centerY);
		gr.drawLine(_centerX, _centerY - 10, _centerX, _centerY + 10);
		
		// draw bodies with vectors if _showVectors is true, else only bodies
		for(Body b : _bodies) {
			Vector2D v = b.getVel().direction().scale(20);
			Vector2D f = b.getForce().direction().scale(20);
			int x = _centerX + (int) (b.getPos().getX() / _scale);
		    int y = _centerY -  (int) (b.getPos().getY() / _scale);
		    int x2 = x + (int) v.getX();
		    int y2 = y - (int) v.getY();
		    int x1 = x + (int) f.getX();
		    int y1 = y - (int) f.getY();
		 
		    gr.setColor(Color.BLUE);
		    gr.fillOval(x-5, y-5, 9, 9);
		    gr.setColor(Color.BLACK);
		    gr.drawString(b.getId(), x , y-7);
		            
		    if(_showVector) {
		        drawLineWithArrow(gr, x, y, x2, y2, 5, 5, Color.GREEN, Color.GREEN);
		        drawLineWithArrow(gr, x, y, x1, y1, 5, 5,  Color.RED, Color.RED);
		    }
		}
		
		
		//---------------------------------
		// help panel
		//---------------------------------
		if (this._showHelp) {
			gr.setColor(Color.RED);
			gr.drawString("h : toggle help, v:toggle vectors, + : zoom-in, -: zoom-out, * : autoscale", 5, 23);
			gr.drawString("Scaling ratio: " + _scale, 5, 35);
		}
	}
	
	private void autoScale() {
		double max = 1.0;
		for (Body b : _bodies) {
		Vector2D p = b.getPos();
		max = Math.max(max, Math.abs(p.getX()));
		max = Math.max(max, Math.abs(p.getY()));
		}
		double size = Math.max(1.0, Math.min(getWidth(), getHeight()));
		_scale = max > size ? 4.0 * max / size : 1.0;
	}
	
	private void drawLineWithArrow(//
			Graphics g, //
			int x1, int y1, //
			int x2, int y2, //
			int w, int h, //
			Color lineColor, Color arrowColor) {
		int dx = x2 - x1, dy = y2 - y1;
		double D = Math.sqrt(dx * dx + dy * dy);
		double xm = D - w, xn = xm, ym = h, yn = -h, x;
		double sin = dy / D, cos = dx / D;
		x = xm * cos - ym * sin + x1;
		ym = xm * sin + ym * cos + y1;
		xm = x;
		x = xn * cos - yn * sin + x1;
		yn = xn * sin + yn * cos + y1;
		xn = x;
		int[] xpoints = { x2, (int) xm, (int) xn };
		int[] ypoints = { y2, (int) ym, (int) yn };
		g.setColor(lineColor);
		g.drawLine(x1, y1, x2, y2);
		g.setColor(arrowColor);
		g.fillPolygon(xpoints, ypoints, 3);
	}


	@Override
	public void onDeltaTimeChanged(double dt) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onForceLawsChanged(String fLawsDesc) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onRegister(List<Body> bodies, double time, double dt, String fLawsDesc) {
		_bodies = bodies;
		this.autoScale();
		repaint();
	}
	@Override
	public void onReset(List<Body> bodies, double time, double dt, String fLawsDesc) {
		_bodies = bodies;
		this.autoScale();
		repaint();
	}
	@Override
	public void onBodyAdded(List<Body> bodies, Body b) {
		_bodies = bodies;
		this.autoScale();
		repaint();
	}
	@Override
	public void onAdvance(List<Body> bodies, double time) {
		_bodies = bodies;
		repaint();
		
	}
}
