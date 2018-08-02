//今天对hibernate 的HQL语句进行了学习，总结如下几点：
//完成的功能是：通过HQL，通过User表和DtManager表中的mobile字段来查询对应的dtmanager_apply的数据

//1.创建HQL连接：
val cirteria = currentSession.createCirteria(DtManagetApply::class.java)
//2.创建cirteria的脱离连接：
val dmcirteria = Detachedcirteria.forclass(DtManager::class.java,"dm")
//3.创建User表的对象，取别名：
dmcirteria.createAlias(DtManager::getUser.fieldName(),"user")
//4.开始添加查询语句：
dmcirteria.add(Restrictions.eq("user.${User.getuserType.fieldName()},User_UserType.DtManager.value"))
          .add(Restrictions.or(Restrictions.like(DtManager.getMobile.fieldName(),it,matchMode.AnyWhere),Restrictions.like("user.${User.getMobile.fieldName()}",it,matchMode.AnyWhere)))
//5.获取上面获取数据的id作为突出查询列
dmcirteria.setProjection(Projections.property("dm.${ID}"))
//6.给dtmanagerapply添加子查询
cirteria.add(Subqueries.propertyin(DtManager::getDtManager.fieldName(),dmcirteria))
                                 
                                 
