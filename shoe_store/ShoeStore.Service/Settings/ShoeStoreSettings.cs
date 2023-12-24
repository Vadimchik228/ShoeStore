namespace ShoeStore.Service.Settings
{
    public class ShoeStoreSettings
    {
        public string ShoeStoreDbContextConnectionString { get; set; }
        public string IdentityServerUri { get; set; }
        public string ClientId { get; set; }
        public string ClientSecret { get; set; }
    }
}