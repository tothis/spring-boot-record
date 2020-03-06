>dozer可以通过xml或注解配置不同名称实体转化 但实际大部分仅对同名字段进行拷贝 可使用cglib BeanCopiers转化

操作类型|调用100万次|描述
get/set|20ms|手动调用
BeanCopiers|9ms|修改字节码
BeanUtils|2232ms|反射
Dozer|19818ms|反射
PropertyUtils|23971ms|反射