using AutoMapper;
using ShoeStore.BL.Entities.Products.Entities;
using ShoeStore.DataAccess.Entities;

namespace ShoeStore.BL.Mapper
{
    public class ProductBLProfile : Profile
    {
        public ProductBLProfile()
        {
            CreateMap<ProductEntity, ProductModel>()
                .ForMember(x => x.Id, y => y.MapFrom(src => src.ExternalId));

            CreateMap<CreateProductModel, ProductEntity>()
                .ForMember(x => x.Id, y => y.Ignore())
                .ForMember(x => x.ExternalId, y => y.Ignore())
                .ForMember(x => x.ModificationTime, y => y.Ignore())
                .ForMember(x => x.CreationTime, y => y.Ignore());

            //CreateMap<UpdateProductModel, ProductEntity>()
            //    .ForMember(x => x.Id, y => y.Ignore())
            //    .ForMember(x => x.ExternalId, y => y.Ignore())
            //    .ForMember(x => x.ModificationTime, y => y.Ignore())
            //    .ForMember(x => x.CreationTime, y => y.Ignore());
        }
    }
}
