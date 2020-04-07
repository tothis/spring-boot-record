# hibernate-jpa

> https://docs.spring.io/spring-data/jpa/docs/2.0.9.RELEASE/reference/html/#jpa.query-methods

## Cascade级联关系
订单和订单中商品是一对多关系 订单被删除时 订单所关联商品不能被删除

级联关系类型
CascadeType.PERSIST 级联保存 对应EntityManager的persist方法 当保存Order时 会级联保存Order对应OrderItem数据
CascadeType.REMOVE 级联删除 对应EntityManager的remove方法 当删除Order时会先级联删除OrderItem数据 再删除Order
CascadeType.MERGE 级联更新 对应EntityManager的merge方法 当Order数据改变 会相应更新OrderItem中的Order数据
CascadeType.REFRESH 级联刷新 对应EntityManager的refresh(object)方法 获取order时也重新获取最新的OrderItem
CascadeType.DETACH 级联托管 对应EntityManager的detach方法 删除Order 但有外键无法删除 拥有此级联权限 会撤销相关外键关联
CascadeType.ALL 包含所有级联属性

***

## 一方关联多个多方
一个人可有多个邮箱 一个人可有多个事件 搜索人和其相关事件和邮件 jpa默认采用join抓取策略 获取人多个关联对象 会报cannot simultaneously fetch multiple bags异常

当持久框架抓取一方对象时 同时加载多方对象放进容器中 多方又可能关联其它对象 hibernate jpa 默认最高抓取深度含本身级为四级(有个属性配置是0~3)
若多方(第二级)存在重复值 则第三级中抓取的值就无法映射 此时抛出`无法同时加载多个包异常`

解决方案
1. 将List变为Set
2. fetch=FetchType.LAZY 即让`人`只关联一个`多方`
3. @Fetch(FetchMode.SUBSELECT) 额外生成`select ... in`查询后自动合并