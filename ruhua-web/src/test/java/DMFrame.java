
import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.SystemColor;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.TextEvent;
import java.awt.event.TextListener;
import java.util.Calendar;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSlider;
import javax.swing.JToggleButton;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
/**
 * Created with IntelliJ IDEA.
 * User: lijing3
 * Date: 14-12-24
 * Time: 上午10:49
 * To change this template use File | Settings | File Templates.
 */
/*
 * lishu,kaiti,heiti  分别是是隶书、楷书、黑体
 * startbtn,stopbtn,resetbtn,hidebtn,exitbtn; 分别是 开始、停止、恢复、隐藏、退出
 * man ,kuai;   分别是滚动速度按钮
 * neizhi 【】  是 内置字体按钮数组;
 * fontfield,txtfield;  分别是 字体显示框  、 滚动文字输入框、
 * bigfont, smailfont,left_right,right_left,penetrate,reflect; 分别时 文字大小单选按钮与 文字方向 单选按钮
 *   autocolor; 文字颜色复选框
 *   topdrop,backedrop;  前景色、 背景色
 *   topbel ,backbel,speedlabel   分别为 前景色、背景色、颜色自动 标签
 * fontSize,fontName  分别是初始字体大小和样式
 * */

public class DMFrame {
    private JPanel panels;
    private JLabel lblShow;
    private JButton  startbtn, stopbtn, resetbtn, hidebtn,exitbtn;
    private JToggleButton[] zt = new JToggleButton[4];
    private JButton[] neizhi = { new JButton("1"), new JButton("2"),
            new JButton("3"), new JButton("4"), new JButton("5"),
            new JButton("6"), new JButton("7"), new JButton("8") };
    private TextField txtfield;
    private JRadioButton bigfont, smallfont, left_right, right_left, penetrate,reflect;
    private JCheckBox autocolor;
    private JComboBox topdrop, backedrop;
    private JLabel topbel, backbel, speedlabel,colorlabel;
    private JSlider jSliderX;
    private int fontSize = 36;
    private String fontName = "隶书";

    // private Vector<String> v;
    public DMFrame() {
        //ZiMupanel zimu = new ZiMupanel();
        zimu.setVisible(true);
        //Panelshow show = new Panelshow();
        show.setVisible(true);
    }
    public static void main(String args[]) {
        new DMFrame();
    }

    ZiMupanel zimu = new ZiMupanel();
    Panelshow show = new Panelshow();

    class Panelshow extends JFrame {

        public Panelshow() {
            this.setTitle("滚动面板");
            this.setBounds(250, 50, 550, 150);
            this.setResizable(false);//不可以改变窗体大小
            this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            Container con = this.getContentPane();
            panels = new JPanel();
            panels.setBackground(Color.green);
            con.add(panels);

            lblShow = new JLabel("我们都是好孩子！！！");
            lblShow.setFont(new Font(fontName, Font.BOLD, fontSize));
            lblShow.setForeground(Color.red);
            panels.add(lblShow);
        }
    }

    class ZiMupanel extends JFrame {
        public ZiMupanel() {
            Container con = this.getContentPane();
            // 设置窗口名称
            this.setTitle("滚动字幕");
            // 设置布局管理器为 null ，方便 面板定位，一般使用null 布局时，将窗口设置不可改变大小
            this.setLayout(null);
            // 设置窗口位置和大小
            this.setBounds(300, 300, 460, 330);
            // 设置窗口可见
            this.setVisible(true);
            // 设置不可改变大小
            this.setResizable(false);
            // 设置当点击窗口的关闭按钮时退出
            // 设置此窗体关闭，滚动面板不关闭   this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
            this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            Panelfont1 p1 = new Panelfont1();
            con.add(p1);
            p1.setBounds(15, 15, 175, 60);

            Panelfont2 p2 = new Panelfont2();
            con.add(p2);
            p2.setBounds(195, 15, 250, 60);

            Panelneizi p3 = new Panelneizi();
            con.add(p3);
            p3.setBounds(15, 80, 175, 80);

            Paneldirection p4 = new Paneldirection();
            con.add(p4);
            p4.setBounds(195, 80, 250, 80);

            Panel5 p5 = new Panel5();
            con.add(p5);
            p5.setBounds(15, 155, 430, 70);

            Panel6 p6 = new Panel6();
            con.add(p6);
            p6.setBounds(15, 220, 430, 70);
        }
    }

