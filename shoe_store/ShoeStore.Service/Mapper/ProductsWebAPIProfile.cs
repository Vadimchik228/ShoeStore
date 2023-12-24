using AutoMapper;
using ShoeStore.BL.Entities.Products.Entities;
using ShoeStore.Service.Controllers.Entities;

namespace ShoeStore.Service.Mapper
{
    public class ProductsWebAPIProfile : Profile
    {
        public ProductsWebAPIProfile()
        {
            CreateMap<ProductsFilter, ProductModelFilter>();
            CreateMap<CreateProductRequest, CreateProductModel>();
            CreateMap<UpdateProductRequest, UpdateProductModel>();
        }
    }
}
