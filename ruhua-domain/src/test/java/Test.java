import java.io.UnsupportedEncodingException;

/**
 * Created with IntelliJ IDEA.
 * User: lijing3
 * Date: 14-12-25
 * Time: 上午9:53
 * To change this template use File | Settings | File Templates.
 */
public class Test {

    public static void main(String[] args) throws UnsupportedEncodingException {
        /*long item = 12;
        Long storeId = 12L;
        //是否在新门店 在新门店 数量从0开始计算
        if(item > 0 && storeId > 0 && storeId != item) {
            System.out.println("是同一门店");
        }*/

        String ori = "宸濊彍锛�閲嶅锛墊浜ゆ槗閲戦涓嶆纭�";
        System.out.println(new String(ori.getBytes("gbk"),"UTF-8"));
    }
}
