package rumen.web.dao;


import rumen.web.entity.BookNodeEntity;

//@Repository
@Deprecated
public interface BookNodeDao  {
    BookNodeEntity findByFsid(String fsId);
}