    class Panelfont1 extends JPanel implements ActionListener{
        public Panelfont1() {
            this.setBorder(BorderFactory.createTitledBorder("滚动字幕的文字大小控制"));//设置 面板边框
            this.setLayout(new GridLayout(1, 2));
            bigfont = new JRadioButton("大字体", false);
            smallfont = new JRadioButton("小字体", true);

            bigfont.addActionListener(this);
            smallfont.addActionListener(this);

            ButtonGroup sexRadioButtonGroup = new ButtonGroup();// 创建一个选按钮组
            sexRadioButtonGroup.add(bigfont);// 将单选按钮对象添加到按钮组中
            sexRadioButtonGroup.add(smallfont);// 将单选按钮对象添加到按钮组中
            //Enumeration<AbstractButton> elements = sexRadioButtonGroup.getElements();// 遍历按钮组中的所有按钮

            this.add(bigfont);
            this.add(smallfont);
        }

        public void actionPerformed(ActionEvent e) {
            if(bigfont.isSelected()){
                fontSize = 50;
            }else {
                fontSize = 36;
            }
            Font font = new Font(fontName,Font.BOLD,fontSize);
            lblShow.setFont(font);
        }
    }

    class Panelfont2 extends JPanel implements ActionListener{

        private String[] strFontName = {"隶书","幼圆","黑体","宋体"};
        public Panelfont2() {
            this.setBorder(BorderFactory.createTitledBorder("滚动字幕字体控制"));
            this.setLayout(new GridLayout(1, 4));
            ButtonGroup bg = new ButtonGroup();
            for(int i = 0 ; i < zt.length ; i++){
                zt[i] = new JToggleButton(strFontName[i]);
                this.add(zt[i]);
                bg.add(zt[i]);
                zt[i].addActionListener(this);
            }
            zt[0].setSelected(true);
        }

        public void actionPerformed(ActionEvent e) {
            int index = 0;
            for(int i = 0 ; i < zt.length;i++){
                if(zt[i].isSelected()){
                    index = i;
                    break;
                }
            }
            fontName = strFontName[index];
            Font font = new Font(fontName,Font.BOLD,fontSize);
            lblShow.setFont(font);
        }
    }

    class Panelneizi extends JPanel implements ActionListener {
        public Panelneizi() {
            this.setBorder(BorderFactory.createTitledBorder("内置字幕"));
            this.setLayout(new GridLayout(2, 4));
            for (int i = 0; i < neizhi.length; i++) {
                // 设置 按钮无色
                neizhi[i].setBackground(SystemColor.info);
                // neizhi[i].setHorizontalAlignment(SwingConstants.CENTER);
                // neizhi[i].setHorizontalTextPosition(SwingConstants.CENTER);
                this.add(neizhi[i]);
                neizhi[i].addActionListener(this);
            }
        }

        public void actionPerformed(ActionEvent e) {

            if (e.getActionCommand() == "1") {
                lblShow.setText("我们都是好孩子！！！");
            }
            if (e.getActionCommand() == "2") {
                lblShow.setText("快乐是自己的，只要你愿意！");
            }
            if (e.getActionCommand() == "3") {
                lblShow.setText("欢迎来到上海！！！");
            }
            if (e.getActionCommand() == "4") {
                lblShow.setText("一毛纸币折成心！！！");
            }
            if (e.getActionCommand() == "5") {
                lblShow.setText("JAVA，很强大！");
            }
            if (e.getActionCommand() == "6") {
                lblShow.setText("不抛弃，不放弃！！！");
            }
            if (e.getActionCommand() == "7") {
                lblShow.setText("路漫漫其修远兮，吾将上下而求索。");
            }
            if (e.getActionCommand() == "8") {
                lblShow.setText("温暖是件奢侈的东西");
            }
        }
    }

