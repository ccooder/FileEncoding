package me.fenglu.gui;

import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import me.fenglu.util.FileEncodingConverter;

public class FileEncodingConverterMainFrame extends JFrame{
	
	private static final long serialVersionUID = -3809426880243983474L;
	
	private JTextField file;
	private static JTextArea info;
	private static JScrollPane scroll;
	private JButton btnChange;
	
	public void launchFrame(FileEncodingConverterMainFrame frame) {
		this.setBounds(100, 100, 400, 300);
		JPanel panelUp = new JPanel();
		
		panelUp.setLayout(new BorderLayout());
		info = new JTextArea();
		info.setText("请双击文本框选择文件！");
		info.setLineWrap(true);
		info.setAutoscrolls(true);
		info.setRows(14);
		info.setEditable(false);
		scroll = new JScrollPane(info);
		//JScrollBar bar = scroll.getVerticalScrollBar();
		scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED); 
		scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		
		panelUp.add(scroll, BorderLayout.CENTER);
		JPanel panelDown = new JPanel() {
			private static final long serialVersionUID = 448345287521503766L;
			@Override
			public void paint(Graphics g) {
				super.paint(g);
				//g.drawImage(img, 0, 0, this);
			}
			
		};
		file = new JTextField(20);
		btnChange = new JButton("开始转换");
		JLabel labelfile = new JLabel("工程目录：");
		panelDown.add(labelfile);
		panelDown.add(file);
		panelDown.add(btnChange);
		ImageIcon icon = new ImageIcon(this.getClass().getResource("/asserts/icon/sevenColorFlower.png"));
		Image iconImg = icon.getImage();
		this.setResizable(false);
		this.setTitle("项目编码转换器--七色花软件制作");
		this.setName("项目编码转换器--七色花软件制作");
		this.setLayout(new BorderLayout());
		this.setIconImage(iconImg);
		this.add(panelUp, BorderLayout.NORTH);
		this.add(panelDown, BorderLayout.SOUTH);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.pack();
		this.setVisible(true);
		
		btnChange.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if(file.getText().trim().equals("")) {
					JOptionPane.showMessageDialog(btnChange.getParent(), "请先选择目录", "错误", JOptionPane.ERROR_MESSAGE);
				}else {
					info.setText("开始转换：\r\n\r\n");
					//new FileEncodingConverter().toUTF8(JavaSouceFilePickup.pickup(file.getText()), frame);
					FileEncodingThread fileEncodingThread = new FileEncodingThread();
					ThreadListener threadListener = new ThreadListener();
					
					fileEncodingThread.setConverter(new FileEncodingConverter());
					fileEncodingThread.setFrame(frame);
					
					
					Thread t = new Thread(fileEncodingThread);
					
					threadListener.setT(t);
					threadListener.setFrame(frame);
					Thread t1 = new Thread(threadListener);
					
					t.start();
					t1.start();
					btnChange.setEnabled(false);
				}
			}
			
		});
		
		file.setEditable(false);
		file.addMouseListener(new MouseListener () {

			@Override
			public void mouseClicked(MouseEvent e) {
				if(e.getClickCount() == 2) {
					JFileChooser fileChooser = new JFileChooser(file.getText());
					fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
					int option = fileChooser.showDialog(file.getParent(), "选择工程目录");
					if(option == JFileChooser.APPROVE_OPTION) {
						File f=fileChooser.getSelectedFile(); 
						String absolutePath = f.getAbsolutePath();
						fileChooser.setCurrentDirectory(f);
						file.setText(absolutePath);
						//new FileEncodingConverter().toUTF8(JavaSouceFilePickup.pickup(absolutePath), FileEncodingConverterMainFrame.this);
					}
				}
			}

			@Override
			public void mousePressed(MouseEvent e) {
				
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				
			}

			@Override
			public void mouseExited(MouseEvent e) {
				
			}
			
		});
	}

	public static void main(String[] args) {
		FileEncodingConverterMainFrame frame = new FileEncodingConverterMainFrame();
		frame.launchFrame(frame);
		//System.out.println(FileEncodingConverterMainFrame.class.getResource("/asserts/icon/sevenColorFlower.png"));
	}
	
	public void done() {
		btnChange.setEnabled(true);
		JOptionPane.showMessageDialog(btnChange.getParent(), "所有文件已转码完毕！", "Yah!", JOptionPane.INFORMATION_MESSAGE);
	}
	
	public void displayInfo(String tip) {
		if(info != null) {

			//String text = info.getText() + "\r\n";
			info.append("\r\n"+tip);
			//info.paintImmediately(info.getBounds());
			int  height =  100;   
		    Point p = new  Point();
		    p.setLocation(0 , info.getLineCount()*height);
		    scroll.getViewport().setViewPosition(p);
		}
	}

	public JTextField getFile() {
		return file;
	}
}
