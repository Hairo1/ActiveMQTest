package com.hairo.mq.holloworid;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;
import java.util.concurrent.TimeUnit;

/**
 * 项目名称： ActiveMQDemo
 * 作 者   ： Hairo
 * 创建时间: 2018/11/8 19:49
 * 作用描述:
 * 消息发送
 */

public class Sender {

    public static void main(String[] args) throws JMSException, InterruptedException {
        //建立工厂对象获取连接,需要输入用户名，密码,以及连接的地址，默认端口为:"tcp://localhost:61616"
        ConnectionFactory factory = new ActiveMQConnectionFactory(
                ActiveMQConnectionFactory.DEFAULT_USER,//默认admin
                ActiveMQConnectionFactory.DEFAULT_PASSWORD,//默认admin
                ActiveMQConnectionFactory.DEFAULT_BROKER_URL
        );
        Connection connection = factory.createConnection();
        //conn默认是关闭的,所以需要手动打开
        connection.start();
        //创建会话,参数1代表是否开启事务,默认true,参数2代表签收模式,一般设置为自动签收
        Session session = connection.createSession(Boolean.FALSE, Session.AUTO_ACKNOWLEDGE);
        //通过session创建Destination对象,指的是一个客户端用来指定生产消息目标和消费消息来源的对象,在PTP模式中,Destination称为Queue即队列
        Destination destination = session.createQueue("hairo");
        //通过session创建MessageProducer对象(消息产生者),消息接收者MessageConsumer
        MessageProducer producer = session.createProducer(null);

        for(int i = 0; i < 30; i++){
            TextMessage textMessage = session.createTextMessage("消息:" + (i+1));

            //参数一:发送给谁,参数二:发送的具体数据,参数三:传送的模式，参数四:优先级,参数五：过期时间
            producer.send(destination,textMessage);

            TimeUnit.SECONDS.sleep(1);
        }

        //关闭conn,释放资源
        if(connection!=null){  connection.close();}


    }
}
