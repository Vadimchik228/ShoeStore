using AutoMapper;
using ShoeStore.Service.Mapper;

namespace ShoeStore.BL.UnitTests.Mapper
{
    public static class MapperHelper
    {
        static MapperHelper()
        {
            var config = new MapperConfiguration(x =>
            {
                x.AddProfile(typeof(AdminsWebAPIProfile));
                x.AddProfile(typeof(CustomersWebAPIProfile));
                x.AddProfile(typeof(ProductsWebAPIProfile));
            });
            Mapper = new AutoMapper.Mapper(config);
        }

        public static IMapper Mapper { get; }
    }
}
