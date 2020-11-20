package enum_demo;

public enum DirectionEnum implements DirectionInterface{
    UP("向上"){
        @Override
        public void show() {
            System.out.println("贪吃蛇向上移动了一下");
        }
    }, DOWN("向下"){
        @Override
        public void show() {
            System.out.println("贪吃蛇向下移动了一下");
        }
    }, LEFT("向左"){
        @Override
        public void show() {
            System.out.println("贪吃蛇向左移动了一下");
        }
    }, RIGHT("向右"){
        @Override
        public void show() {
            System.out.println("贪吃蛇向右移动了一下");
        }
    };


    private final String desc;

    DirectionEnum(String desc) {
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }

//    @Override
//    public void show() {
//
//    }
}
