package com.lrw.test;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ImageTest {

    public static void main(String[] args) throws IOException {
        String filename = "C:\\Users\\Administrator\\Desktop\\pic\\2cd2c534-a314-44b5-b628-904b1e101371.jpg";// separator��File���һ������,����java��ʷ���������ΪСд
        File file = new File(filename);
        BufferedImage bi = ImageIO.read(file);
        // ��ȡ��ǰͼƬ�ĸ�,��,ARGB
        int h = bi.getHeight();
        int w = bi.getWidth();
        int rgb = bi.getRGB(0, 0);
        int arr[][] = new int[w][h];

        // ��ȡͼƬÿһ���ص�ĻҶ�ֵ
        for (int i = 0; i < w; i++) {
            for (int j = 0; j < h; j++) {
                // getRGB()����Ĭ�ϵ�RGB��ɫģ��(ʮ����)
                arr[i][j] = getImageRgb(bi.getRGB(i, j));//�õ�ĻҶ�ֵ
            }

        }
        
        BufferedImage bufferedImage=new BufferedImage(w, h, BufferedImage.TYPE_BYTE_BINARY);//  ����һ������ΪԤ����ͼ������֮һ�� BufferedImage��TYPE_BYTE_BINARY����ʾһ����͸�������ֽڴ���� 1��2 �� 4 λͼ�񡣣�
        int FZ=130;
        for (int i = 0; i < w; i++) {
            for (int j = 0; j < h; j++) {
                if(getGray(arr,i,j,w,h)>FZ){
                    int black=new Color(255,255,255).getRGB();
                    bufferedImage.setRGB(i, j, black);
                }else{
                    int white=new Color(0,0,0).getRGB();
                    bufferedImage.setRGB(i, j, white);
                }
            }
            
        }
         ImageIO.write(bufferedImage, "jpg", new File("C:\\Users\\Administrator\\Desktop\\pic\\new123.jpg"));
    }

    private static int getImageRgb(int i) {
        String argb = Integer.toHexString(i);// ��ʮ���Ƶ���ɫֵתΪʮ������
        // argb�ֱ����͸��,��,��,�� �ֱ�ռ16����2λ
        int r = Integer.parseInt(argb.substring(2, 4),16);//�������Ϊʹ�ý���
        int g = Integer.parseInt(argb.substring(4, 6),16);
        int b = Integer.parseInt(argb.substring(6, 8),16);
        int result=(int)((r+g+b)/3);
        return result;
    }
    
    
    
    //�Լ�����Χ8���Ҷ�ֵ�ٳ���9���������ԻҶ�ֵ 
     public static int  getGray(int gray[][], int x, int y, int w, int h)  
        {  
            int rs = gray[x][y]  
                            + (x == 0 ? 255 : gray[x - 1][y])  
                            + (x == 0 || y == 0 ? 255 : gray[x - 1][y - 1])  
                            + (x == 0 || y == h - 1 ? 255 : gray[x - 1][y + 1])  
                            + (y == 0 ? 255 : gray[x][y - 1])  
                            + (y == h - 1 ? 255 : gray[x][y + 1])  
                            + (x == w - 1 ? 255 : gray[x + 1][ y])  
                            + (x == w - 1 || y == 0 ? 255 : gray[x + 1][y - 1])  
                            + (x == w - 1 || y == h - 1 ? 255 : gray[x + 1][y + 1]);  
            return rs / 9;  
        }  
}
