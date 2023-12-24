using Microsoft.EntityFrameworkCore;
using Microsoft.Extensions.Configuration;
using Microsoft.Extensions.DependencyInjection;
using ShoeStore.DataAccess;
using ShoeStore.Service.Settings;

namespace ShoeStore.UnitTests.Repository
{
    public class RepositoryTestBase
    {
        protected readonly ShoeStoreSettings Settings;
        protected readonly IDbContextFactory<ShoeStoreDbContext> DbContextFactory;
        protected readonly IServiceProvider ServiceProvider;
        public RepositoryTestBase()
        {
            var configuration = new ConfigurationBuilder()
                .AddJsonFile("appsettings.json", optional: false)
                .Build();

            Settings = ShoeStoreSettingsReader.Read(configuration);
            ServiceProvider = ConfigureServiceProvider();

            DbContextFactory = ServiceProvider.GetRequiredService<IDbContextFactory<ShoeStoreDbContext>>();
        }

        private IServiceProvider ConfigureServiceProvider()
        {
            var serviceCollection = new ServiceCollection();
            serviceCollection.AddDbContextFactory<ShoeStoreDbContext>(
                options => { options.UseSqlServer(Settings.ShoeStoreDbContextConnectionString); },
                ServiceLifetime.Scoped);
            return serviceCollection.BuildServiceProvider();
        }
    }
}
