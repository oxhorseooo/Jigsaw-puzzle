package com.heima.ui;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

public class GameJFrame extends JFrame implements KeyListener, ActionListener {
    int[] data = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15};
    int[][] win = {
            {1, 2, 3, 4},
            {5, 6, 7, 8},
            {9, 10, 11, 12},
            {13, 14, 15, 16},
    };
    String path = "game/image/animal/animal1/";
    //记录空白图片的位置（坐标）
    int x = 0, y = 0;
    int[][] data2 = new int[4][4];
    //步数
    int step = 0;
    //创建选项下的对象条目
    JMenuItem replay_Item = new JMenuItem("重新游戏");
    JMenuItem close_Item = new JMenuItem("关闭游戏");
    JMenuItem tip_Item = new JMenuItem("给stars");
    JMenu changImage = new JMenu("更换图片");
    JMenuItem girls=new JMenuItem("美女");
    JMenuItem animals=new JMenuItem("动物");
    JMenuItem sports=new JMenuItem("运动");
    //与游戏有关的代码
    public GameJFrame() {
        inital_data();
        inital_Farme();
        inital_Menu();
        //初始化图片
        inital_image();
        //显示界面
        this.setVisible(true);
    }

    private boolean victory() {
        for (int i = 0; i < data2.length; i++) {
            for (int j = 0; j < data2[i].length; j++) {
                if (win[i][j] != data2[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }

    private void inital_data() {
        //打乱数据顺序并存入二维数组中
        //打乱一维数组
        for (int i = 0; i < data.length; i++) {
            Random random = new Random();
            int temp = data[i];
            int index = random.nextInt(data.length);
            data[i] = data[index];
            data[index] = temp;
        }

        for (int i = 0; i < data.length; i++) {
            if (data[i] == 0) {
                x = i / 4;
                y = i % 4;
            }
            data2[i / 4][i % 4] = data[i];
        }
    }

    private void inital_image() {
        //清空所有图片
        this.getContentPane().removeAll();
        if (victory()) {
            ImageIcon winImageIcon = new ImageIcon("game/image/win.png");
            JLabel winJLable = new JLabel(winImageIcon);
            winJLable.setBounds(203, 283, 197, 197);
            this.getContentPane().add(winJLable);
        }
        JLabel stepJLabel = new JLabel("步数:" + step);
        stepJLabel.setBounds(50, 30, 100, 20);
        this.getContentPane().add(stepJLabel);
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                int num = data2[i][j];
                //创建图片对象imageIcon
                ImageIcon icon = new ImageIcon(path + num + ".jpg");
                //创建JLable对象（管理容器）
                JLabel jLabel = new JLabel(icon);
                jLabel.setBounds(105 * j + 83, 105 * i + 134, 105, 105);
                //获取隐藏容器，并将jLabel添加到隐藏容器中
                //使图片凹下去，更加美观
                jLabel.setBorder(new BevelBorder(1));
                this.getContentPane().add(jLabel);
            }
        }
        //加载背景图片
        ImageIcon imageIcon = new ImageIcon("game/image/background.png");
        JLabel background = new JLabel(imageIcon);
        background.setBounds(40, 40, 508, 560);
        this.getContentPane().add(background);
        //刷新界面
        this.getContentPane().repaint();
    }

    private void inital_Menu() {
        //初始化菜单
        //创建菜单对象
        JMenuBar jMenuBar = new JMenuBar();
        //创建选项的对象（功能，隐藏彩蛋）
        JMenu fuctionMenu = new JMenu("功能");
        JMenu ScerttMenu = new JMenu("秘密基地");

        //讲选项下的添加到菜单中
        fuctionMenu.add(replay_Item);
        fuctionMenu.add(changImage);
        fuctionMenu.add(close_Item);
        ScerttMenu.add(tip_Item);

        changImage.add(girls);
        changImage.add(animals);
        changImage.add(sports);
        //给条目绑定事件
        replay_Item.addActionListener(this);
        close_Item.addActionListener(this);
        tip_Item.addActionListener(this);
        girls.addActionListener(this);
        animals.addActionListener(this);
        sports.addActionListener(this);

        jMenuBar.add(fuctionMenu);
        jMenuBar.add(ScerttMenu);
        //给整个界面设置菜单
        this.setJMenuBar(jMenuBar);
    }

    private void inital_Farme() {
        this.setSize(603, 680);
        this.setTitle("拼图游戏");
        this.setAlwaysOnTop(true);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(3);
        //取消默认居中位置
        this.setLayout(null);
        //给界面添加键盘监听事件
        this.addKeyListener(this);
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        //按下按键A不松开就会展示完整页面
        int keyCode = e.getKeyCode();
        if (keyCode == 65) {
            //清空所有图片
            this.getContentPane().removeAll();
            //展示完整图片
            ImageIcon all = new ImageIcon(path + "all.jpg");
            JLabel Jall = new JLabel(all);
            Jall.setBounds(83, 134, 420, 420);
            this.getContentPane().add(Jall);

            //加载背景图片
            ImageIcon imageIcon = new ImageIcon("game/image/background.png");
            JLabel background = new JLabel(imageIcon);
            background.setBounds(40, 40, 508, 560);
            this.getContentPane().add(background);
            //刷新界面
            this.getContentPane().repaint();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (victory()) {
            return;
        }
        int keyCode = e.getKeyCode();
        System.out.println(keyCode);
        if (keyCode == 37) {
            step++;
            System.out.println("向左");
            if (!(y + 1 <= 3 && y + 1 >= 0))
                return;
            data2[x][y] = data2[x][y + 1];
            data2[x][y + 1] = 0;
            y++;
            //交换图片位置，重新加载图片

            inital_image();
        } else if (keyCode == 38) {
            step++;
            System.out.println("向上");
            if (!(x + 1 <= 3 && x + 1 >= 0))
                return;
            data2[x][y] = data2[x + 1][y];
            data2[x + 1][y] = 0;
            x++;
            inital_image();
        } else if (keyCode == 39) {
            step++;
            System.out.println("向右");
            //交换图片位置，重新加载图片
            if (!(y - 1 <= 3 && y - 1 >= 0))
                return;
            data2[x][y] = data2[x][y - 1];
            data2[x][y - 1] = 0;
            y--;
            inital_image();
        } else if (keyCode == 40) {
            step++;
            System.out.println("向下");
            //交换图片位置，重新加载图片
            if (!(x - 1 <= 3 && x - 1 >= 0))
                return;
            data2[x][y] = data2[x - 1][y];
            data2[x - 1][y] = 0;
            x--;

            //交换图片位置，重新加载图片
            inital_image();
        } else if (keyCode == 65) {
            //松下按键A
            inital_image();
        } else if (keyCode == 87) {
            data2 = new int[][]{
                    {1, 2, 3, 4},
                    {5, 6, 7, 8},
                    {9, 10, 11, 12},
                    {13, 14, 15, 16},
            };
            inital_image();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        if (source == replay_Item) {
            System.out.println("重新游戏");
            step = 0;
            inital_data();
            inital_image();

        } else if (source == close_Item) {
            System.out.println("关闭游戏");
            //关闭程序
            System.exit(0);
            //关闭当前界面
            this.setVisible(false);

        } else if (source == tip_Item) {
            System.out.println("给星星");
            //创建谈弹窗对象
            JDialog stars = new JDialog();
            JLabel jLabel=new JLabel(new ImageIcon("game/image/star.jpg"));
            jLabel.setBounds(0,0,258,258);
            //把图片添加到弹窗中
            stars.getContentPane().add(jLabel);
            //设置弹窗大小
            stars.setSize(344,344);
            //弹窗置顶
            stars.setAlwaysOnTop(true);
            //弹窗居中
            stars.setLocationRelativeTo(null);
            //弹窗不关闭无法进行下一步操作
            stars.setModal(true);
            //显示弹窗
            stars.setVisible(true);

        } else if (source==girls) {
            System.out.println("美女");
            step=0;
            Random random=new Random();
            int temp=random.nextInt(1,6);
            path = "game/image/girl/girl"+ temp+"/";
            inital_data();
            inital_image();
        } else if (source==animals) {
            System.out.println("动物");
            step=0;
            Random random=new Random();
            int temp=random.nextInt(1,6);
            path = "game/image/animal/animal"+ temp+"/";
            inital_data();
            inital_image();
        }else if (source==sports){
            System.out.println("运动");
            step=0;
            Random random=new Random();
            int temp=random.nextInt(1,6);
            path = "game/image/sport/sport"+ temp+"/";
            inital_data();
            inital_image();
        }
    }
}