    class Paneldirection extends JPanel {
        public Paneldirection() {
            this.setBorder(BorderFactory.createTitledBorder("字幕滚动方向控制"));
            this.setLayout(new GridLayout(2, 2));
            left_right = new JRadioButton("从左向右", false);
            right_left = new JRadioButton("从右向左", true);
            penetrate = new JRadioButton("穿透式左右摆动", false);
            reflect = new JRadioButton("壁面反射式摆动", false);

            left_right.addActionListener(new ActionListener() {// 捕获单选按钮被选中的事件
                public void actionPerformed(ActionEvent e) {
                    gundong.gdfx("left");
                }
            });
            right_left.addActionListener(new ActionListener() {// 捕获单选按钮被选中的事件
                public void actionPerformed(ActionEvent e) {
                    gundong.gdfx("right");
                }
            });
            penetrate.addActionListener(new ActionListener() {// 捕获单选按钮被选中的事件
                public void actionPerformed(ActionEvent e) {
                    gundong.gdfx("penetrate");

                }
            });

            reflect.addActionListener(new ActionListener() {// 捕获单选按钮被选中的事件
                public void actionPerformed(ActionEvent e) {
                    gundong.gdfx("reflect");
                }
            });

            ButtonGroup sexRadioButtonGroup = new ButtonGroup();// 创建一个选按钮组对象
            sexRadioButtonGroup.add(left_right);// 将单选按钮对象添加到按钮组对象中
            sexRadioButtonGroup.add(right_left);
            sexRadioButtonGroup.add(penetrate);
            sexRadioButtonGroup.add(reflect);
            // Enumeration<AbstractButton> elements =sexRadioButtonGroup.getElements();// 遍历按钮组中的所有按钮
            this.add(left_right);
            this.add(right_left);
            this.add(penetrate);
            this.add(reflect);
        }
    }

