package rumen.web.dao;


import geek.me.javaapi.entity.BookNodeEntity;

//@Repository
@Deprecated
public interface BookNodeDao  {
    BookNodeEntity findByFsid(String fsId);
}
