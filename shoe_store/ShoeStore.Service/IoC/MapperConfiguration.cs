using ShoeStore.BL.Mapper;
using ShoeStore.Service.Mapper;

namespace ShoeStore.Service.IoC
{
    public static class MapperConfigurator
    {
        public static void ConfigureServices(IServiceCollection services)
        {
            services.AddAutoMapper(config =>
            {
                config.AddProfile<AdminBLProfile>();
                config.AddProfile<CustomerBLProfile>();
                config.AddProfile<ProductBLProfile>();
                config.AddProfile<AdminsWebAPIProfile>();
                config.AddProfile<CustomersWebAPIProfile>();
                config.AddProfile<ProductsWebAPIProfile>();
            });
        }
    }
}
