using ShoeStore.BL.Entities.Admins;
using ShoeStore.BL.Entities.Customers;
using ShoeStore.BL.Entities.Products;
using ShoeStore.DataAccess;

namespace ShoeStore.Service.IoC
{
    public class ServicesConfigurator
    {
        public static void ConfigureServices(IServiceCollection services)
        {
            services.AddScoped(typeof(IRepository<>), typeof(Repository<>));
            services.AddScoped<IAdminsProvider, AdminsProvider>();
            services.AddScoped<IAdminsManager, AdminsManager>();
            services.AddScoped<ICustomersProvider, CustomersProvider>();
            services.AddScoped<ICustomersManager, CustomersManager>();
            services.AddScoped<IProductsProvider, ProductsProvider>();
            services.AddScoped<IProductsManager, ProductsManager>();
        }
    }
}