    class Panel5 extends JPanel implements ChangeListener{
        public Panel5() {
            this.setBorder(BorderFactory.createTitledBorder(""));
            this.setLayout(new GridLayout(2,2));
            JPanel p1 = new JPanel();
            JPanel p2 = new JPanel();
            JPanel p3 = new JPanel();
            JPanel p4 = new JPanel();
            p2.setLayout(new GridLayout(1,2));
            p4.setLayout(new GridLayout(1,2));
            //设置滑块，最小与最大值、初始值
            jSliderX = new JSlider(JSlider.HORIZONTAL,0,200,100);
            //设置滑块最小分割刻度
            //jSliderX.setMinorTickSpacing(10);
            // 设置滑块最大分割刻度
            jSliderX.setMajorTickSpacing(5);
            // 设置显示刻度线
            //jSliderX.setPaintTicks(true);
            //设置滑块当前值
            //jSliderX.setValue(100);
            //反转滑块方向
            jSliderX.setInverted(true);
            //给滑块添加监听器
            jSliderX.addChangeListener(this);

            topbel = new JLabel("前景色：");
            backbel = new JLabel("背景色：");
            speedlabel = new JLabel("滚动速度：");
            colorlabel = new JLabel("背景自动变化：");
            topdrop = new JComboBox();
            backedrop = new JComboBox();
            autocolor = new JCheckBox();

            backedrop.addItem("黑色");
            backedrop.addItem("绿色");
            backedrop.addItem("蓝色");
            backedrop.addItem("青色");
            backedrop.addItem("红色");
            backedrop.addItem("黄色");
            backedrop.setSelectedItem("绿色");

            topdrop.addItem("黑色");
            topdrop.addItem("绿色");
            topdrop.addItem("蓝色");
            topdrop.addItem("青色");
            topdrop.addItem("红色");
            topdrop.addItem("黄色");
            topdrop.setSelectedItem("红色");
			/*
			 * v = new Vector<String>(); v.add("黑色"); v.add("绿色"); v.add("蓝色");
			 * v.add("青色"); v.add("红色"); v.add("黄色"); topdrop = new
			 * JComboBox(v);
			 */// Vector  方法 向量组

            p1.add(topbel);
            p1.add(topdrop);
            p2.add(speedlabel);
            p2.add(jSliderX);
            p3.add(backbel);
            p3.add(backedrop);
            p4.add(colorlabel);
            p4.add(autocolor);

            this.add(p1);
            this.add(p2);
            this.add(p3);
            this.add(p4);

            topdrop.addItemListener(new ItemListener(){
                public void itemStateChanged(ItemEvent evt) {
                    if(evt.getStateChange() == ItemEvent.SELECTED){
                        try{
                            String s = evt.getItem().toString();//选中的值
                            lblShow.setForeground(Color.black);
                            if(s=="黑色"){lblShow.setForeground(Color.black);}
                            else if(s=="绿色"){lblShow.setForeground(Color.green);}
                            else if(s=="蓝色"){lblShow.setForeground(Color.blue);}
                            else if(s=="青色"){lblShow.setForeground(Color.cyan);}
                            else if(s=="红色"){lblShow.setForeground(Color.red);}
                            else if(s=="黄色"){lblShow.setForeground(Color.yellow);}
                        }catch (Exception e){
                        }
                    }
                }
            });
            backedrop.addItemListener(new ItemListener(){
                public void itemStateChanged(ItemEvent evt) {
                    if(evt.getStateChange() == ItemEvent.SELECTED){
                        try{
                            String s = evt.getItem().toString();//选中的值
                            if(s=="黑色"){panels.setBackground(Color.black);}
                            else if(s=="绿色"){panels.setBackground(Color.green);}
                            else if(s=="蓝色"){panels.setBackground(Color.blue);}
                            else if(s=="青色"){panels.setBackground(Color.cyan);}
                            else if(s=="红色"){panels.setBackground(Color.red);}
                            else if(s=="黄色"){panels.setBackground(Color.yellow);}
                        }catch (Exception e){
                        }
                    }
                }
            });
            autocolor.addItemListener(new ItemListener(){
                public void itemStateChanged(ItemEvent evt) {
                    if(evt.getStateChange() == ItemEvent.SELECTED){
                        try{
                            Thread sj = new Thread(new Thread(yansesuiji));
                            yansesuiji.setsuiji(true);
                            sj.start();
                        }catch (Exception e){
                        }
                    }else{yansesuiji.setsuiji(false);}
                }
            });
        }
        public void stateChanged(ChangeEvent e) {
            //确定滑块速度
        }
    }
    class Panel6 extends JPanel implements ActionListener, TextListener {
        public Panel6() {
            this.setBorder(BorderFactory.createTitledBorder(""));
            this.setLayout(new GridLayout(2, 1));
            JPanel p5 = new JPanel();
            txtfield = new TextField("请输入要滚动的字幕：");
            startbtn = new JButton("开始");
            stopbtn = new JButton("停止");
            resetbtn = new JButton("恢复");
            hidebtn = new JButton("隐藏");
            exitbtn = new JButton("退出");

            p5.add(startbtn);
            p5.add(stopbtn);
            p5.add(resetbtn);
            p5.add(hidebtn);
            p5.add(exitbtn);
            p5.setLayout(new GridLayout(1, 5));
            this.add(txtfield);
            this.add(p5);
            startbtn.addActionListener(this);
            stopbtn.addActionListener(this);
            resetbtn.addActionListener(this);
            hidebtn.addActionListener(this);
            exitbtn.addActionListener(this);
            txtfield.addTextListener(this);
        }

        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == exitbtn) {
                System.exit(0);
            }

