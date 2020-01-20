# hibernate-jpa

Cascade级联关系
用户和用户收货地址是一对多关系 用户被删除时 用户所有收货地址应一并删除
订单和订单中商品是一对多关系 订单被删除时 订单所关联商品不能被删除

级联关系类型
CascadeType.REFRESH 级联刷新 当多个用户同时作操作一个实体 为了用户取到的数据是实时的 在用实体中的数据之前就可以调用一下refresh()方法
CascadeType.REMOVE 级联删除 当调用remove()方法删除Order实体时会先级联删除OrderItem的相关数据
CascadeType.MERGE 级联更新 当调用了Merge()方法 如果Order中的数据改变了会相应的更新OrderItem中的数据
CascadeType.ALL 包含以上四种级联属性
CascadeType.PERSIST 级联保存 当调用了Persist()方法 会级联保存相应的数据