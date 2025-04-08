package mrrock.com.wishlistminiproject.Repository;


import java.io.Serializable;

public interface CrudOperations<T,ID extends Serializable> {

    T findById(ID id);


}
