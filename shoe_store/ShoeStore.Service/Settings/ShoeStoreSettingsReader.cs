namespace ShoeStore.Service.Settings
{
    public static class ShoeStoreSettingsReader
    {
        public static ShoeStoreSettings Read(IConfiguration configuration)
        {
            return new ShoeStoreSettings()
            {
                ShoeStoreDbContextConnectionString = configuration.GetValue<string>("ShoeStoreDbContext"),
                IdentityServerUri = configuration.GetValue<string>("IdentityServerSettings:Uri"),
                ClientId = configuration.GetValue<string>("IdentityServerSettings:ClientId"),
                ClientSecret = configuration.GetValue<string>("IdentityServerSettings:ClientSecret")
            };
        }
    }
}