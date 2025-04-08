package mrrock.com.wishlistminiproject.Repository;


import java.io.Serializable;

public interface CrudOperations<T,ID extends Serializable> {

    public T findById(ID id);


}
