using ShoeStore.BL.Entities.Products.Entities;
using ShoeStore.DataAccess.Entities;
using ShoeStore.DataAccess;
using AutoMapper;

namespace ShoeStore.BL.Entities.Products
{
    public class ProductsProvider : IProductsProvider
    {
        private readonly IRepository<ProductEntity> _productRepository;
        private readonly IMapper _mapper;

        public ProductsProvider(IRepository<ProductEntity> productsRepository, IMapper mapper)
        {
            _productRepository = productsRepository;
            _mapper = mapper;
        }

        public IEnumerable<ProductModel> GetProducts(ProductModelFilter modelFilter = null)
        {
            var minimumPrice = modelFilter.MinimumPrice;
            var maximumPrice = modelFilter.MaximumPrice;
            var productTypeId = modelFilter.ProductTypeId;
            var productBrandId = modelFilter.ProductBrandId;
            var size = modelFilter.Size;
            var color = modelFilter.Color;

            var products = _productRepository.GetAll(x =>
            (minimumPrice == null || minimumPrice < x.Price) &&
            (maximumPrice == null || maximumPrice > x.Price) &&
            (size == null || size == x.Size) &&
            (color == null || color == x.Color) &&
            (productTypeId == null || productTypeId == x.ProductTypeId) &&
            (productBrandId == null || productBrandId == x.ProductBrandId)); 


            return _mapper.Map<IEnumerable<ProductModel>>(products);
        }

        public ProductModel GetProductInfo(Guid id)
        {
            var product = _productRepository.GetById(id);
            if (product is null)
                throw new ArgumentException("Product not found.");

            return _mapper.Map<ProductModel>(product);
        }
    }
}
