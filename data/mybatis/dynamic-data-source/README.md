# dynamic-data-source
***
基于multi-data-source 但多个数据源实例由自己管理
+ 使用CommandLineRunner在springboot启动后初始化已有实例
+ 数据库或删除数据源后调用DataSourceRunner.dataSourceTask刷新即可