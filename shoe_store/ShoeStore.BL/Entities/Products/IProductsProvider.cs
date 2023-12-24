using ShoeStore.BL.Entities.Products.Entities;

namespace ShoeStore.BL.Entities.Products
{
    public interface IProductsProvider
    {
        IEnumerable<ProductModel> GetProducts(ProductModelFilter modelFilter = null);
        ProductModel GetProductInfo(Guid id);
    }
}
