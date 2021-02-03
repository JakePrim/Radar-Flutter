package confirm;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.support.CorrelationData;

/**
 * @program: spring-rabbitmq-producer
 * @Description: 消息确认处理
 * @author: sufulu
 * @version: 1.0.0
 * @create: 2021-02-02 23:48
 * @PackageName: confirm
 * @ClassName: MessageConfirm.java
 **/
public class MessageConfirm implements RabbitTemplate.ConfirmCallback {
    /***
     *
     * @param correlationData 消息相关的数据对象，封装了消息的唯一id
     * @param b 消息是否确认成功
     * @param s 异常信息
     */
    @Override
    public void confirm(CorrelationData correlationData, boolean b, String s) {
        if (b) {
            System.out.println("消息确认成功");
        } else {
            System.out.println("消息确认失败xxxx:" + s);
            //补发 失败的消息，如果本条消息一定要发送到队列中，例如下订单消息，我们可以采用补发
            //1. 采用递归 限制递归的次数

            //2. redis+定时任务(JDK的timer，或者定时任务框架Quartz)
        }
    }
}
