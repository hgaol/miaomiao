# miaomiao

miaomiao是一个很俗气的秒杀架构实践 :laughing: 

其架构为 Spring + MySQL + Redis + RabbitMQ，另外还有隐藏秒杀接口，图形验证码，接口限流防刷等优化手段。

接下来是逐步几个阶段循序渐进优化的结果。



## 过程

### Step 1 未做任何优化

秒杀接口的qps为5789/s

![](https://cdn.nlark.com/yuque/0/2018/png/135791/1543939304567-f3760160-8821-4af5-b7ce-3c2d3aac3866.png)

同时出现了超卖现象

![img](https://cdn.nlark.com/yuque/0/2018/png/135791/1543939083420-62ffc9bd-d987-4135-afc3-fbc52820b2aa.png)

### Step 2 对象缓存

主要将秒杀商品放入redis缓存，并在数据库层加限制（stock > 0)，添加唯一索引（每人每件商品只能秒杀一次），解决了超卖现象，且qps有些许提升

![img](https://cdn.nlark.com/yuque/0/2018/png/135791/1544012854535-6848584b-b915-4133-83d8-f294f07d4894.png)

![img](https://cdn.nlark.com/yuque/0/2018/png/135791/1544149110480-1d28c239-93e4-4e42-9d33-37888090852a.png)

### Step 3 加入MQ

Redis预减库存减少数据库访问；内存标记减少redis访问；请求先入队列缓冲，异步下单，请求出队，生成订单，减少库存。

此时qps达到了11706，有了很大的进步。

![img](https://cdn.nlark.com/yuque/0/2018/png/135791/1544013240537-b5a670fe-7fd0-48ff-96ce-0c984bbe76ad.png)

### Step 4 安全优化

1. 秒杀接口地址隐藏：先请求验证码，验证码通过返回秒杀地址

2. 数学公式验证码

3. 接口限流防刷