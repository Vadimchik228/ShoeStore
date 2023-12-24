using ShoeStore.DataAccess;
using ShoeStore.Service.Settings;
using Microsoft.EntityFrameworkCore;

namespace ShoeStore.Service.IoC;

public static class DbContextConfigurator
{
    public static void ConfigureService(IServiceCollection services, ShoeStoreSettings settings)
    {
        services.AddDbContextFactory<ShoeStoreDbContext>(
            options => { options.UseSqlServer(settings.ShoeStoreDbContextConnectionString); },
            ServiceLifetime.Scoped);
    }

    public static void ConfigureApplication(IApplicationBuilder app)
    {
        using var scope = app.ApplicationServices.CreateScope();
        var contextFactory = scope.ServiceProvider.GetRequiredService<IDbContextFactory<ShoeStoreDbContext>>();
        using var context = contextFactory.CreateDbContext();
        context.Database.Migrate(); 
    }

}