            if (e.getSource() == startbtn) {
                Thread you = new Thread(new Thread(gundong));
                gundong.setIsMove(true);
                you.start();
            }
            if (e.getSource() == stopbtn) {
                gundong.setIsMove(false);
                yansesuiji.setsuiji(false);
            }
            //恢复功能
            if (e.getSource() == resetbtn) {
                yansesuiji.setsuiji(false);
                gundong.setIsMove(false);
                gundong.gdfx("right");
                //滚动面板显示
                show.setVisible(true);
                smallfont.setSelected(true);
                zt[0].setSelected(true);
                right_left.setSelected(true);
                autocolor.setSelected(false);
                panels.setBackground(Color.green);
                backedrop.setSelectedItem("绿色");
                topdrop.setSelectedItem("红色");
                fontName = "隶书";
                fontSize = 36;
                jSliderX.setValue(100);
                lblShow.setFont(new Font(fontName, Font.BOLD, fontSize));
                txtfield.setText("请输入要滚动的字幕：");
            }
            if (e.getSource() == hidebtn) {
                //滚动面板关闭
                show.setVisible(false);
            }
        }

        public void textValueChanged(TextEvent e) {
            lblShow.setText(txtfield.getText());
        }

    }
    //线程    移动
    Gundong gundong = new Gundong();

    class Gundong implements Runnable {
        private boolean gd = true;
        private String fx = "right";

        public void setIsMove(boolean b) {
            gd = b;
        }
        // 滚动方向
        public void gdfx(String s){
            fx = s;
        }
        public void run() {
            // 设定条件为真时执行
            while (gd == true) {
                if(fx=="right"){
                    Point p = lblShow.getLocation();
                    p.x -= 5;
                    lblShow.setLocation(p);
                    if (p.x < -lblShow.getWidth()) {
                        p.x = panels.getWidth();
                        lblShow.setLocation(p);
                    }
                }

                if(fx=="left"){
                    Point p = lblShow.getLocation();
                    p.x += 5;
                    lblShow.setLocation(p);
                    if(p.x >= panels.getWidth() ){
                        p.x = -lblShow.getWidth();
                        lblShow.setLocation(p);
                    }
                }
                if(fx=="penetrate"){

                }
                if(fx=="reflect"){

                }
                try {
                    Thread.sleep(jSliderX.getValue());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        public synchronized void shutDownThread() {
            gd = false;
        }

        public synchronized boolean isShutDown() {
            return gd;
        }
    }
    Yanse yansesuiji = new Yanse();
    class Yanse implements Runnable {
        private boolean suiji = true;
        public void setsuiji(boolean b){
            suiji= b;
        }
        public void run() {
            while (suiji == true) {
                Calendar c = Calendar.getInstance();// 调用 系统时间 进行每5秒自动设置背景颜色
                int s = c.get(Calendar.SECOND);
                if(s<= 5 ){panels.setBackground(Color.black);}
                else if((s>5 && s<=10 ) || (s>30 && s<=35)){panels.setBackground(Color.blue);}
                else if((s>10 && s<=15) || (s>35 && s<=40)){panels.setBackground(Color.cyan);}
                else if((s>15 && s<=20) || (s>45 && s<=50)){panels.setBackground(Color.red);}
                else if((s>20 && s<=25) || (s>50 && s<=55)){panels.setBackground(Color.yellow);}
                else if((s>25 && s<=30) || (s>55 && s<=60)){panels.setBackground(Color.green);}
                // 异常的处理
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {

                    e.printStackTrace();
                }

            }
        }
        public synchronized void shutDownThreadp() {
            suiji = false;
        }

        public synchronized boolean isShutDownp() {
            return suiji;
        }
    }


}
