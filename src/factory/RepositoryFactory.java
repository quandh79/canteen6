package factory;

import dao.impls.ProductsRepository;
import dao.interfaces.IRepository;
import enums.RepositoryType;

public class RepositoryFactory {
    private RepositoryFactory() {
    }

    public static IRepository createRepository(RepositoryType type) {
        if (type == RepositoryType.PRODUCT) {
            return new ProductsRepository();
        }
        throw new IllegalArgumentException("Error");
    }

}
