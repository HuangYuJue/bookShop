package org.book.myssm.ioc;

public interface BeanFactory {
    //通过map.get(id)获取到Object(对象)
    public Object getBean(String id);
}
