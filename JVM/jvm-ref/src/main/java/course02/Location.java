package course02;

public class Location {
    private String city;
    private String region;
    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public void mode1(){
        String str ="abc";
    }
    public void mode2(){
        String str =new String("abc");
    }
    public void mode3(){
        Location location = new Location();
        location.setCity("深圳");
        location.setRegion("南山");
    }
    public void mode4(){
        String str2= "ab" + "cd" + "ef";//3个对象。效率最低。java -》class- java
    }
    public void mode5(){
//        String str = "abcdef";
//        for(int i=0; i<1000; i++) {
//            str = str + i;
//        }
        //优化
        String str = "abcdef";
        for(int i=0; i<1000; i++) {
            str = (new StringBuilder(String.valueOf(str)).append(i).toString());
        }
    }
    public void mode6(){
        //去字符串常量池找到是否有等于该字符串的对象，如果有，直接返回对象的引用。
        String a =new String("king").intern();// new 对象、king 字符常量池创建
        String b = new String("king").intern();// b ==a。
        if(a==b) {
            System.out.print("a==b");
        }else{
            System.out.print("a!=b");
        }
    }

    public static void main(String[] args) {
        Location location = new Location();
        location.mode6();

        String str1="abc";//常量池中的引用
        String str2 = new String("abc");//str2是在堆中的
        String str3 = str2.intern();//常量池中的引用
        System.out.println(str1==str2);//false
        System.out.println(str2==str3);//false
        System.out.println(str1==str3);//true

    }

}
