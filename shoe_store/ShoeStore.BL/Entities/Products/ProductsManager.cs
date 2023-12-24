using ShoeStore.BL.Entities.Products.Entities;
using ShoeStore.DataAccess.Entities;
using ShoeStore.DataAccess;
using AutoMapper;

namespace ShoeStore.BL.Entities.Products
{
    public class ProductsManager : IProductsManager
    {
        private readonly IRepository<ProductEntity> _productsRepository;
        private readonly IMapper _mapper;
        public ProductsManager(IRepository<ProductEntity> productsRepository, IMapper mapper)
        {
            _productsRepository = productsRepository;
            _mapper = mapper;
        }

        public ProductModel CreateProduct(CreateProductModel model)
        {
            var entity = _mapper.Map<ProductEntity>(model);

            _productsRepository.Save(entity);

            return _mapper.Map<ProductModel>(entity);
        }
        public void DeleteProduct(Guid id)
        {
            var entity = _productsRepository.GetById(id);
            if (entity == null)
                throw new ArgumentException("Product not found");
            _productsRepository.Delete(entity);
        }
        public ProductModel UpdateProduct(Guid id, UpdateProductModel model)
        {
            var entity = _productsRepository.GetById(id);
            if (entity == null)
                throw new ArgumentException("Product not found");
            entity.Name = model.Name;
            entity.Price = model.Price;
            entity.PictureUrl = model.PictureUrl;
            entity.Description = model.Description;
            _productsRepository.Save(entity);
            return _mapper.Map<ProductModel>(entity);
        }
    }
}
