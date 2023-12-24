using ShoeStore.BL.Entities.Products.Entities;

namespace ShoeStore.BL.Entities.Products
{
    public interface IProductsManager
    {
        ProductModel CreateProduct(CreateProductModel model);
        void DeleteProduct(Guid id);
        ProductModel UpdateProduct(Guid id, UpdateProductModel model);
    }
}
