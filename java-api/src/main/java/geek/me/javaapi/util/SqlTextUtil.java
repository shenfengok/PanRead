package geek.me.javaapi.util;

import geek.me.javaapi.entity.node.BookEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.lang.reflect.Field;
import java.util.List;

public class SqlTextUtil<T> {

    public static String langCode ="en"; //"zh-hans" "en"

    public  String reflect(PagingAndSortingRepository  repository){
        StringBuilder sb = new StringBuilder();
        for(int i = 1;i< 10000;i ++){
            PageRequest request = PageRequest.of(i,20);
            Page<T> entitys = repository.findAll(request);

            if(entitys.getContent().size() <=0){
                break;
            }
            for(T entity : entitys){
              sb.append(  reflect(entity));
            }
        }
        return sb.toString();
    }

    private String reflect(T t){
        try{
            StringBuilder sb = new StringBuilder();
            sb.append("{");
            Class cls = t.getClass();
            Field[] fields = cls.getDeclaredFields();
            for(Field f : fields){
                f.setAccessible(true);
                sb.append("\"").append(f.getName()).append("\":\"").append(f.get(t)).append("\",");
            }
            return sb.toString();
        } catch(Exception e){
            return "";
        }
    }
}
