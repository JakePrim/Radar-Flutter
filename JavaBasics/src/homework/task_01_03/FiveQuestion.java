package homework.task_01_03;

import java.util.*;

/**
 * 使用集合实现斗地主游戏的部分功能，要求如下：
 * <p>
 * （1）首先准备 54 张扑克牌并打乱顺序。
 * <p>
 * （2）由三个玩家交替摸牌，每人 17 张扑克牌，最后三张留作底牌。
 * <p>
 * （3）查看三个玩家手中的扑克牌和底牌。
 * <p>
 * （4）其中玩家手中的扑克牌需要按照大小顺序打印，规则如下：
 * <p>
 * 手中扑克牌从大到小的摆放顺序：大王,小王,2,A,K,Q,J,10,9,8,7,6,5,4,3
 */
public class FiveQuestion {
    //存储每个玩家的牌
    private Map<String, TreeSet<Squeezer>> playerMap;

    private List<Squeezer> results;

    private Random random = new Random();


    public FiveQuestion() {
        playerMap = new LinkedHashMap<>();
        results = new ArrayList<>();
        //准备54张扑克牌 打乱顺序
        shuffle(getCards());
    }

    /**
     * 打乱扑克牌的顺序
     * @param cards
     * @return
     */
    public List<Squeezer> shuffle(List<Squeezer> cards){
        for (int i = 0; i < 54; i++) {
            Squeezer squeezer = cards.get(i);
            int s = results.size() + 1;
            int index = random.nextInt(s);
            results.add(index,squeezer);
        }
        return results;
    }

    /**
     * 获取54扑克牌
     * @return List<Squeezer>
     */
    public List<Squeezer> getCards(){
        List<String> colors = new ArrayList();//扑克牌的花色
        List<String> number = new ArrayList();//扑克牌的大小数
        List<Squeezer> cards = new ArrayList();//扑克牌列表
        //设置扑克牌的花色 通过 A-D来进行排序
        colors.add("A");//红桃
        colors.add("B");//黑桃
        colors.add("C");//方片
        colors.add("D");//梅花

        //设置扑克牌的牌面数
        number.add("A");
        for (int i = 2; i < 11; i++) {
            number.add(i+"");
        }
        number.add("J");
        number.add("Q");
        number.add("K");

        for (int i = 0; i < 13; i++) {
            for (int j = 0; j < 4; j++) {
                String color =  colors.get(j);
                String name =  number.get(i);
                cards.add(new Squeezer(name,color,i));
            }
        }
        cards.add(new Squeezer("小王","小王",13));
        cards.add(new Squeezer("大王","大王",14));
        return cards;
    }

    /**
     * 开始，三个玩家交替摸牌
     */
    public void play(){
        int curPlayer = 0;//当前玩家摸牌
        playerMap.put("0",new TreeSet<Squeezer>());
        playerMap.put("1",new TreeSet<Squeezer>());
        playerMap.put("2",new TreeSet<Squeezer>());
        //三个玩家轮流摸牌
        for (int i = 0; i < results.size() - 3; i++) {
            if (curPlayer > 2) {
                curPlayer = 0;
            }
            playerMap.get(String.valueOf(curPlayer)).add(results.get(i));
            curPlayer++;
        }
        Set<Map.Entry<String, TreeSet<Squeezer>>> entries = playerMap.entrySet();
        for (Map.Entry<String, TreeSet<Squeezer>> entry : entries) {
            System.out.println("玩家:"+entry.getKey()+" 的手牌：");
            for (Squeezer squeezer : entry.getValue()) {
                System.out.println(squeezer);
            }
        }
        System.out.println("底牌:");
        for (int i = results.size() - 1; i >= results.size() - 3 ; i--) {
            System.out.println(results.get(i));
        }
    }

    public static void main(String[] args) {
        FiveQuestion fiveQuestion = new FiveQuestion();
        fiveQuestion.play();
    }

}
